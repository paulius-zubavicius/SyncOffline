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
import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.RepoMetaData;
import com.owr.so.diff.model.diffs.DirMovedDiff;
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
		if (!treeDiffs.getMovedDirs().isEmpty())
			outs.forEach(out -> out.out(DirMovedDiff.class, treeDiffs.getMovedDirs(), rootPathByRepoName));
		if (!treeDiffs.getNewFiles().isEmpty())
			outs.forEach(out -> out.out(FileNewDiff.class, treeDiffs.getNewFiles(), rootPathByRepoName));
		if (!treeDiffs.getDuplicates().isEmpty())
			outs.forEach(out -> out.out(FileDuplicatesDiff.class, treeDiffs.getDuplicates(), rootPathByRepoName));

		summary(treeDiffs);
	}

	private void printMetaFileInfo(LocalDateTime currentTime, DataLoader dl1) {

		System.out.println("Name      : " + dl1.getMeta().getRepoName());
		System.out.println("Path      : " + dl1.getRepoFile().getPath());
		System.out.println("Age       : "
				+ ConvertUtil.getTimeInHumanFormat(Duration.between(dl1.getRepoData().getLastScan(), currentTime)));

		long readSize = 0;
		for (FileEntityWrapper ent : dl1.getMeta().getFiles()) {
			readSize += ent.getFile().getSize();
		}
		System.out.println("Size      : " + ConvertUtil.getSizeInHumanFormat(readSize) + " ("
				+ dl1.getMeta().getFiles().size() + " files)");
		if (null != dl1.getRepoFile().getExcludes() && !dl1.getRepoFile().getExcludes().isEmpty()) {
			dl1.getRepoFile().getExcludes().forEach((k, v) -> System.out.println("Exludes   : " + k + " [" + v + "]"));
		}
		System.out.println();
	}

	private void summary(DirTreesDiffResult treeDiffs) {
		System.out.println("============================[Summary]============================");
		System.out.println();
		System.out.println("Modified files................. " + treeDiffs.getModifiedFiles().size());
		System.out.println("Moved/Renamed files............ " + treeDiffs.getMovedFiles().size());
		System.out.println("Moved/Renamed dirs............. " + treeDiffs.getMovedDirs().size());
		System.out.println("New files...................... " + treeDiffs.getNewFiles().size());
		System.out.println("Conflicted & dublicated files.. " + treeDiffs.getDuplicates().size());
	}

}
