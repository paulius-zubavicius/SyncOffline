package com.owr.so.diff.out.backup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.owr.so.diff.model.diffs.DirMovedDiff;
import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;
import com.owr.so.diff.out.IDiffOutput;
import com.owr.so.diff.out.OutputHandler;

public class BackupOut extends OutputHandler {
	
	

	// TODO repo1 -> repo2 : shell commands only
	private List<IDiffOutput> outs = Arrays.asList(new ForceUpdateShellOut());

	@Override
	public List<IDiffOutput> getDiffsOutputs() {
		
	}

}
