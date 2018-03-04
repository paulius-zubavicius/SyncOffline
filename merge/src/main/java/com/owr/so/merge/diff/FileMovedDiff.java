package com.owr.so.merge.diff;

import com.owr.so.model.FileEntity;

public class FileMovedDiff implements Diff {

	private FileEntity file1;
	private FileEntity file2;
	private boolean conflict;

	public FileMovedDiff(FileEntity file1, FileEntity file2, boolean conflict) {
		this.file1 = file1;
		this.file2 = file2;
	}

	public FileEntity getFile1() {
		return file1;
	}

	public FileEntity getFile2() {
		return file2;
	}

	@Override
	public boolean isInConflict() {
		return conflict;
	}

}
