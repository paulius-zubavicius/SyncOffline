package com.owr.so.merge.gui.model;

import com.owr.so.commons.DirTreeEntityLoader;
import com.owr.so.diff.TreesDiff;
import com.owr.so.merge.gui.DirTrees;
import com.owr.so.merge.gui.IMergeHandler;
import com.owr.so.merge.gui.args.ArgsValues;
import com.owr.so.merge.gui.args.InputValidator;
import com.owr.so.merge.gui.edit.DiffsEditorFactory;
import com.owr.so.merge.gui.edit.IDiffsEditor;
import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.merge.gui.log.IUIEventsListener;

public class Merge implements IMergeHandler {

	private static final int REPO_ID_1 = 1;
	private static final int REPO_ID_2 = 2;

	private ArgsValues argsValues;

	public Merge(ArgsValues argsValues) {

		this.argsValues = argsValues;

		IDiffsEditor editor = DiffsEditorFactory.getInstance(argsValues.isGuiMode());
		editor.runUI(this);

	}

	@Override
	public DirTrees loadTrees(IUIEventsListener eventsListener) {

		DirTrees result = new DirTrees();
		String subDir;

		subDir = argsValues.getSubDir1();
		result.setTree1(DirTreeEntityLoader.load(argsValues.getMetaFile1Path(), REPO_ID_1));
		if (subDir != null) {
			InputValidator.validateSubDirAfterLoading(result.getTree1(), subDir, REPO_ID_1);
			result.getTree1().setSubDir(subDir);
		}

		subDir = argsValues.getSubDir2();
		result.setTree2(DirTreeEntityLoader.load(argsValues.getMetaFile2Path(), REPO_ID_2));
		if (subDir != null) {
			InputValidator.validateSubDirAfterLoading(result.getTree2(), subDir, REPO_ID_2);
			result.getTree2().setSubDir(subDir);
		}

		eventsListener.treesLoaded(argsValues);

		return result;
	}

	@Override
	public DirTreesDiffResult compareTrees(DirTrees dirTrees, IUIEventsListener eventsListener) {

		TreesDiff diff = new TreesDiff();
		DirTreesDiffResult diffCollection = diff.findDifferences(dirTrees.getTree1(), dirTrees.getTree2());

		eventsListener.treesCompared(diffCollection);

		return diffCollection;
	}

	@Override
	public void uiEditDone(DirTreesDiffResult diffCollection, DirTrees dirTrees,
						   IUIEventsListener eventsListener) {
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
