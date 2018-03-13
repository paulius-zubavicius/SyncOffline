package com.owr.so.merge.diff;

import com.owr.so.model.FileEntity;

public class FileMovedDiff extends UserResolution {

	private FileEntity file1;
	private FileEntity file2;

	public FileMovedDiff(FileEntity file1, FileEntity file2) {
		this.file1 = file1;
		this.file2 = file2;

		if (file1.getAccessed() == file2.getAccessed()) {
			super.setAction(DiffAction.IGNORE);
		} else {
			super.setAction(DiffAction.UPDATE);
		}

	}

	public FileEntity getFile1() {
		return file1;
	}

	public FileEntity getFile2() {
		return file2;
	}

	@Override
	public void setAction(DiffAction action) {

		if (file1.getAccessed() == file2.getAccessed()) {
			super.setAction(DiffAction.IGNORE);
		} else {
			super.setAction(action);
		}
	}

}
