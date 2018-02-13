package com.owr.so.model;

public class RepositoryMeta {

	private String name;
	private String path;

	public RepositoryMeta() {

	}

	public RepositoryMeta(String name, String path) {
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
