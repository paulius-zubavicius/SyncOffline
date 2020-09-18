package com.owr.so.diff.model.diffs;

import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.RepoDiff;

public class FileNewDiff implements RepoDiff {

	private FileEntityWrapper file1;

	public FileNewDiff(FileEntityWrapper file1) {
		this.file1 = file1;
	}

	public FileEntityWrapper getFile1() {
		return file1;
	}

}
