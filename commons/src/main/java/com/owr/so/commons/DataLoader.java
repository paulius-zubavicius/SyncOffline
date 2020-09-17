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
	private RepoFile repoFile;
	private Path dataFilePath;
	private Path repoFilePath;

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
		repoFilePath = repoPath.toAbsolutePath();
		dataFilePath = Path.of(removeRepoExtension(repoFilePath.toString()) + "." + DATE_EXT);
		load(repoFilePath, dataFilePath, listener);
	}

	private void load(Path repoFilePath, Path dataFilePath, IScanEventsListener listener) {
		validateRepoPath(repoFilePath, listener);
		repoFile = Storage.load(repoFilePath, RepoFile.class);
		validateRepoFile(repoFile, listener);

		repoData = new RepoData();
		if (dataFilePath.toFile().exists()) {
			repoData = Storage.load(dataFilePath, RepoData.class);
		}

		meta = new RepoMetaData(repoData, repoFile.getPath(),
				removeRepoExtension(repoFilePath.getFileName().toString()));
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

	public RepoFile getRepoFile() {
		return repoFile;
	}

	public Path getRepoFilePath() {
		return repoFilePath;
	}

	private String removeRepoExtension(String fileName) {
		String withoutExt = fileName.substring(0, fileName.length() - REPO_EXT.length() - 1);
		return withoutExt.isEmpty() ? fileName : withoutExt;
	}

	public void validateRepoPath(Path repoPath, IScanEventsListener listener) {

		File repoFile = repoPath.toFile();

		if (!repoFile.exists()) {
			listener.event(ScanEvent.VALIDATION_REPO_FILE_NOT_EXIST,
					"Repository file does not exists: [" + repoFile.getName() + "]");
			throw new RuntimeException("Repository file does not exists: [" + repoFile + "]");
		}

		if (!repoFile.getName().endsWith(REPO_EXT)) {
			listener.event(ScanEvent.VALIDATION_NOT_A_REPO_FILE,
					"Expected:[path/to/*." + REPO_EXT + "] provided:[" + repoFile.getName() + "]");
			throw new RuntimeException("Not a repo file");
		}
	}

	private void validateRepoFile(RepoFile repo, IScanEventsListener listener) {

		if (repo.getPath() == null || repo.getPath().isEmpty()) {
			listener.event(ScanEvent.VALIDATION_BLANK_PATH, "Empty paths not allowed");
			throw new RuntimeException("Repository file is not valid");
		}

	}

}
