package com.owr.so.diff.collector.dirmove;

import com.owr.so.diff.collector.DiffFilter;
import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.diff.model.RepoMetaData;

public class DirMoveFilter implements DiffFilter {
    //TODO recognize whole dir move instead separate files
    @Override
    public void apply(RepoMetaData tree1, RepoMetaData tree2, DirTreesDiffResult diffResult) {

    }


}
