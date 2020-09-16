package com.owr.so.diff.out;

import com.owr.so.diff.args.ArgsValues;
import com.owr.so.diff.model.DirTreesDiffResult;

public interface IOutputHandler {
	void treesLoaded(ArgsValues argsValues);
	void treesCompared(DirTreesDiffResult diffCollection);
}
