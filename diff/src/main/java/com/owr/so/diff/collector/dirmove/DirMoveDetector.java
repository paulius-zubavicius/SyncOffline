package com.owr.so.diff.collector.dirmove;

import com.owr.so.diff.collector.DiffCollector;
import com.owr.so.diff.model.DirTree;
import com.owr.so.diff.model.DirTreesDiffResult;

public class DirMoveDetector implements DiffCollector {
    //TODO recognize whole dir move instead separate files
    @Override
    public void apply(DirTree tree1, DirTree tree2, DirTreesDiffResult diffResult) {

    }
}
