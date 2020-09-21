package com.owr.so.diff.model;

import java.util.List;
import java.util.Map;

public class RepoFile {

	private PathType pathType;

	private String path;

	private Map<ExclusionType, List<String>> excludes;

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

	public Map<ExclusionType, List<String>> getExcludes() {
		return excludes;
	}

	public void setExcludes(Map<ExclusionType, List<String>> excludes) {
		this.excludes = excludes;
	}

}
