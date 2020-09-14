package com.owr.so.diff.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class RepoData {

	private LocalDateTime lastScan = null;

	private Map<String, Map<String, DirEntity>> trees = new HashMap<>();

	public LocalDateTime getLastScan() {
		return lastScan;
	}

	public void setLastScan(LocalDateTime lastScan) {
		this.lastScan = lastScan;
	}

	public Map<String, Map<String, DirEntity>> getTrees() {
		return trees;
	}

	@Override
	public String toString() {
		return "RepoData [lastScan=" + lastScan + "]";
	}

}
