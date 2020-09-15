package com.owr.so.diff.collector.similar.img;

import com.owr.so.diff.collector.DiffFilter;
import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.diff.model.RepoMetaData;

public class SimilarImgFilter implements DiffFilter {
    @Override
    public void apply(RepoMetaData tree1, RepoMetaData tree2, DirTreesDiffResult diffResult) {
        //TODO incorporate to TreesDiffCollector ? it is for special case - edited and renamed/moved
    }
}
