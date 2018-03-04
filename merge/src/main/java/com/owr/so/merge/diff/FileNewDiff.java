package com.owr.so.merge.diff;

import com.owr.so.model.FileEntity;

public class FileNewDiff implements Diff {

	private FileEntity file1;

	public FileNewDiff(FileEntity file1) {
		this.file1 = file1;
	}

	public FileEntity getFile1() {
		return file1;
	}

	@Override
	public boolean isInConflict() {
		return false;
	}

}
