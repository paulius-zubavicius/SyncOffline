package com.owr.so.scan.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.owr.so.commons.PCName;
import com.owr.so.commons.Storage;
import com.owr.so.diff.model.DirEntity;
import com.owr.so.diff.model.RepoData;
import com.owr.so.diff.model.RepoFile;
import com.owr.so.diff.model.RepoMetaData;
import com.owr.so.diff.model.ScanPath;
import com.owr.so.scan.dirtree.FileVisitorScanner;
import com.owr.so.scan.events.IScanEventsListener;
import com.owr.so.scan.events.ScanEvent;

/**
 * @author Paulius Zubavicius
 *
 */
public class RepoScanner {

	private static final String REPO_EXT = "repo";
	private static final String DATE_EXT = "data";

	public void scan(Path repoPath, IScanEventsListener listener) {

		File repoFile = repoPath.toFile();
		validateRepoPath(repoFile, listener);
		RepoFile repo = Storage.load(repoPath, RepoFile.class);
		validateRepoFile(repo, listener);

		File dataFile = dataFileFromRepo(repoFile);

		RepoData repoData = new RepoData();
		if (dataFile.exists()) {
			repoData = Storage.load(dataFile.toPath(), RepoData.class);
		}

		RepoData newDataTree = scanTree(repo, repoData, listener);
		Storage.save(dataFile.toPath(), newDataTree);
	}

	private void validateRepoFile(RepoFile repo, IScanEventsListener listener) {

		boolean valid = true;

		if (repo.getPc() == null) {
			listener.event(ScanEvent.VALIDATION_MOUNTED_DISC,
					"Detected as mobile repository / removable disk (no pc value / 'uname -u' provided)");
		} else if (!PCName.uname().equals(repo.getPc())) {
			valid = false;
			listener.event(ScanEvent.VALIDATION_OTHER_PCS_REPO,
					"Expected:[" + PCName.uname() + "] found:[" + repo.getPc() + "]");
		}

		if (repo.getPaths().stream().map(ScanPath::getPath).anyMatch(String::isEmpty)) {
			valid = false;
			listener.event(ScanEvent.VALIDATION_BLANK_PATH, "Empty paths not allowed");
		}

		if (repo.getPaths().stream().map(ScanPath::getId).anyMatch(String::isBlank)) {
			valid = false;
			listener.event(ScanEvent.VALIDATION_BLANK_ID, "Blank Id not allowed");
		}

		long beforeDistinct = repo.getPaths().size();
		long afterDistinct = repo.getPaths().stream().map(ScanPath::getId).distinct().count();

		if (beforeDistinct != afterDistinct) {
			valid = false;
			listener.event(ScanEvent.VALIDATION_PATH_NAMES_NOT_UNIQUE,
					"Expected uniq:[" + beforeDistinct + "] found uniq:[" + afterDistinct + "]");
		}

		if (!valid) {
			throw new RuntimeException("Repository file is not valid");
		}
	}

	private File dataFileFromRepo(File repoFile) {
		String repoFileName = repoFile.getName();
		return new File(repoFileName.substring(0, repoFileName.length() - REPO_EXT.length() - 1) + "." + DATE_EXT);
	}

	public void validateRepoPath(File repoFile, IScanEventsListener listener) {

		String repoFileName = repoFile.getName();

		if (!repoFile.exists()) {
			listener.event(ScanEvent.VALIDATION_REPO_FILE_NOT_EXIST,
					"Repository file does not exists: [" + repoFile.getName() + "]");
			throw new RuntimeException("Repository file does not exists: [" + repoFile + "]");
		}

		if (!repoFileName.endsWith(REPO_EXT)) {
			listener.event(ScanEvent.VALIDATION_NOT_A_REPO_FILE,
					"Expected:[path/to/*." + REPO_EXT + "] provided:[" + repoFile.getName() + "]");
			throw new RuntimeException("Not a repo file");
		}
	}

	private RepoData scanTree(RepoFile repo, RepoData data, IScanEventsListener listener) {
		Instant instant1 = Instant.now();
		RepoData result = new RepoData();

		repo.getPaths().forEach(path -> {
			try {
				Map<String, DirEntity> dirTree = new HashMap<>();
				Files.walkFileTree(Path.of(path.getPath()), new FileVisitorScanner(dirTree, new RepoMetaData(data),
						path.getPath(), path.getExcludes(), listener));
				result.getTrees().put(path.getId(), dirTree);
			} catch (IOException e) {
				e.printStackTrace();
				listener.event(ScanEvent.DONE_FAIL, e);
			}
		});

		result.setLastScan(LocalDateTime.now());

		listener.event(ScanEvent.DONE_SUCCESS, Duration.between(instant1, Instant.now()), result);
		return result;
	}

}
