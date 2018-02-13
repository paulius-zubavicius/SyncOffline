package com.owr.so.merge.output;

import static com.owr.so.console.Const.ANSI_BLUE;
import static com.owr.so.console.Const.ANSI_GREEN;
import static com.owr.so.console.Const.ANSI_RED;
import static com.owr.so.console.Const.ANSI_RESET;
import static com.owr.so.console.Const.ANSI_YELLOW;

import com.owr.so.merge.diff.Operation;
import com.owr.so.merge.diff.RepoFile;
import com.owr.so.merge.diff.SimpleDiff;

public class Output {

	public String toString(SimpleDiff diff) {

		Operation op = diff.getOperation();
		RepoFile src = diff.getSource();
		RepoFile trg = diff.getTarget();

		int repoLen = Math.max(getRepoStrLen(src), getRepoStrLen(trg));

		switch (op) {
		case Modified:
			return String.format(ANSI_GREEN + "%s " + repoLen + "%s" + ANSI_RESET + " %s", op.getUISymbol(),
					trg.getRepo(), trg.getPath());

		case Move:
			return String.format(ANSI_BLUE + "%s " + repoLen + "%s" + ANSI_RESET + " %s", op.getUISymbol(),
					trg.getRepo(), trg.getPath());

		case Add:
			return String.format(ANSI_YELLOW + "%s " + repoLen + "%s" + ANSI_RESET + " %s", op.getUISymbol(),
					trg.getRepo(), trg.getPath());

		case Delete:
			return String.format(ANSI_RED + "%s " + repoLen + "%s" + ANSI_RESET + " %s", op.getUISymbol(),
					trg.getRepo(), trg.getPath());
		}

		return null;
	}

	private int getRepoStrLen(RepoFile rf) {
		if (rf != null && rf.getRepo() != null) {
			return rf.getRepo().length();
		}
		return 0;
	}

}
