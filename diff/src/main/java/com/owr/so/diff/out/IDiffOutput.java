package com.owr.so.diff.out;

import java.util.List;

import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;

public interface IDiffOutput {

	void outModified(List<FileModifiedDiff> modifiedFiles);

	void outMoved(List<FileMovedDiff> movedFiles);

	void outNew(List<FileNewDiff> newFiles);

	void outDuplicates(List<FileDuplicatesDiff> duplicates);

}
