package com.owr.so.diff;

import java.nio.file.Path;
import java.util.List;

import com.owr.so.commons.DataLoader;
import com.owr.so.diff.args.ArgsValues;
import com.owr.so.diff.filters.DiffFilter;
import com.owr.so.diff.filters.MovedDirFilter;
import com.owr.so.diff.filters.SimilarImgFilter;
import com.owr.so.diff.filters.TreesDiffFilter;
import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.diff.model.ReposRootPaths;
import com.owr.so.diff.out.IOutputHandler;
import com.owr.so.diff.out.OutputHandlerFactory;

public class RepoDiff {

    private static final List<DiffFilter> filters = List.of(new TreesDiffFilter(), new MovedDirFilter(),
            new SimilarImgFilter());

    public RepoDiff(ArgsValues argsValues) {

        DataLoader dl1 = new DataLoader(Path.of(argsValues.getRepoFilePath1()));
        DataLoader dl2 = new DataLoader(Path.of(argsValues.getRepoFilePath2()));
        IOutputHandler outHandler = OutputHandlerFactory.createInstance(argsValues.getMode());
        DirTreesDiffResult treesDiffs = new DirTreesDiffResult();

        outHandler.loadRepoTrees(dl1, dl2);
        filters.forEach(filter -> filter.apply(dl1.getMeta(), dl2.getMeta(), treesDiffs));
        outHandler.compareRepoTrees(treesDiffs, dl1.getMeta(), dl2.getMeta(), new ReposRootPaths(dl1.getMeta(), dl2.getMeta()));
    }

}
