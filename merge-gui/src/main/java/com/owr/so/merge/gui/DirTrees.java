package com.owr.so.merge.gui;

import com.owr.so.diff.model.RepoMetaData;

public class DirTrees {

	private RepoMetaData meta1;
	private RepoMetaData meta2;

	public DirTrees(RepoMetaData meta1, RepoMetaData meta2) {
		this.meta1 = meta1;
		this.meta2 = meta2;
	}

	public RepoMetaData getMeta1() {
		return meta1;
	}

	public void setMeta1(RepoMetaData meta1) {
		this.meta1 = meta1;
	}

	public RepoMetaData getMeta2() {
		return meta2;
	}

	public void setMeta2(RepoMetaData meta2) {
		this.meta2 = meta2;
	}

}
