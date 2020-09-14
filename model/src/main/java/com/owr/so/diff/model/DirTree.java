package com.owr.so.diff.model;

import java.util.List;

public class DirTree {

	private String id;

	private List<DirEntity> trees;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<DirEntity> getTrees() {
		return trees;
	}

	public void setTrees(List<DirEntity> trees) {
		this.trees = trees;
	}

}
