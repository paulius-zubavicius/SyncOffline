package com.owr.so.diff.out;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.owr.so.commons.ConvertUtil;
import com.owr.so.commons.DataLoader;
import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.diff.model.RepoMetaData;
import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;

public class OutputHandler implements IOutputHandler {

	private List<IDiffOutput> outs = Arrays.asList(new TitlesOut(), new ConsoleOut(), new ConsoleShellOut());

	@Override
	public void treesLoaded(DataLoader dl1, DataLoader dl2) {
		LocalDateTime currentTime = LocalDateTime.now();
		printMetaFileInfo(currentTime, dl1);
		printMetaFileInfo(currentTime, dl2);
	}

	@Override
	public void treesCompared(DirTreesDiffResult treeDiffs, RepoMetaData meta1, RepoMetaData meta2) {

		Map<String, String> rootPathByRepoName = new HashMap<>();
		rootPathByRepoName.put(meta1.getRepoName(), meta1.getRepoRootDir());
		rootPathByRepoName.put(meta2.getRepoName(), meta2.getRepoRootDir());

		if (!treeDiffs.getModifiedFiles().isEmpty())
			outs.forEach(out -> out.out(FileModifiedDiff.class, treeDiffs.getModifiedFiles(), rootPathByRepoName));
		if (!treeDiffs.getMovedFiles().isEmpty())
			outs.forEach(out -> out.out(FileMovedDiff.class, treeDiffs.getMovedFiles(), rootPathByRepoName));
		if (!treeDiffs.getNewFiles().isEmpty())
			outs.forEach(out -> out.out(FileNewDiff.class, treeDiffs.getNewFiles(), rootPathByRepoName));
		if (!treeDiffs.getDuplicates().isEmpty())
			outs.forEach(out -> out.out(FileDuplicatesDiff.class, treeDiffs.getDuplicates(), rootPathByRepoName));

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
