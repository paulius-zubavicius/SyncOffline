package com.owr.so.diff.model;

public class DirEntity {

	private String relativeDirPath;

	private String repoName;

	public DirEntity(String relativeDirPath, String repoName) {
		this.relativeDirPath = relativeDirPath;
		this.repoName = repoName;
	}

	public String getRelativeDirPath() {
		return relativeDirPath;
	}

	public String getRepoName() {
		return repoName;
	}

}
