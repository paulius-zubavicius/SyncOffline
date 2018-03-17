package com.owr.so.merge.edit.console;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import com.owr.so.merge.diff.DiffAction;
import com.owr.so.merge.diff.FileMovedDiff;
import com.owr.so.model.FileEntity;

public class EditMoved extends MenuController {

	private static final String KEY_EXIT = "E";

	public EditMoved(List<FileMovedDiff> src) {

		List<String> menuKeys = new ArrayList<>();

		menuKeys.add(KEY_EXIT);

		String cmd = null;
		List<String> parseFail = null;
		while (!KEY_EXIT.equalsIgnoreCase(cmd)) {

			printContent(src);

			cmd = waitForInput("Menu option: ", menuKeys, true);

			parseFail = executeCommands(cmd, src);
			if (!parseFail.isEmpty()) {
				System.out.println("Failed commands: " + Arrays.toString(parseFail.toArray()));
			}
		}
	}

	private void printContent(List<FileMovedDiff> src) {
		System.out.println("============================================================");
		System.out.println(" Moved / renamed files");
		System.out.println("============================================================");
		System.out.println("[" + KEY_EXIT + "] - Exit;");
		System.out.println("[cmd] - Editing commands. Format: [<number in list><command>]");
		System.out.println();
		System.out.println("Possible comands:");
		System.out.println(" [U]PDATE - The older file will be renamed / moved");
		System.out.println(" [R]EVERT - The newer file will be reverted by older one");
		System.out.println(" [I]GNORE - It will take no action");
		System.out.println();
		System.out.println(" Examples:");
		System.out.println("   21R - 21'th item will be marked as REVERT");
		System.out.println("   3I - 3'th item will be IGNORED");
		System.out.println("============================================================");

		int counter = 0;
		for (FileMovedDiff diff : src) {
			counter++;
			printDiffTitle(counter, diff);
		}

		System.out.println("============================================================");

	}

	private void printDiffTitle(int nr, FileMovedDiff diff) {

		FileEntity f1 = diff.getFile1();
		FileEntity f2 = diff.getFile2();

		System.out.println();
		System.out.println(
				"Diff number: " + nr + " / " + diff.getAction() + "----------------------------------------------");
		System.out.println();

		sysOutFileInfo(f1, diff, nr);
		sysOutFileInfo(f2, diff, nr);

	}

	private void sysOutFileInfo(FileEntity f, FileMovedDiff diff, int nr) {
		System.out.println("  (" + f.getRepoId() + ")" + f.getPath());
		System.out.println("         - Access date: " + convertToHumanReadable(f.getAccessed()));
		System.out.println("         - " + showTheStatus(nr, f, diff));
		System.out.println();

	}

	private String showTheStatus(int nr, FileEntity f, FileMovedDiff diff) {

		if (diff.getFile1().getAccessed() == diff.getFile2().getAccessed()) {
			return "CONFLICT: both files are the same age. Choose this file as a newer with cmd: [" + nr + "T"
					+ f.getRepoId() + "]";
		} else {

			int newer = diff.getFile2().getRepoId();
			int older = diff.getFile1().getRepoId();
			if (diff.getFile1().getAccessed() > diff.getFile2().getAccessed()) {
				newer = diff.getFile1().getRepoId();
				older = diff.getFile2().getRepoId();
			}

			if (f.getRepoId() == older && DiffAction.UPDATE.equals(diff.getAction())) {
				return "This will be UPDATED";
			}

			if (f.getRepoId() == newer && DiffAction.REVERT.equals(diff.getAction())) {
				return "This will be REVERTED";
			}

		}

		return "";
	}

	private String convertToHumanReadable(long accessed) {
		LocalDateTime accessed1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(accessed),
				TimeZone.getDefault().toZoneId());
		return accessed1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	private List<String> executeCommands(String cmdLine, List<FileMovedDiff> src) {

		List<String> failedResult = new ArrayList<>();

		ParsedCmd parsedCmd = null;

		if (StringUtils.isNotBlank(cmdLine)) {

			String cmds[] = cmdLine.split(" ");

			for (String cmd : cmds) {
				parsedCmd = new ParsedCmd(cmd);

				if (parsedCmd.isValid(src.size())) {

					makeChanges(src, parsedCmd, cmd, failedResult);

				} else {
					// Add to skiped
					failedResult.add(cmd);
				}

			}

		} else {
			failedResult.add("<empty cmd>");
		}

		return failedResult;
	}

	private void makeChanges(List<FileMovedDiff> src, ParsedCmd parsedCmd, String originalCmd,
			List<String> failedResult) {
		// Do the action
		// TODO make change in list objects
		if (parsedCmd.isValidTimeStampToutchCmd(src.size())) {
			// TODO patikrint ar tai tikrai IGNORE / CONFLICT
		} else if (parsedCmd.isValidEditCmd(src.size())) {
			// TODO patikrint ar galima tokia komanda naudot
		} else {
			failedResult.add(originalCmd);
		}

	}

}
