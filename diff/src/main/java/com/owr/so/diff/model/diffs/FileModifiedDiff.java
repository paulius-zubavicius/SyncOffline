package com.owr.so.diff.model.diffs;

import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.RepoDiff;

public class FileModifiedDiff implements RepoDiff {

	private FileEntityWrapper file1;
	private FileEntityWrapper file2;

	public FileModifiedDiff(FileEntityWrapper file1, FileEntityWrapper file2) {
		this.file1 = file1;
		this.file2 = file2;
	}

	public FileEntityWrapper getFile1() {
		return file1;
	}

	public FileEntityWrapper getFile2() {
		return file2;
	}

}
