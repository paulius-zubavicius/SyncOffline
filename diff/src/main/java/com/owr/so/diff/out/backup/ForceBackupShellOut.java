package com.owr.so.diff.out.backup;

import java.util.*;

import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.RepoDiff;
import com.owr.so.diff.model.ReposRootPaths;
import com.owr.so.diff.model.diffs.DirMovedDiff;
import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;
import com.owr.so.diff.out.IDiffOutput;

public class ForceBackupShellOut implements IDiffOutput {

    @SuppressWarnings("unchecked")
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

    public void outDuplicates(List<FileDuplicatesDiff> duplicates, ReposRootPaths rootPaths) {
        for (FileDuplicatesDiff modFile : duplicates) {
            if (rootPaths.isItSource(modFile.getFiles1().get(0).getRepoName())) {
                outDuplicates(modFile.getFiles1(), modFile.getFiles2(), rootPaths);
            } else {
                outDuplicates(modFile.getFiles2(), modFile.getFiles1(), rootPaths);
            }
        }
    }

    private void outDuplicates(List<FileEntityWrapper> source, List<FileEntityWrapper> target, ReposRootPaths rootPaths) {

        if (source.size() > target.size()) {
            // Copy files in target repo
            copyInTarget(source.subList(target.size() - 1, source.size() - 1), target.get(0), rootPaths);
        } else if (source.size() < target.size()) {
            // Remove files in target repo
            removeInTarget(target.subList(source.size() - 1, target.size() - 1), rootPaths);
        }
        renameEqualSizeOfDiff(source, target, rootPaths);
    }

    private void removeInTarget(List<FileEntityWrapper> filesInTarget, ReposRootPaths rootPaths) {
        for (FileEntityWrapper trgFile : filesInTarget) {
            System.out.println("rm '" + rootPaths.getTargetRootDir() + trgFile.getPath() + "'");
        }
    }

    private void copyInTarget(List<FileEntityWrapper> filesInSource, FileEntityWrapper fileInTarget, ReposRootPaths rootPaths) {
        for (FileEntityWrapper srcFile : filesInSource){
            System.out.println("mkdir -p '" + rootPaths.getTargetRootDir() + srcFile.getRelativeDirPath() + "'");
            System.out.println("cp '" + rootPaths.getTargetRootDir() + fileInTarget.getPath() + "' '" + rootPaths.getTargetRootDir() + srcFile.getPath() + "'");
        }
    }

    private void renameEqualSizeOfDiff(List<FileEntityWrapper> source, List<FileEntityWrapper> target, ReposRootPaths rootPaths) {
        int min = Math.min(source.size(), target.size());
        for (int i = 0; i < min; i++) {
            System.out.println("mv '" + rootPaths.getTargetRootDir() + target.get(i).getPath() + "' '" + rootPaths.getTargetRootDir() + source.get(i).getPath() + "'");
        }
    }
}
