package com.owr.so.merge.edit.console;

import org.apache.commons.lang3.StringUtils;

import com.owr.so.merge.diff.DiffAction;

public class ParsedCmd {

	private Integer itemNr;
	private DiffAction itemAction;
	private Boolean touthAction;
	private Integer repoId;

	private boolean unparsedCmd = false;

	public ParsedCmd(String cmd) {
		cmd = StringUtils.trimToNull(cmd);
		if (cmd != null) {

			parse(cmd);

		} else {
			unparsedCmd = true;
		}
	}

	public boolean isValid(int itemsNrMax) {
		if (isUnparsedCmd()) {
			return false;
		}

		return isValidTimeStampToutchCmd(itemsNrMax) ^ isValidEditCmd(itemsNrMax);
	}

	public boolean isValidTimeStampToutchCmd(int itemsNrMax) {

		if (!Boolean.TRUE.equals(touthAction)) {
			return false;
		}

		if (itemAction != null) {
			return false;
		}

		if (itemNr == null || repoId == null) {
			return false;
		}

		if (itemsNrMax < itemNr || 1 > itemNr) {
			return false;
		}

		if (2 < repoId || 1 > repoId) {
			return false;
		}

		return true;

	}

	public boolean isValidEditCmd(int itemsNrMax) {
		if (touthAction != null) {
			return false;
		}

		if (itemAction == null || itemNr == null || itemAction == null) {
			return false;
		}

		if (itemsNrMax < itemNr || 1 > itemNr) {
			return false;
		}

		return true;
	}

	public boolean isUnparsedCmd() {
		return unparsedCmd;
	}

	private void parse(String cmd) {

		cmd = cutNrFromBeginning(cmd);
		cmd = cutCommand(cmd);
		cmd = cutRepoNr(cmd);

		if (cmd.length() > 0) {
			unparsedCmd = true;
		}

	}

	private String cutRepoNr(String cmd) {
		int cmdLenght = cmd.length();
		if (cmdLenght > 0) {
			if (StringUtils.isNumeric("" + cmd.charAt(0))) {
				repoId = Integer.valueOf(cmd.substring(0, 1));
				return cmd.substring(1, cmdLenght);
			}
		}

		return cmd;
	}

	private String cutCommand(String cmd) {
		int cmdLenght = cmd.length();
		if (cmdLenght > 0) {
			String currChar = "" + cmd.charAt(0);

			if (StringUtils.containsAny(currChar, "U", "u")) {
				itemAction = DiffAction.UPDATE;
			} else if (StringUtils.containsAny(currChar, "R", "r")) {
				itemAction = DiffAction.REVERT;
			} else if (StringUtils.containsAny(currChar, "I", "i")) {
				itemAction = DiffAction.IGNORE;
			} else if (StringUtils.containsAny(currChar, "T", "t")) {
				touthAction = true;
			}

			return cmd.substring(1, cmdLenght);
		}

		return cmd;

	}

	private String cutNrFromBeginning(String cmd) {

		int cmdLenght = cmd.length();

		for (int i = 0; i < cmdLenght; i++) {

			String currChar = "" + cmd.charAt(i);

			if (StringUtils.isNumeric(currChar)) {

			} else {
				itemNr = Integer.valueOf(cmd.substring(0, i));
				return cmd.substring(i, cmdLenght);
			}
		}

		return cmd;
	}

	public Integer getItemNr() {
		return itemNr;
	}

	public DiffAction getItemAction() {
		return itemAction;
	}

	public Integer getRepoId() {
		return repoId;
	}

	public Boolean getTouthAction() {
		return touthAction;
	}

}