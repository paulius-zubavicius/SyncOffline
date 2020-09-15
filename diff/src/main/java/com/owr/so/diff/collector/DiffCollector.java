package com.owr.so.diff.collector;

import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.diff.model.RepoMetaData;

public interface DiffCollector {

	void collect(RepoMetaData tree1, RepoMetaData tree2, DirTreesDiffResult diffResult);
}
