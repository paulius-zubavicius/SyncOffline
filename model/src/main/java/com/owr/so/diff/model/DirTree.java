package com.owr.so.diff.model;

import java.util.List;
import java.util.Map;

@Deprecated
public class DirTree {

	private String id;

	private Map<String, List<FileEntity>> tree;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, List<FileEntity>> getTree() {
		return tree;
	}

	public void setTrees(Map<String, List<FileEntity>> tree) {
		this.tree = tree;
	}

}
