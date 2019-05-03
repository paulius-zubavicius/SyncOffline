package com.owr.so.diff.collector.similar.img;

import com.owr.so.diff.collector.DiffCollector;
import com.owr.so.diff.model.DirTree;
import com.owr.so.diff.model.DirTreesDiffResult;

public class SimilarImgDetector implements DiffCollector {
    @Override
    public void apply(DirTree tree1, DirTree tree2, DirTreesDiffResult diffResult) {
        //TODO incorporate to TreesDiffCollector ? it is for special case - edited and renamed/moved
    }
}
