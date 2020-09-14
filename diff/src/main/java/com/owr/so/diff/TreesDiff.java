package com.owr.so.diff;

import java.util.List;

import com.owr.so.diff.collector.DiffCollector;
import com.owr.so.diff.collector.TreesDiffCollector;
import com.owr.so.diff.collector.dirmove.DirMoveDetector;
import com.owr.so.diff.collector.similar.img.SimilarImgDetector;
import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.diff.model.RepoData;

/**
 * @author Paulius Zubavicius
 *
 *         Finds and collects differences of tree's
 */
public class TreesDiff {


	private static final List<DiffCollector> plugins = List.of(new TreesDiffCollector(), new SimilarImgDetector(), new DirMoveDetector());

	/**
	 * @param tree1
	 * @param tree2
	 *
	 */
	public DirTreesDiffResult findDifferences(RepoData tree1, RepoData tree2) {

		DirTreesDiffResult diffsResult = new DirTreesDiffResult();

		plugins.forEach(diffCollector -> diffCollector.apply(tree1, tree2, diffsResult));

		return diffsResult;
	}
}
