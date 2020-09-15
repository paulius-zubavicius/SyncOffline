package com.owr.so.diff.model.diffs;

import com.owr.so.diff.model.DiffAction;
import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.UserResolution;

public class FileMovedDiff extends UserResolution {

	private FileEntityWrapper file1;
	private FileEntityWrapper file2;

	public FileMovedDiff(FileEntityWrapper file1, FileEntityWrapper file2) {
		this.file1 = file1;
		this.file2 = file2;

		if (file1.getFile().getAccessed() == file2.getFile().getAccessed()) {
			super.setAction(DiffAction.IGNORE);
		} else {
			super.setAction(DiffAction.UPDATE);
		}

	}

	public FileEntityWrapper getFile1() {
		return file1;
	}

	public FileEntityWrapper getFile2() {
		return file2;
	}

	@Override
	public void setAction(DiffAction action) {

		if (file1.getFile().getAccessed() == file2.getFile().getAccessed()) {
			super.setAction(DiffAction.IGNORE);
		} else {
			super.setAction(action);
		}
	}

}
