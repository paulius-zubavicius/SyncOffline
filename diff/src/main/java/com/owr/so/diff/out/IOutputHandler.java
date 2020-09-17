package com.owr.so.diff.out;

import com.owr.so.commons.DataLoader;
import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.diff.model.RepoFile;

public interface IOutputHandler {
	void treesLoaded(DataLoader dl1, DataLoader dl2);

	void treesCompared(DirTreesDiffResult diffCollection);
}
