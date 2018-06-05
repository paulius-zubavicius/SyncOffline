package com.owr.so.merge.edit;

import com.owr.so.merge.edit.console.DiffsEditorCui;
import com.owr.so.merge.edit.gui.DiffsEditorGui;

public class DiffsEditorFactory {

	public static IDiffsEditor getInstance(boolean guiMode) {
		if (guiMode) {
			return new DiffsEditorGui();
		} else {
			return new DiffsEditorCui();
		}

	}

}
