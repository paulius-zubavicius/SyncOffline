package com.owr.so.merge.gui.log;

import com.owr.so.merge.gui.args.ArgsValues;
import com.owr.so.diff.model.DirTreesDifferences;

public interface IUIEventsListener {

	void treesLoaded(ArgsValues argsValues);
	
	void treesCompared(DirTreesDifferences diffCollection);
}
