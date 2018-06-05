package com.owr.so.merge.edit.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.owr.so.merge.IMergeHandler;
import com.owr.so.merge.diff.TreesDiffCollections;
import com.owr.so.merge.edit.IDiffsEditor;
import com.owr.so.merge.log.IUIEventsListener;
import com.owr.so.merge.log.UIDefaultEventsListener;
import com.owr.so.model.DirTreesBundle;

public class DiffsEditorCui implements IDiffsEditor {

	private static final String KEY_QUIT = "Q";
	private static final String KEY_GEN = "G";

	private IUIEventsListener eventsListener = new UIDefaultEventsListener();

	@Override
	public void runUI(IMergeHandler mergeHandler) {
		System.out.println("Collecting diferences of files trees...");

		DirTreesBundle dirTreesBundle = mergeHandler.loadTrees(eventsListener);

		TreesDiffCollections treesDiffs = mergeHandler.compareTrees(dirTreesBundle, eventsListener);

		printClear(15);

		MainMenuController menu = new MainMenuController(treesDiffs);

		String keyInput = null;

		List<String> menuKeys = menu.createMainMenu();
		menuKeys.add(KEY_GEN);
		menuKeys.add(KEY_QUIT);

		while (!KEY_QUIT.equalsIgnoreCase(keyInput)) {

			printMainMenu(menu);

			keyInput = waitForInput("Menu option: ", menuKeys);

			menu.executeCommand(keyInput);

		}

	}

	private void printMainMenu(MainMenuController menu) {
		System.out.println("-------------------------------------------");
		System.out.println("Main menu:");
		// System.out.println("[0] This menu");
		menu.printMainMenu();
		System.out.println("");
		System.out.println("[" + KEY_QUIT + "] Quit");
		System.out.println("[" + KEY_GEN + "] Generate scripts");
		System.out.println("-------------------------------------------");
	}

	private String waitForInput(String waitingLineStr, List<String> possibleValues) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(waitingLineStr);
		String input = null;
		try {
			input = br.readLine();
		} catch (IOException e) {
			System.out.println("Wrong command" + e.getMessage());
		}

		for (String correctVal : possibleValues) {
			if (correctVal.equalsIgnoreCase(input)) {
				return correctVal;
			}
		}

		System.out.println(
				"Wrong command [" + input + "]. Available options: " + Arrays.toString(possibleValues.toArray()));

		return null;
	}

	private void printClear(int lines) {
		// System.out.print("\033[H\033[2J");
		for (int i = 0; i < lines; i++) {
			System.out.println();
		}
	}

}
