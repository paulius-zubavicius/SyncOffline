package com.owr.so.merge.gui;

import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.merge.gui.log.IUIEventsListener;

public interface IMergeHandler {
	
	DirTrees loadTrees(IUIEventsListener eventsListener);

	DirTreesDiffResult compareTrees(DirTrees dirTrees, IUIEventsListener eventsListener);

	void uiEditDone(DirTreesDiffResult diffCollection, DirTrees dirTrees, IUIEventsListener eventsListener);

	void uiEditCanceled();
}
