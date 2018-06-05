package com.owr.so.merge.args;

public class ArgsValues {

	private String metaFile1Path;
	private String metaFile2Path;

	private String subdir1;
	private String subdir2;

	private boolean guiMode;

	ArgsValues(String metaFile1Path, String metaFile2Path, String subdir1, String subdir2, boolean guiMode) {

		this.metaFile1Path = metaFile1Path;
		this.metaFile2Path = metaFile2Path;
		this.subdir1 = subdir1;
		this.subdir2 = subdir2;
		this.guiMode = guiMode;

	}

	public String getMetaFile1Path() {
		return metaFile1Path;
	}

	public String getMetaFile2Path() {
		return metaFile2Path;
	}

	public String getSubdir1() {
		return subdir1;
	}

	public String getSubdir2() {
		return subdir2;
	}

	public boolean isGuiMode() {
		return guiMode;
	}

}
