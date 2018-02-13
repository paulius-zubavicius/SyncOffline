package com.owr.fr.merge.console;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import com.owr.fr.merge.data.MergedRepos;
import com.owr.fr.merge.data.SimpleDiffComparator;
import com.owr.fr.merge.diff.ConflictDiff;
import com.owr.fr.merge.diff.ConflictReason;
import com.owr.fr.merge.diff.Operation;
import com.owr.fr.merge.diff.SimpleDiff;
import com.owr.frs.model.FileMeta;
import com.owr.frs.model.Version;

public class ConsoleOutput {

	public static void printConsoleHelp() {

		System.out.println();
		System.out.println(" Files Repo Merge (data model version: " + Version.version + ")");
		System.out.println();
		System.out.println(" Usage:");
		System.out.println(" java -jar merge.jar repoName1 repoName2");
		System.out.println();

	}

	public static void repoNotFound(String repoName) {
		System.out.println("Warning: Repo not found: [" + repoName + "]");

	}

	public static void resultPrint(MergedRepos result) {

		List<SimpleDiff> diffs = result.getDiffList();

		Collections.sort(diffs, new SimpleDiffComparator());

		System.out.println("Result:");

		for (int i = 0; i < diffs.size(); i++) {
			outDiff(diffs.get(i), i);
		}

		// Conflicts
		List<ConflictDiff> diffConf = result.getConflicts();

		for (int i = 0; i < diffConf.size(); i++) {
			outConflictDiff(diffConf.get(i), i);
		}

	}

	private static void outConflictDiff(ConflictDiff c, int sequenceId) {

		System.out.println(" --- " + (sequenceId));

		ConflictReason reason = c.getReason();

		switch (reason) {
		case SameHashes:
			System.out.println(
					" File was moved/renamed but it dificult to determinate new path because there are more than one file with identical content.");
			System.out.println(" md5: " + c.getFml().get(0).getMd5());
			System.out.println(" Files: ");
			for (FileMeta fileMeta : c.getFml()) {
				System.out.println(" [" + fileMeta.getRepo() + "] : " + fileMeta.getPath());
			}
			break;

		case SameAccessed:
			System.out.println(" File was moved/renamed but the 'accessed' value the same.");
			System.out.println(" accessed: " + LocalTime.ofNanoOfDay(c.getFml().get(0).getAccessed()));
			System.out.println(" Files: ");
			for (FileMeta fileMeta : c.getFml()) {
				System.out.println(" [" + fileMeta.getRepo() + "] : " + fileMeta.getPath());
			}
			break;
		case SameModified:
			System.out.println(" File was modified but the 'modified' value the same.");
			System.out.println(" modified: " + LocalTime.ofNanoOfDay(c.getFml().get(0).getModified()));
			System.out.println(" Files: ");
			for (FileMeta fileMeta : c.getFml()) {
				System.out.println(" [" + fileMeta.getRepo() + "] : " + fileMeta.getPath());
			}
			break;
		default:
			throw new RuntimeException();
		}

	}

	private static void outDiff(SimpleDiff diff, int sequenceId) {

		Operation op = diff.getOperation();

		System.out.println(" --- " + (sequenceId));

		switch (op) {
		case Modified:
			outModified(diff);
			break;
		case Move:
			outMoved(diff);
			break;
		case Add:
		case Delete:
			outAdd(diff);
		default:

		}

	}

	private static void outModified(SimpleDiff diff) {
		System.out.println("Copy : [" + diff.getSource().getRepo() + "] -> [" + diff.getTarget().getRepo() + "]");
		System.out.println("     : " + diff.getSource().getPath());
	}

	private static void outMoved(SimpleDiff diff) {
		System.out.println("Move  : [" + diff.getTarget().getRepo() + "]");
		System.out.println(" from : " + diff.getSource().getPath());
		System.out.println(" to   : " + diff.getTarget().getPath());

	}

	private static void outAdd(SimpleDiff diff) {
		System.out.println("AddNew : [" + diff.getSource().getRepo() + "] -> [" + diff.getTarget().getRepo() + "]");
		System.out.println("       : " + diff.getSource().getPath());
	}

}
