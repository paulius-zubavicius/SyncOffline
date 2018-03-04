package com.owr.so.merge.diff;

import com.owr.so.model.DirEntity;

public class DirNewDiff implements Diff {

	private DirEntity dir1;

	public DirNewDiff(DirEntity dir1) {
		this.dir1 = dir1;
	}

	public DirEntity getDir1() {
		return dir1;
	}

	@Override
	public boolean isInConflict() {
		return false;
	}

}
