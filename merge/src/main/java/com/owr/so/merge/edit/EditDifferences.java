package com.owr.so.merge.edit;

import com.owr.so.merge.diff.TreesDiffCollections;
import com.owr.so.merge.edit.console.EditDiffsCui;
import com.owr.so.model.DirTreeEntity;

public class EditDifferences {

	public void edit(TreesDiffCollections treeDiffs, DirTreeEntity tree1, DirTreeEntity tree2, boolean guiMode) {

		// Will run on current thread

		if (guiMode) {
			// Not implemented
			// EditDiffsGui gui = new EditDiffsGui();

		}

		EditDiffsCui cui = new EditDiffsCui();
		cui.edit(treeDiffs, tree1, tree2);

	}

}
