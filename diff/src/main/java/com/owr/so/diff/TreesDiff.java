package com.owr.so.diff;

import java.util.List;

import com.owr.so.diff.collector.DiffFilter;
import com.owr.so.diff.collector.TreesDiffCollector;
import com.owr.so.diff.collector.dirmove.DirMoveFilter;
import com.owr.so.diff.collector.similar.img.SimilarImgFilter;
import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.diff.model.RepoMetaData;

/**
 * @author Paulius Zubavicius
 *
 *         Finds and collects differences of tree's
 */
public class TreesDiff {

	private static final List<DiffFilter> filters = List.of(new SimilarImgFilter(), new DirMoveFilter());

	/**
	 * @param tree1
	 * @param tree2
	 *
	 */
	public DirTreesDiffResult findDifferences(RepoMetaData tree1, RepoMetaData tree2) {

		DirTreesDiffResult diffsResult = new DirTreesDiffResult();

		new TreesDiffCollector().collect(tree1, tree2, diffsResult);
		filters.forEach(diffCollector -> diffCollector.apply(tree1, tree2, diffsResult));

		return diffsResult;
	}
}
