package com.owr.so.diff.args;

public class ArgsValues {

	private String repoFilePath1;
	private String repoFilePath2;

	private String mode;

	ArgsValues(String repoFilePath1, String repoFilePath2, String mode) {
		this.repoFilePath1 = repoFilePath1;
		this.repoFilePath2 = repoFilePath2;
		this.mode = mode;
	}

	public String getRepoFilePath1() {
		return repoFilePath1;
	}

	public String getRepoFilePath2() {
		return repoFilePath2;
	}

	public String getMode() {
		return mode;
	}
}
