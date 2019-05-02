package com.owr.so.merge.gui;

import com.owr.so.diff.model.DirTreesDifferences;
import com.owr.so.merge.gui.log.IUIEventsListener;

public interface IMergeHandler {
	
	DirTrees loadTrees(IUIEventsListener eventsListener);

	DirTreesDifferences compareTrees(DirTrees dirTrees, IUIEventsListener eventsListener);

	void uiEditDone(DirTreesDifferences diffCollection, DirTrees dirTrees, IUIEventsListener eventsListener);

	void uiEditCanceled();
}
