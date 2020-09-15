package com.owr.so.merge.gui.model;

import java.nio.file.Path;

import com.owr.so.commons.DataLoader;
import com.owr.so.diff.TreesDiff;
import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.merge.gui.DirTrees;
import com.owr.so.merge.gui.IMergeHandler;
import com.owr.so.merge.gui.args.ArgsValues;
import com.owr.so.merge.gui.edit.console.DiffsEditorCui;
import com.owr.so.merge.gui.log.IUIEventsListener;

public class Merge implements IMergeHandler {

	private ArgsValues argsValues;

	public Merge(ArgsValues argsValues) {

		this.argsValues = argsValues;

//		IDiffsEditor editor = DiffsEditorFactory.getInstance(argsValues.isGuiMode());
		new DiffsEditorCui().runUI(this);

	}

	@Override
	public DirTrees loadTrees(IUIEventsListener eventsListener) {

		DataLoader dl1 = new DataLoader(Path.of(argsValues.getRepoFilePath1()));
		DataLoader dl2 = new DataLoader(Path.of(argsValues.getRepoFilePath2()));

		eventsListener.treesLoaded(argsValues);

		return new DirTrees(dl1.getMeta(), dl2.getMeta());
	}

	@Override
	public DirTreesDiffResult compareTrees(DirTrees dirTrees, IUIEventsListener eventsListener) {

		DirTreesDiffResult diffCollection = new TreesDiff().findDifferences(dirTrees.getMeta1(), dirTrees.getMeta2());
		eventsListener.treesCompared(diffCollection);
		return diffCollection;
	}

	@Override
	public void uiEditDone(DirTreesDiffResult diffCollection, DirTrees dirTrees, IUIEventsListener eventsListener) {
		// TODO Auto-generated method stub

		// auto merge (renamed dir only)

		// output module

		System.out.println("Done");
	}

	@Override
	public void uiEditCanceled() {
		System.out.println("Canceled. Exiting...");
		System.exit(0);
	}

}
