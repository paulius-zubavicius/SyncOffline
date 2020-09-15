package com.owr.so.diff.model.diffs;

import java.util.List;

import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.UserResolution;

public class FileDuplicatesDiff extends UserResolution {

	private List<FileEntityWrapper> files1;
	private List<FileEntityWrapper> files2;

	public FileDuplicatesDiff(List<FileEntityWrapper> files1, List<FileEntityWrapper> files2) {
		this.files1 = files1;
		this.files2 = files2;
	}

	public List<FileEntityWrapper> getFiles1() {
		return files1;
	}

	public List<FileEntityWrapper> getFiles2() {
		return files2;
	}

}
