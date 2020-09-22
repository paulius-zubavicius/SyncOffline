package com.owr.so.diff.out.backup;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.RepoDiff;
import com.owr.so.diff.model.ReposRootPaths;
import com.owr.so.diff.model.diffs.DirMovedDiff;
import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;
import com.owr.so.diff.out.IDiffOutput;

public class ForceUpdateShellOut implements IDiffOutput {

    @Override
    public <T extends RepoDiff> void out(Class<T> type, List<T> data, ReposRootPaths rootPaths) {
        if (type == FileModifiedDiff.class) {
            outModified((List<FileModifiedDiff>) data, rootPaths);
        } else if (type == FileMovedDiff.class) {
            outMoved((List<FileMovedDiff>) data, rootPaths);
        } else if (type == DirMovedDiff.class) {
            outDirMoved((List<DirMovedDiff>) data, rootPaths);
        } else if (type == FileNewDiff.class) {
            outNew((List<FileNewDiff>) data, rootPaths);
        } else if (type == FileDuplicatesDiff.class) {
            outDuplicates((List<FileDuplicatesDiff>) data, rootPaths);
        }
    }

    public void outModified(List<FileModifiedDiff> modifiedFiles, ReposRootPaths rootPaths) {
        for (FileModifiedDiff modFile : modifiedFiles) {
            System.out.println("rm '" + rootPaths.getTargetRootDir() + modFile.getFile2().getPath() + "'");
            System.out.println("cp '" + rootPaths.getSourceRootDir() + modFile.getFile1().getPath() + "' '" + rootPaths.getTargetRootDir() + "'");
        }
    }

    public void outMoved(List<FileMovedDiff> movedFiles, ReposRootPaths rootPaths) {
        movedFiles.forEach(modFile -> System.out.println("mv '" + rootPaths.getTargetRootDir() + modFile.getFile2().getPath() + "' '" + rootPaths.getSourceRootDir() + modFile.getFile1().getPath() + "'"));
    }

    private void outDirMoved(List<DirMovedDiff> movedDirs, ReposRootPaths rootPath) {
        movedDirs.forEach(modDir -> System.out.println("mv '" + rootPath.getTargetRootDir() + modDir.getDir2().getRelativeDirPath() + "' '" + rootPath.getTargetRootDir() + modDir.getDir1().getRelativeDirPath() + "'"));
    }

    public void outNew(List<FileNewDiff> newFiles, ReposRootPaths rootPaths) {
        for (FileNewDiff modFile : newFiles) {
            if (rootPaths.isItSource(modFile.getFile1().getRepoName())) {
                System.out.println("mkdir -p '" + rootPaths.getTargetRootDir() + modFile.getFile1().getRelativeDirPath() + "'");
                System.out.println("cp '" + rootPaths.getSourceRootDir() + modFile.getFile1().getPath() + "' '" + rootPaths.getTargetRootDir() + modFile.getFile1().getPath() + "'");
            } else {
                System.out.println("rm '" + rootPaths.getTargetRootDir() + modFile.getFile1().getPath() + "'");
            }
        }
    }


    public void outDuplicates(List<FileDuplicatesDiff> duplicates, ReposRootPaths rootPath) {
        for (FileDuplicatesDiff modFile : duplicates) {

            System.out.println(GROUP + modFile.getChecksum());
            System.out.println();
            System.out.println(OPT + "remove from repo: "
                    + (modFile.getFiles1() != null ? "[" + modFile.getFiles1().get(0).getRepoName() + "]" : ""));

            for (FileEntityWrapper fileEntity : modFile.getFiles1()) {
                String rootPath = rootPathByRepoName.get(fileEntity.getRepoName());
                System.out.println("rm '" + rootPath + fileEntity.getPath() + "'");
            }

            System.out.println();
            System.out.println(OPT + "remove from repo: "
                    + (modFile.getFiles2() != null ? "[" + modFile.getFiles2().get(0).getRepoName() + "]" : ""));

            for (FileEntityWrapper fileEntity : modFile.getFiles2()) {
                String rootPath = rootPathByRepoName.get(fileEntity.getRepoName());
                System.out.println("rm '" + rootPath + fileEntity.getPath() + "'");
            }

            System.out.println();
        }
    }
}
