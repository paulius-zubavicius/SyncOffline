package com.owr.so.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DirEntity implements Serializable {

	private static final long serialVersionUID = 118318597202971234L;

	private String path;

	private List<FileEntity> files = new ArrayList<>();

	/**
	 * Repository name (json file name)
	 */
	private transient String repoName;

	public DirEntity() {

	}

	public DirEntity(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<FileEntity> getFiles() {
		return files;
	}

	public void setFiles(List<FileEntity> files) {
		this.files = files;
	}

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

}
