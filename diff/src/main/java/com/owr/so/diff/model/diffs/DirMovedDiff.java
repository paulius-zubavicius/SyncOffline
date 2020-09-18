package com.owr.so.diff.model.diffs;

import com.owr.so.diff.model.DirEntity;
import com.owr.so.diff.model.RepoDiff;

public class DirMovedDiff implements RepoDiff{

	private DirEntity dir1;
	private DirEntity dir2;

	public DirMovedDiff(DirEntity dir1, DirEntity dir2) {
		this.dir1 = dir1;
		this.dir2 = dir2;
	}

	public DirEntity getDir1() {
		return dir1;
	}

	public DirEntity getDir2() {
		return dir2;
	}

}
