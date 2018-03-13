package com.owr.so.merge.edit.console;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.owr.so.merge.diff.DiffAction;
import com.owr.so.merge.diff.FileMovedDiff;
import com.owr.so.model.FileEntity;

public class EditMoved extends MenuController {

	private static final String KEY_EXIT = "E";

	public EditMoved(List<FileMovedDiff> src) {

		List<String> menuKeys = new ArrayList<>();

		menuKeys.add(KEY_EXIT);

		String keyInput = null;
		while (!KEY_EXIT.equalsIgnoreCase(keyInput)) {

			printContent(src);

			keyInput = waitForInput("Menu option: ", menuKeys);

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
		System.out.println("[" + nr + "] / " + diff.getAction() + "----------------------------------------------");
		System.out.println();
		System.out.println("  (" + f1.getRepoId() + ")");
		System.out.println("   - " + showTheStatus(nr, f1, diff));
		System.out.println("   - Access date: " + convertToHumanReadable(f1.getAccessed()));
		System.out.println("   - " + f1.getPath());
		System.out.println();
		System.out.println("  (" + f2.getRepoId() + ")");
		System.out.println("   - " + showTheStatus(nr, f2, diff));
		System.out.println("   - Access date: " + convertToHumanReadable(f2.getAccessed()));
		System.out.println("   - " + f2.getPath());
		System.out.println();

		// if (diff.getFile1().getAccessed() == diff.getFile2().getAccessed()) {
		// System.out.println(" Other actions:");
		// System.out.println(
		// " CONFLICT: The timestamps of access are the same. To solve conflict choose
		// newer file with command: ");
		// System.out.println(" [" + nr + "T1] - Newer file in repo (1) ");
		// System.out.println(" [" + nr + "T2] - Newer file in repo (2) ");
		// }

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

			// System.out.println(
			// activeAction(DiffAction.UPDATE, diff.getAction()) + ": The older (" + older +
			// ") will be updated.");
			// System.out.println(activeAction(DiffAction.REVERT, diff.getAction()) + ": The
			// newer (" + newer
			// + ") will be reverted.");
			// System.out.println(activeAction(DiffAction.IGNORE, diff.getAction()) + ": It
			// will take no action.");
		}

		return "";
	}

	// private String activeAction(DiffAction lineAction, DiffAction action) {
	// return (lineAction.equals(action) ? " * " +
	// lineAction.toString().toUpperCase()
	// : " " + lineAction.toString().toLowerCase());
	// }

	private String convertToHumanReadable(long accessed) {
		LocalDateTime accessed1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(accessed),
				TimeZone.getDefault().toZoneId());
		return accessed1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

}
