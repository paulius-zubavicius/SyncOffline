package com.owr.so.commons;

import java.io.File;
import java.nio.file.Path;

import com.owr.so.diff.model.RepoData;
import com.owr.so.diff.model.RepoFile;
import com.owr.so.diff.model.RepoMetaData;

public class DataLoader {

	private static final String REPO_EXT = "repo";
	private static final String DATE_EXT = "data";

	private RepoMetaData meta;
	private RepoData repoData;
	private RepoFile repo;
	private Path dataFilePath;

	public DataLoader(Path repoPath) {
		this(repoPath, (event, data) -> {
			System.out.print(event.toString() + " : ");
			for (Object obj : data) {
				System.out.print(obj + "\t");
			}
			System.out.println();
		});
	}

	public DataLoader(Path repoPath, IScanEventsListener listener) {
		File repoFile = repoPath.toFile();
		validateRepoPath(repoFile, listener);
		repo = Storage.load(repoPath, RepoFile.class);
		validateRepoFile(repo, listener);

		File dataFile = dataFileFromRepo(repoFile);
		dataFilePath = dataFile.toPath();
		repoData = new RepoData();
		if (dataFile.exists()) {
			repoData = Storage.load(dataFilePath, RepoData.class);
		}

		meta = new RepoMetaData(repoData, extractFileName(repoFile));
	}

	public Path getDataFilePath() {
		return dataFilePath;
	}

	public RepoMetaData getMeta() {
		return meta;
	}

	public RepoData getRepoData() {
		return repoData;
	}

	public RepoFile getRepo() {
		return repo;
	}

	private File dataFileFromRepo(File repoFile) {
		String repoFileName = repoFile.getName();
		return new File(repoFileName.substring(0, repoFileName.length() - REPO_EXT.length() - 1) + "." + DATE_EXT);
	}

	private String extractFileName(File repoFile) {
		String repoFileName = repoFile.getName();
		String withoutExt = repoFileName.substring(0, repoFileName.length() - REPO_EXT.length() - 1);
		return withoutExt.isEmpty() ? repoFileName : withoutExt;
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

	private void validateRepoFile(RepoFile repo, IScanEventsListener listener) {

//		boolean valid = true;
//
//		if (repo.getPc() == null) {
//			listener.event(ScanEvent.VALIDATION_MOUNTED_DISC,
//					"Detected as mobile repository / removable disk (no pc value / 'uname -u' provided)");
//		} else if (!PCName.uname().equals(repo.getPc())) {
//			valid = false;
//			listener.event(ScanEvent.VALIDATION_OTHER_PCS_REPO,
//					"Expected:[" + PCName.uname() + "] found:[" + repo.getPc() + "]");
//		}

		if (repo.getPath() == null || repo.getPath().isEmpty()) {
//			valid = false;
			listener.event(ScanEvent.VALIDATION_BLANK_PATH, "Empty paths not allowed");
			throw new RuntimeException("Repository file is not valid");
		}

//		if (!valid) {
//			throw new RuntimeException("Repository file is not valid");
//		}
	}

}
