package com.owr.so.merge.diff;

import java.util.List;

import com.owr.so.model.FileEntity;

public class FileDuplicatesDiff implements Diff {

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

	@Override
	public boolean isInConflict() {
		return true;
	}

}
