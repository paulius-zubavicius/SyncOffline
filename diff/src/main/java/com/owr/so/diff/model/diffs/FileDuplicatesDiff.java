package com.owr.so.diff.model.diffs;

import java.util.List;

import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.RepoDiff;

public class FileDuplicatesDiff implements RepoDiff {

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

	public String getChecksum() {
		return files1.get(0).getFile().getChecksum();
	}

}
