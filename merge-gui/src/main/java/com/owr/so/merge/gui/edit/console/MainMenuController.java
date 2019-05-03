package com.owr.so.merge.gui.edit.console;

import java.util.ArrayList;
import java.util.List;

import com.owr.so.diff.model.DirTreesDiffResult;

public class MainMenuController {

	private static final String KEY_1 = "1";
	private static final String KEY_2 = "2";
	private static final String KEY_3 = "3";
	private static final String KEY_4 = "4";
	private static final String KEY_5 = "5";

	private int modified;
	private int moved;
	private int newFiles;
	private int newDirs;
	private int duplicates;
	
	private DirTreesDiffResult treeDiffs;

	public MainMenuController(DirTreesDiffResult treeDiffs) {
		
		
		this.treeDiffs = treeDiffs;
		
		modified = treeDiffs.getModifiedFiles().size();
		moved = treeDiffs.getMovedFiles().size();
		newFiles = treeDiffs.getNewFiles().size();
		newDirs = treeDiffs.getNewDirs().size();
		duplicates = treeDiffs.getDuplicates().size();
	}

	public void printMainMenu() {

		System.out.println(formtMenuLineStr(1, "Modified files................. ", modified));
		System.out.println(formtMenuLineStr(2, "Moved/Renamed files............ ", moved));
		System.out.println(formtMenuLineStr(3, "New files...................... ", newFiles));
		System.out.println(formtMenuLineStr(4, "New direrctories............... ", newDirs));
		System.out.println(formtMenuLineStr(5, "Conflicted & dublicated files.. ", duplicates));

	}

	public void executeCommand(String keyInput) {
		if (isModified() && KEY_1.equals(keyInput)) {
			new EditModified(treeDiffs.getModifiedFiles());
		}

		if (isMoved() && KEY_2.equals(keyInput)) {
			new EditMoved(treeDiffs.getMovedFiles());
		}

		if (isNewFiles() && KEY_3.equals(keyInput)) {
			System.err.println("Not implemented");
		}

		if (isNewDirs() && KEY_4.equals(keyInput)) {
			System.err.println("Not implemented");
		}

		if (isDuplicates() && KEY_5.equals(keyInput)) {
			System.err.println("Not implemented");
		}
	}

	public List<String> createMainMenu() {
		List<String> mainMenu = new ArrayList<>();

		if (isModified()) {
			mainMenu.add(KEY_1);
		}
		if (isMoved()) {
			mainMenu.add(KEY_2);
		}
		if (isNewFiles()) {
			mainMenu.add(KEY_3);
		}
		if (isNewDirs()) {
			mainMenu.add(KEY_4);
		}
		if (isDuplicates()) {
			mainMenu.add(KEY_5);
		}
		return mainMenu;
	}

	private boolean isModified() {
		return modified > 0;
	}

	private boolean isMoved() {
		return moved > 0;
	}

	private boolean isNewFiles() {
		return newFiles > 0;
	}

	private boolean isNewDirs() {
		return newDirs > 0;
	}

	private boolean isDuplicates() {
		return duplicates > 0;
	}

	private String formtMenuLineStr(int i, String menuStr, int listSize) {

		String result = "";

		if (listSize < 1) {
			result = "[ ] ";
			result += menuStr;
			result += "";
		} else {
			result += "[" + i + "] ";
			result += menuStr;
			result += listSize + " change" + (listSize > 1 ? "s" : "");
		}

		return result;
	}

}
