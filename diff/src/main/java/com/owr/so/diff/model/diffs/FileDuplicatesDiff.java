package com.owr.so.diff.model.diffs;

import java.util.List;

import com.owr.so.diff.model.UserResolution;
import com.owr.so.diff.model.FileEntity;

public class FileDuplicatesDiff extends UserResolution {

	private List<FileEntity> files1;
	private List<FileEntity> files2;

	public FileDuplicatesDiff(List<FileEntity> files1, List<FileEntity> files2) {
		this.files1 = files1;
		this.files2 = files2;
	}

	public List<FileEntity> getFiles1() {
		return files1;
	}

	public List<FileEntity> getFiles2() {
		return files2;
	}

}
