package com.owr.so.diff.model.diffs;

import com.owr.so.diff.model.UserResolution;
import com.owr.so.diff.model.FileEntity;

public class FileNewDiff extends UserResolution {

	private FileEntity file1;

	public FileNewDiff(FileEntity file1) {
		this.file1 = file1;
	}

	public FileEntity getFile1() {
		return file1;
	}

}
