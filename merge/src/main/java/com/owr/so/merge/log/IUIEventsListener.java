package com.owr.so.merge.log;

import com.owr.so.merge.args.ArgsValues;
import com.owr.so.merge.diff.TreesDiffCollections;

public interface IUIEventsListener {

	void treesLoaded(ArgsValues argsValues);
	
	void treesCompared(TreesDiffCollections diffCollection);
}
