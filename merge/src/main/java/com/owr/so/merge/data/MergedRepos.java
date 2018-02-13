package com.owr.so.merge.data;

import java.util.ArrayList;
import java.util.List;

import com.owr.so.merge.diff.ConflictDiff;
import com.owr.so.merge.diff.ConflictReason;
import com.owr.so.merge.diff.Operation;
import com.owr.so.merge.diff.RepoFile;
import com.owr.so.merge.diff.SimpleDiff;
import com.owr.so.model.FileMetaDepricated;

public class MergedRepos {

	private List<SimpleDiff> diffs = new ArrayList<>();
	private List<ConflictDiff> conflicts = new ArrayList<>();

	public MergedRepos() {

	}

	public void addConflict(ConflictReason reason, List<FileMetaDepricated> fml1, List<FileMetaDepricated> fml2) {
		conflicts.add(new ConflictDiff(reason, fml1, fml2));
	}

	public void addConflict(ConflictReason reason, FileMetaDepricated fm1, FileMetaDepricated fm2) {
		conflicts.add(new ConflictDiff(reason, fm1, fm2));
	}

	public void addDiff(Operation op, FileMetaDepricated src, FileMetaDepricated trg) {
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
