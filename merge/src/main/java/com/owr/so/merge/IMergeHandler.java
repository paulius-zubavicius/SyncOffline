package com.owr.so.merge;

import com.owr.so.merge.diff.TreesDiffCollections;
import com.owr.so.merge.log.IUIEventsListener;
import com.owr.so.model.DirTreesBundle;

public interface IMergeHandler {
	
	DirTreesBundle loadTrees(IUIEventsListener eventsListener);

	TreesDiffCollections compareTrees(DirTreesBundle dirTrees, IUIEventsListener eventsListener);

	void uiEditDone(TreesDiffCollections diffCollection, DirTreesBundle dirTrees, IUIEventsListener eventsListener);

	void uiEditCanceled();
}
