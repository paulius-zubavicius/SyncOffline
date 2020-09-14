package com.owr.so.diff.model;

import java.util.List;

public class RepoFile {

	private String pc;

	private boolean absolutePaths;

	private List<ScanPath> paths;

	public RepoFile() {

	}

	public RepoFile(String pc, boolean absolutePaths, List<ScanPath> paths) {
		this.pc = pc;
		this.paths = paths;
		this.absolutePaths = absolutePaths;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	public List<ScanPath> getPaths() {
		return paths;
	}

	public void setPaths(List<ScanPath> paths) {
		this.paths = paths;
	}

	public boolean isAbsolutePaths() {
		return absolutePaths;
	}

	public void setAbsolutePaths(boolean absolutePaths) {
		this.absolutePaths = absolutePaths;
	}

}
