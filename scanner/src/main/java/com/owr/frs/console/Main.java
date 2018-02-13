package com.owr.frs.console;

import java.io.IOException;
import java.net.URISyntaxException;

import com.owr.frs.scanner.Scanner;

public class Main {

	private static final String LIST = "list";
	private static final String ADD = "add";
	private static final String SCAN = "scan";
	private static final String UPDATE = "update";
	private static final String REMOVE = "remove";

	public static void main(String[] args) throws IOException, URISyntaxException {

		if (args.length > 0) {

			Scanner scn = new Scanner();

			if (LIST.equalsIgnoreCase(args[0])) {
				ConsoleOutput.repoListOutput(scn.getRepolist());
				ConsoleOutput.printDone(LIST);
				return;
			}

			if (ADD.equalsIgnoreCase(args[0])) {

				// FIXME Fix: path with spaces
				String path = "";
				for (int i = 2; i < args.length; i++) {
					path += args[i];
				}

				scn.addRepo(args[1], path);
				ConsoleOutput.printDone(args[1] + " : " + path);
				return;
			}

			if (REMOVE.equalsIgnoreCase(args[0])) {
				scn.removeRepo(args[1]);
				ConsoleOutput.printDone(args[1]);
				return;
			}

			if (SCAN.equalsIgnoreCase(args[0])) {
				scn.scanFilesRepo(args[1], true);
				ConsoleOutput.printDone(args[1]);
				return;
			}

			if (UPDATE.equalsIgnoreCase(args[0])) {
				scn.scanFilesRepo(args[1], false);
				ConsoleOutput.printDone(args[1]);
				return;
			}
		}
		ConsoleOutput.printConsoleHelp();
	}
}
