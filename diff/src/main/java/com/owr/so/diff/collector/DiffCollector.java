package com.owr.so.diff.collector;

import com.owr.so.diff.model.RepoData;
import com.owr.so.diff.model.DirTreesDiffResult;

public interface DiffCollector {

      void apply(RepoData tree1, RepoData tree2, DirTreesDiffResult diffResult);

}
