package com.owr.so.diff.filters;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import com.owr.so.diff.model.DirEntity;
import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.RepoMetaData;
import com.owr.so.diff.model.diffs.DirMovedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;

public class MovedDirFilter implements DiffFilter {

	@Override
	public void apply(RepoMetaData tree1, RepoMetaData tree2, DirTreesDiffResult diffResult) {

		if (diffResult.getMovedFiles().isEmpty()) {
			return;
		}

		Map<String, List<FileMovedDiff>> diffPool1 = diffResult.getMovedFiles().stream()
				// .map(diff -> diff.getFile1())
				// .collect(groupingBy(wr -> wr.getRelativeDirPath()));
				.collect(groupingBy(diff -> diff.getFile1().getRelativeDirPath()));

//		Map<String, List<FileEntityWrapper>> diffPool2 = diffResult.getMovedFiles().stream()
//				.map(diff -> diff.getFile2()).collect(groupingBy(wr -> wr.getRelativeDirPath()));

		Map<String, List<FileEntityWrapper>> pool1 = tree1.getFiles().stream()
				.collect(groupingBy(wr -> wr.getRelativeDirPath()));
//		Map<String, List<FileEntityWrapper>> pool2 = tree2.getFiles().stream()
//				.collect(groupingBy(wr -> wr.getRelativeDirPath()));

		Set<DirMovedDiff> renamedDirs = findSameDirsContent(diffPool1, pool1);

		diffResult.getMovedFiles().removeIf(oneOf(renamedDirs));
		diffResult.getMovedDirs().addAll(renamedDirs);

	}

	private Predicate<? super FileMovedDiff> oneOf(Set<DirMovedDiff> renamedDirs) {
		return fileMovedDiff -> {
			Optional<DirMovedDiff> optDirDiff = renamedDirs.stream().filter(dirDiff -> dirDiff.getDir1()
					.getRelativeDirPath().equals(fileMovedDiff.getFile1().getRelativeDirPath())).findAny();

			if (optDirDiff.isPresent()) {
				return true;
			}

			optDirDiff = renamedDirs.stream().filter(dirDiff -> dirDiff.getDir2().getRelativeDirPath()
					.equals(fileMovedDiff.getFile2().getRelativeDirPath())).findAny();

			return optDirDiff.isPresent();
		};
	}

	private Set<DirMovedDiff> findSameDirsContent(Map<String, List<FileMovedDiff>> diffPool1,
			Map<String, List<FileEntityWrapper>> pool1) {
		Set<DirMovedDiff> result = new HashSet<>();
		List<FileEntityWrapper> empty = new ArrayList<>();
		diffPool1.forEach((key, list) -> {

			if (list.size() == pool1.getOrDefault(key, empty).size()) {
				DirEntity dir1 = new DirEntity(list.get(0).getFile1().getRelativeDirPath(),
						list.get(0).getFile1().getRepoName());
				DirEntity dir2 = new DirEntity(list.get(0).getFile2().getRelativeDirPath(),
						list.get(0).getFile2().getRepoName());

				result.add(new DirMovedDiff(dir1, dir2));
			}

		});

		return result;

	}

}
