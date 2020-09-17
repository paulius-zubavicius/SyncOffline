package com.owr.so.diff.model;

import java.util.ArrayList;
import java.util.List;

import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;

public class DirTreesDiffResult {

	private List<FileModifiedDiff> modifiedFiles = new ArrayList<>();

	private List<FileMovedDiff> movedFiles = new ArrayList<>();

	private List<FileNewDiff> newFiles = new ArrayList<>();

	private List<FileDuplicatesDiff> duplicates = new ArrayList<>();

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

}
