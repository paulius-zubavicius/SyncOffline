package com.owr.so.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DirEntity implements Serializable {

	private static final long serialVersionUID = 118318597202971234L;

	private String path;

	private List<FileEntity> files = new ArrayList<>();

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

}
