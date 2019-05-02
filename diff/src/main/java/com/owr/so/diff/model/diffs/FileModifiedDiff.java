package com.owr.so.diff.model.diffs;

import com.owr.so.diff.model.DiffAction;
import com.owr.so.diff.model.UserResolution;
import com.owr.so.diff.model.FileEntity;

public class FileModifiedDiff extends UserResolution {

	private FileEntity file1;
	private FileEntity file2;

	public FileModifiedDiff(FileEntity file1, FileEntity file2) {
		this.file1 = file1;
		this.file2 = file2;

		setAction(DiffAction.UPDATE);

	}

	public FileEntity getFile1() {
		return file1;
	}

	public FileEntity getFile2() {
		return file2;
	}
	
	@Override
	public void setAction(DiffAction action) {

		if (file1.getModified() == file2.getModified()) {
			super.setAction(DiffAction.IGNORE);
		} else {
			super.setAction(action);
		}
	}

}
