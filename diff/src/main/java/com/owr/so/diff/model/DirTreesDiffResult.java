package com.owr.so.diff.model;

import com.owr.so.diff.model.diffs.*;

import java.util.ArrayList;
import java.util.List;

public class DirTreesDiffResult {

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
