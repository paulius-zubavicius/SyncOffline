package com.owr.so.model;

import java.io.Serializable;

public class FileEntity implements Serializable {

	private static final long serialVersionUID = -3211564506768018649L;

	private String name;
	private String hashSum;
	private long size;
	private long modified;
	private long accessed;

	private transient DirEntity dir;

	public String getAbsolutePath() {
		return dir.getPath() + "/" + name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHashSum() {
		return hashSum;
	}

	public void setHashSum(String hashSum) {
		this.hashSum = hashSum;
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

	public DirEntity getDir() {
		return dir;
	}

	public void setDir(DirEntity dir) {
		this.dir = dir;
	}

}
