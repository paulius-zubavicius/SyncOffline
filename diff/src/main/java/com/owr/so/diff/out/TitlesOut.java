package com.owr.so.diff.out;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.owr.so.diff.model.RepoDiff;
import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;

public class TitlesOut implements IDiffOutput {

	private static Map<Class<? extends RepoDiff>, String> titleByClass;

	static {
		titleByClass = new HashMap<>();
		titleByClass.put(FileModifiedDiff.class, "Modified files");
		titleByClass.put(FileMovedDiff.class, "Moved files");
		titleByClass.put(FileNewDiff.class, "New files");
		titleByClass.put(FileDuplicatesDiff.class, "Duplicates");
	}

	@Override
	public <T extends RepoDiff> void out(Class<T> type, List<T> data, Map<String, String> rootPathByRepoName) {
		System.out.println("================================[" + titleByClass.getOrDefault(type, "???")
				+ "]================================");
	}

}
