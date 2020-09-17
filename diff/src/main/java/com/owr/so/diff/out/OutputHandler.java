package com.owr.so.diff.out;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.owr.so.commons.ConvertUtil;
import com.owr.so.commons.DataLoader;
import com.owr.so.diff.model.DirTreesDiffResult;

public class OutputHandler implements IOutputHandler {

	private List<IDiffOutput> outs = Arrays.asList(new ConsoleOut(), new ConsoleShellOut());

	@Override
	public void treesLoaded(DataLoader dl1, DataLoader dl2) {
		LocalDateTime currentTime = LocalDateTime.now();
		printMetaFileInfo(currentTime, dl1);
		printMetaFileInfo(currentTime, dl2);
	}

	@Override
	public void treesCompared(DirTreesDiffResult treeDiffs) {

		outs.forEach(out -> out.outModified(treeDiffs.getModifiedFiles()));
		outs.forEach(out -> out.outMoved(treeDiffs.getMovedFiles()));
		outs.forEach(out -> out.outNew(treeDiffs.getNewFiles()));
		outs.forEach(out -> out.outDuplicates(treeDiffs.getDuplicates()));

		summary(treeDiffs);

	}

	private void printMetaFileInfo(LocalDateTime currentTime, DataLoader dl1) {
		System.out.println("Repo file : " + dl1.getRepoFilePath());
		System.out.println("Data file : " + dl1.getDataFilePath());
		System.out.println("Age       : "
				+ ConvertUtil.getTimeInHumanFormat(Duration.between(dl1.getRepoData().getLastScan(), currentTime)));
		System.out.println();
	}

	private void summary(DirTreesDiffResult treeDiffs) {
		System.out.println("============================[Summary]============================");
		System.out.println();
		System.out.println("Modified files................. " + treeDiffs.getModifiedFiles().size() + " changes");
		System.out.println("Moved/Renamed files............ " + treeDiffs.getMovedFiles().size() + " changes");
		System.out.println("New files...................... " + treeDiffs.getNewFiles().size() + " changes");
		System.out.println("Conflicted & dublicated files.. " + treeDiffs.getDuplicates().size() + " changes");
	}

}
