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
import com.owr.so.diff.out.IOutputHandler;
import com.owr.so.diff.out.options.OptionsOutput;

public class RepoDiff {

	private static final List<DiffFilter> filters = List.of(new TreesDiffFilter(), new MovedDirFilter(),
			new SimilarImgFilter());

	private IOutputHandler outHandler = new OptionsOutput();

	public RepoDiff(ArgsValues argsValues) {

		DataLoader dl1 = new DataLoader(Path.of(argsValues.getRepoFilePath1()));
		DataLoader dl2 = new DataLoader(Path.of(argsValues.getRepoFilePath2()));
		
		outHandler.treesLoaded(dl1, dl2);

		DirTreesDiffResult treesDiffs = new DirTreesDiffResult();

		filters.forEach(filter -> filter.apply(dl1.getMeta(), dl2.getMeta(), treesDiffs));
		
		outHandler.treesCompared(treesDiffs, dl1.getMeta(), dl2.getMeta());
	}

}
