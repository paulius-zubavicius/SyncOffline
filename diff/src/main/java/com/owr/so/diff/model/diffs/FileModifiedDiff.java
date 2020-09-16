package com.owr.so.diff.model.diffs;

import com.owr.so.diff.model.DiffAction;
import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.UserResolution;

public class FileModifiedDiff extends UserResolution {

	private FileEntityWrapper file1;
	private FileEntityWrapper file2;

	public FileModifiedDiff(FileEntityWrapper file1, FileEntityWrapper file2) {
		this.file1 = file1;
		this.file2 = file2;

		setAction(DiffAction.UPDATE);

	}

	public FileEntityWrapper getFile1() {
		return file1;
	}

	public FileEntityWrapper getFile2() {
		return file2;
	}
	
	@Override
	public void setAction(DiffAction action) {

		if (file1.getFile().getModified() == file2.getFile().getModified()) {
			super.setAction(DiffAction.IGNORE);
		} else {
			super.setAction(action);
		}
	}

}
