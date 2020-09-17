package com.owr.so.diff.model;

public class FileEntityWrapper {
	private String repoRootDir;
	private String relativeDirPath;

	private String repoName;

	private FileEntity file;

	public FileEntityWrapper(String repoName, String repoRootDir, String relativeDirPath, FileEntity file) {
		this.repoName = repoName;
		this.repoRootDir = repoRootDir;
		this.relativeDirPath = relativeDirPath;
		this.file = file;
	}

	public String getRepoName() {
		return repoName;
	}

	public String getPath() {
		return relativeDirPath + "/" + file.getName();
	}

	public FileEntity getFile() {
		return file;
	}

	public String getRepoRootDir() {
		return repoRootDir;
	}

}
