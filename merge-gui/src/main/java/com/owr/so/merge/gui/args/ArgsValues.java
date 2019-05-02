package com.owr.so.merge.gui.args;

public class ArgsValues {

	private String metaFile1Path;
	private String metaFile2Path;

	private String subDir1;
	private String subDir2;

	private boolean guiMode;

	ArgsValues(String metaFile1Path, String metaFile2Path, String subDir1, String subDir2, boolean guiMode) {

		this.metaFile1Path = metaFile1Path;
		this.metaFile2Path = metaFile2Path;
		this.subDir1 = subDir1;
		this.subDir2 = subDir2;
		this.guiMode = guiMode;

	}

	public String getMetaFile1Path() {
		return metaFile1Path;
	}

	public String getMetaFile2Path() {
		return metaFile2Path;
	}

	public String getSubDir1() {
		return subDir1;
	}

	public String getSubDir2() {
		return subDir2;
	}

	public boolean isGuiMode() {
		return guiMode;
	}

}
