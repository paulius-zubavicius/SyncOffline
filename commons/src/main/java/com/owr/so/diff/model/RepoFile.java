package com.owr.so.diff.model;

import java.util.List;

public class RepoFile {

	private PathType pathType;

	private String path;

	private List<String> excludes;

	public PathType getPathType() {
		return pathType;
	}

	public void setPathType(PathType pathType) {
		this.pathType = pathType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getExcludes() {
		return excludes;
	}

	public void setExcludes(List<String> excludes) {
		this.excludes = excludes;
	}

}
