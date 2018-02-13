package com.owr.so.model;

import java.io.Serializable;

public class FileMeta implements Serializable {

	private static final long serialVersionUID = -3211564506768018649L;

	private String md5;
	private long size;
	private long modified;
	private long accessed;

	private transient String path;
	private transient String repo;
	
	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getModified() {
		return modified;
	}

	public void setModified(long modified) {
		this.modified = modified;
	}

	public long getAccessed() {
		return accessed;
	}

	public void setAccessed(long accessed) {
		this.accessed = accessed;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRepo() {
		return repo;
	}

	public void setRepo(String repo) {
		this.repo = repo;
	}

}
