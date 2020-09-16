package com.owr.so.diff.filters;

import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.diff.model.RepoMetaData;

public class MovedDirFilter implements DiffFilter {
    //TODO recognize whole dir move instead separate files
    @Override
    public void apply(RepoMetaData tree1, RepoMetaData tree2, DirTreesDiffResult diffResult) {

    }


}
