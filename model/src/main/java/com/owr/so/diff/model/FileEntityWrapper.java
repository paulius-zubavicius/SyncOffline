package com.owr.so.diff.model;

public class FileEntityWrapper {

	private String path;

	private String repoName;

	private FileEntity file;

	public FileEntityWrapper(String repoName, String path, FileEntity file) {
		this.repoName = repoName;
		this.path = path;
		this.file = file;
	}

	public String getRepoName() {
		return repoName;
	}

	public String getPath() {
		return path + "/" + file.getName();
	}

	public FileEntity getFile() {
		return file;
	}

}
