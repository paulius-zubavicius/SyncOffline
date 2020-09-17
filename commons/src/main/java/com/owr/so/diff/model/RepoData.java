package com.owr.so.diff.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepoData {

	private LocalDateTime lastScan = LocalDateTime.now();

	private Map<String, List<FileEntity>> tree = new HashMap<>();

	public LocalDateTime getLastScan() {
		return lastScan;
	}

	public void setLastScan(LocalDateTime lastScan) {
		this.lastScan = lastScan;
	}

	public Map<String, List<FileEntity>> getTree() {
		return tree;
	}

	@Override
	public String toString() {
		return "RepoData [lastScan=" + lastScan + "]";
	}

}
