package com.owr.so.diff.out.options;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.owr.so.diff.model.RepoDiff;
import com.owr.so.diff.model.ReposRootPaths;
import com.owr.so.diff.model.diffs.DirMovedDiff;
import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;
import com.owr.so.diff.out.IDiffOutput;

public class TitlesOut implements IDiffOutput {

	private static Map<Class<? extends RepoDiff>, String> titleByClass;

	static {
		titleByClass = new HashMap<>();
		titleByClass.put(FileModifiedDiff.class, "Modified files");
		titleByClass.put(FileMovedDiff.class, "Moved files");
		titleByClass.put(DirMovedDiff.class, "Moved directories");
		titleByClass.put(FileNewDiff.class, "New files");
		titleByClass.put(FileDuplicatesDiff.class, "Duplicates");
	}

	@Override
	public <T extends RepoDiff> void out(Class<T> type, List<T> data, ReposRootPaths rootPathByRepoName) {
		System.out.println("================================[" + titleByClass.getOrDefault(type, "???")
				+ "]================================");
	}

}
