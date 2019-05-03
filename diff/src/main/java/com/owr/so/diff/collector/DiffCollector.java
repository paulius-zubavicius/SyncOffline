package com.owr.so.diff.collector;

import com.owr.so.diff.model.DirTree;
import com.owr.so.diff.model.DirTreesDiffResult;

public interface DiffCollector {

      void apply(DirTree tree1, DirTree tree2, DirTreesDiffResult diffResult);

}
