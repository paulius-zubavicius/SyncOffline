package com.owr.so.diff.out;

import com.owr.so.commons.DataLoader;
import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.diff.model.RepoMetaData;
import com.owr.so.diff.model.ReposRootPaths;

public interface IOutputHandler {
	void treesLoaded(DataLoader dl1, DataLoader dl2);

	void treesCompared(DirTreesDiffResult diffCollection, RepoMetaData meta1, RepoMetaData meta2, ReposRootPaths rootPathByRepoName );
	
	void summary(DirTreesDiffResult diffCollection, RepoMetaData meta1, RepoMetaData meta2);
}
