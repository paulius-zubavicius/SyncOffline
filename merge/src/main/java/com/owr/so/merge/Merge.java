package com.owr.so.merge;

import com.owr.so.commons.DirTreeEntityLoader;
import com.owr.so.merge.args.ArgsValues;
import com.owr.so.merge.args.InputValidator;
import com.owr.so.merge.diff.ReposDifferences;
import com.owr.so.merge.diff.TreesDiffCollections;
import com.owr.so.merge.edit.DiffsEditorFactory;
import com.owr.so.merge.edit.IDiffsEditor;
import com.owr.so.merge.log.IUIEventsListener;
import com.owr.so.model.DirTreesBundle;

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
	public DirTreesBundle loadTrees(IUIEventsListener eventsListener) {

		DirTreesBundle result = new DirTreesBundle();
		String subDir;

		subDir = argsValues.getSubdir1();
		result.setTree1(DirTreeEntityLoader.load(argsValues.getMetaFile1Path(), REPO_ID_1));
		if (subDir != null) {
			InputValidator.validateSubDirAfterLoading(result.getTree1(), subDir, REPO_ID_1);
			result.getTree1().setSubDir(subDir);
		}

		subDir = argsValues.getSubdir2();
		result.setTree2(DirTreeEntityLoader.load(argsValues.getMetaFile2Path(), REPO_ID_2));
		if (subDir != null) {
			InputValidator.validateSubDirAfterLoading(result.getTree2(), subDir, REPO_ID_2);
			result.getTree2().setSubDir(subDir);
		}

		eventsListener.treesLoaded(argsValues);

		return result;
	}

	@Override
	public TreesDiffCollections compareTrees(DirTreesBundle dirTrees, IUIEventsListener eventsListener) {

		ReposDifferences diff = new ReposDifferences();
		TreesDiffCollections diffCollection = diff.treesDifferences(dirTrees.getTree1(), dirTrees.getTree2());

		eventsListener.treesCompared(diffCollection);

		return diffCollection;
	}

	@Override
	public void uiEditDone(TreesDiffCollections diffCollection, DirTreesBundle dirTrees,
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
