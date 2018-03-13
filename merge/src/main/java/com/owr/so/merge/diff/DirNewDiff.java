package com.owr.so.merge.diff;

import com.owr.so.model.DirEntity;

public class DirNewDiff extends UserResolution {

	private DirEntity dir1;

	public DirNewDiff(DirEntity dir1) {
		this.dir1 = dir1;
	}

	public DirEntity getDir1() {
		return dir1;
	}

}
