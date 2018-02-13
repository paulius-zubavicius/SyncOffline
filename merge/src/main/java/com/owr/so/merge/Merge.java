package com.owr.so.merge;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.owr.so.merge.data.MergedRepos;
import com.owr.so.merge.diff.ConflictReason;
import com.owr.so.merge.diff.Operation;
import com.owr.so.model.FileMetaDepricated;
import com.owr.so.model.FilesRepoDepricated;

public class Merge {

	private static final int SINGLE = 1;

	public void merge(FilesRepoDepricated filesRepo1, FilesRepoDepricated filesRepo2, MergedRepos result) {

		mergeRepo(result, filesRepo1, filesRepo2, false);
		mergeRepo(result, filesRepo2, filesRepo1, true);

	}

	private void mergeRepo(MergedRepos data, FilesRepoDepricated filesRepo1, FilesRepoDepricated filesRepo2, boolean oppositeMerge) {

		Set<String> paths1 = filesRepo1.getBranchData().keySet();

		FileMetaDepricated fm2, fm1 = null;
		List<FileMetaDepricated> fml1, fml2 = null;

		for (String path1 : paths1) {

			fm1 = filesRepo1.getBranchData().get(path1);
			fm2 = findByPath(path1, filesRepo2);

			if (fm2 != null) {
				// Place the same

				// is it different content?
				if (!fm1.getMd5().equalsIgnoreCase(fm2.getMd5())) {

					// 
					if (fm1.getModified() > fm2.getModified()) {
						data.addDiff(Operation.Modified, fm1, fm2);
					} else if (fm1.getModified() == fm2.getModified()) {
						if (!oppositeMerge) {
							data.addConflict(ConflictReason.SameModified, fm1, fm2);
						}
					}
				}
			} else {
				// New or moved?

				fml1 = findByMd5(fm1, filesRepo1);
				fml2 = findByMd5(fm1, filesRepo2);

				if (fml1.size() == SINGLE && fml2.size() == SINGLE) {
					fm2 = fml2.get(0);
					// Moved (renamed)

					// 
					if (fm1.getAccessed() > fm2.getAccessed()) {
						data.addDiff(Operation.Move, fm1, fm2);
					} else if (fm1.getAccessed() == fm2.getAccessed()) {
						if (!oppositeMerge) {
							data.addConflict(ConflictReason.SameAccessed, fm1, fm2);
						}
					}

				} else if (fml2.isEmpty()) {
					// New / Deleted
					fm2 = new FileMetaDepricated();
					fm2.setRepo(filesRepo2.getName());
					fm2.setPath(fm1.getPath());
					data.addDiff(Operation.Add, fm1, fm2);
				} else {

					/**
					 * <pre>
					 *         / ? [abc]
					 *  [abc] - 
					 *         \ ? [abc]
					 *         
					 *         
					 *  [abc]  \ / [abc]
					 *          ?
					 *  [abc]  / \ [abc]
					 *  
					 *  
					 *  [abc] ? \  
					 *           - [abc]
					 *  [abc] ? /
					 * 
					 * </pre>
					 */

					if (!oppositeMerge) {
						data.addConflict(ConflictReason.SameHashes, fml1, fml2);
					}
				}
			}
		}
	}

	private List<FileMetaDepricated> findByMd5(FileMetaDepricated fm, FilesRepoDepricated filesRepo) {
		List<FileMetaDepricated> result =filesRepo.getBranchDataByMd5().get(fm.getMd5());
		if (result == null) {
			result = new ArrayList<>();
		}
		return result;
	}

	private FileMetaDepricated findByPath(String path, FilesRepoDepricated filesRepo) {
		return filesRepo.getBranchData().get(path);
	}

}
