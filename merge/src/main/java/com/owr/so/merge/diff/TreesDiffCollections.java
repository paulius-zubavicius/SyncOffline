package com.owr.so.merge.diff;

import java.util.ArrayList;
import java.util.List;

public class TreesDiffCollections {

	private List<FileModifiedDiff> modifiedFiles = new ArrayList<>();

	private List<FileMovedDiff> movedFiles = new ArrayList<>();

	private List<FileNewDiff> newFiles = new ArrayList<>();

	private List<FileDuplicatesDiff> duplicates = new ArrayList<>();

	private List<DirNewDiff> newDirs = new ArrayList<>();

	public List<FileModifiedDiff> getModifiedFiles() {
		return modifiedFiles;
	}

	public List<FileMovedDiff> getMovedFiles() {
		return movedFiles;
	}

	public List<FileNewDiff> getNewFiles() {
		return newFiles;
	}

	public List<FileDuplicatesDiff> getDuplicates() {
		return duplicates;
	}

	public List<DirNewDiff> getNewDirs() {
		return newDirs;
	}

}
