package com.owr.so.diff.collector;

import com.owr.so.diff.model.RepoData;
import com.owr.so.diff.model.DirTreesDiffResult;
import com.owr.so.diff.model.FileEntity;
import com.owr.so.diff.model.diffs.*;

import java.util.ArrayList;
import java.util.List;

public class TreesDiffCollector implements DiffCollector {


    @Override
    public void apply(RepoData tree1, RepoData tree2, DirTreesDiffResult diffResult) {

        // FIXME refactor with sets, eq/hc of XxxDiff
        // TODO tests missing
        treeOneSideDiff(tree1, tree2, diffResult, false);
        treeOneSideDiff(tree2, tree1, diffResult, true);
    }


    private void treeOneSideDiff(RepoData tree1, RepoData tree2, DirTreesDiffResult diffs, boolean mirrorDiffScan) {

        // Files
        tree1.getFiles().stream()
                .filter(tree1File -> tree1File.getPath().startsWith(tree1.getSubDir()))
                .forEach(tree1File -> findDiffSubdir(tree1File, tree1, tree2, diffs, mirrorDiffScan));

        // Empty DIRs
        tree1.getTrees().keySet().stream()
                .filter(tree1Dir -> tree1Dir.startsWith(tree1.getSubDir()))
                .forEach(tree1Dir -> findNewEmptyDir(tree1Dir, tree1, tree2, diffs));

    }

    private void findNewEmptyDir(String tree1Dir, RepoData tree1, RepoData tree2, DirTreesDiffResult diffs) {
        boolean notExistsInTree2 = tree2.getTrees().get(tree1Dir) == null;
        boolean emptyInTree1 = tree1.getTrees().get(tree1Dir).getFiles().isEmpty();

        if (notExistsInTree2 && emptyInTree1) {
            diffs.getNewDirs().add(new DirNewDiff(tree1.getTrees().get(tree1Dir)));
        }

    }

    private void findDiffSubdir(FileEntity tree1File, RepoData tree1, RepoData tree2, DirTreesDiffResult diffs,
                                boolean mirrorDiffScan) {
        // Find by path
        FileEntity tree2File = tree2.getFilesByPath().get(tree1File.getPath());

        if (tree2File != null) {

            // Place the same
            filePathTheSame(tree1File, tree2File, diffs, mirrorDiffScan);
        } else {

            // New or moved
            filePathNotFound(tree1File, tree1, tree2, diffs, mirrorDiffScan);
        }
    }

    private void filePathTheSame(FileEntity tree1File, FileEntity tree2File, DirTreesDiffResult diffs, boolean mirrorDiffScan) {
        if (!mirrorDiffScan) {
            // is modified?
            if (!tree1File.getChecksum().equals(tree2File.getChecksum())) {

                // If modified time the same - it's conflict
                diffs.getModifiedFiles().add(new FileModifiedDiff(tree1File, tree2File));
            }
        }
    }

    private void filePathNotFound(FileEntity tree1File, RepoData tree1, RepoData tree2, DirTreesDiffResult diffs, boolean mirrorScan) {

        // "Not found" could mean 2 things: It's new or it's moved

        List<FileEntity> files1 = findByChecksum(tree1File, tree1);
        List<FileEntity> files2 = findByChecksum(tree1File, tree2);

        /*
         * Removing with identical paths on both sides, cause it could be files copies
         * but it doesn't mean a conflict
         */
        removingNotConflictingFiles(files1, files2);

        // [1:1] Moved (renamed)
        if (files1.size() == 1 && files2.size() == 1) {

            // Two side effect
            if (!mirrorScan) {
                FileEntity tree2File = files2.get(0);

                diffs.getMovedFiles().add(new FileMovedDiff(tree1File, tree2File));
            }
        } else
            // [N:0] New (or deleted in "tree2")
            if (files2.isEmpty()) {

                diffs.getNewFiles().add(new FileNewDiff(tree1File));
            } else
                // [N:N] Duplicates conflict
                if (files1.size() > 1 && files2.size() > 1) {

                    /**
                     * <pre>
                     *  [abc]  \ / [abc]
                     *          ?
                     *  [abc]  / \ [abc]
                     * </pre>
                     */

                    // Two side effects
                    if (!mirrorScan) {
                        diffs.getDuplicates().add(new FileDuplicatesDiff(files1, files2));
                    }
                } else
                    // [1:N] [N:1]
                    if ((files1.size() == 1 && files2.size() > 1) || (files1.size() > 1 && files2.size() == 1)) {
                        /**
                         * <pre>
                         *         / ? [abc]
                         *  [abc] -
                         *         \ ? [abc]
                         *
                         *
                         *  [abc] ? \
                         *           - [abc]
                         *  [abc] ? /
                         *
                         * </pre>
                         */

                        // One side effect
                        diffs.getDuplicates().add(new FileDuplicatesDiff(files1, files2));
                    } else {
                        throw new RuntimeException("Unexpected case");
                    }
    }

    private void removingNotConflictingFiles(List<FileEntity> files1, List<FileEntity> files2) {

        //TODO refactor
        if (files1.size() > 1 && files2.size() > 1) {

            for (int i1 = 0; i1 < files1.size(); i1++) {
                for (int i2 = 0; i2 < files2.size(); i2++) {

                    if (files1.get(i1).getPath().equals(files2.get(i2).getPath())) {
                        files1.remove(i1);
                        files2.remove(i2);

                        i1--;
                        i2--;

                        if (i1 < 0) {
                            i1 = 0;
                        }

                        if (i2 < 0) {
                            i2 = 0;
                        }
                    }
                }
            }
        }
    }

    private List<FileEntity> findByChecksum(FileEntity tree1File, RepoData tree1) {
        return tree1.getFilesByChecksum().getOrDefault(tree1File.getChecksum(), new ArrayList<>());
    }
}
