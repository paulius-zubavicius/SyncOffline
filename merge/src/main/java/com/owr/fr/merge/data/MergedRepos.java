package com.owr.fr.merge.data;

import java.util.ArrayList;
import java.util.List;

import com.owr.fr.merge.diff.ConflictDiff;
import com.owr.fr.merge.diff.ConflictReason;
import com.owr.fr.merge.diff.Operation;
import com.owr.fr.merge.diff.RepoFile;
import com.owr.fr.merge.diff.SimpleDiff;
import com.owr.frs.model.FileMeta;

public class MergedRepos {

	private List<SimpleDiff> diffs = new ArrayList<>();
	private List<ConflictDiff> conflicts = new ArrayList<>();

	public MergedRepos() {

	}

	public void addConflict(ConflictReason reason, List<FileMeta> fml1, List<FileMeta> fml2) {
		conflicts.add(new ConflictDiff(reason, fml1, fml2));
	}

	public void addConflict(ConflictReason reason, FileMeta fm1, FileMeta fm2) {
		conflicts.add(new ConflictDiff(reason, fm1, fm2));
	}

	public void addDiff(Operation op, FileMeta src, FileMeta trg) {
		diffs.add(new SimpleDiff(op, new RepoFile(src.getRepo(), src.getPath()),
				new RepoFile(trg.getRepo(), trg.getPath())));
	}

	public List<SimpleDiff> getDiffList() {
		return diffs;
	}

	public List<ConflictDiff> getConflicts() {
		return conflicts;
	}

}
