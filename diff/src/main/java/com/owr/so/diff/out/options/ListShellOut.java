package com.owr.so.diff.out.options;

import java.util.List;

import com.owr.so.diff.model.FileEntityWrapper;
import com.owr.so.diff.model.RepoDiff;
import com.owr.so.diff.model.ReposRootPaths;
import com.owr.so.diff.model.diffs.DirMovedDiff;
import com.owr.so.diff.model.diffs.FileDuplicatesDiff;
import com.owr.so.diff.model.diffs.FileModifiedDiff;
import com.owr.so.diff.model.diffs.FileMovedDiff;
import com.owr.so.diff.model.diffs.FileNewDiff;
import com.owr.so.diff.out.IDiffOutput;

public class ListShellOut implements IDiffOutput {

    private static String GROUP = "@ Group: ";
    private static String OPT = "$[Option]: ";

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

        int group = 0;
        for (FileModifiedDiff modFile : modifiedFiles) {

            int modifiedCase = Long.compare(modFile.getFile1().getFile().getModified(),
                    modFile.getFile2().getFile().getModified());
            group++;
            if (modifiedCase < 0) {
                modifiedFile(group, modFile.getFile2(), modFile.getFile1(), "replace old one", "rollback",
                        rootPaths);
            } else if (modifiedCase > 0) {
                modifiedFile(group, modFile.getFile1(), modFile.getFile2(), "replace old one", "rollback",
                        rootPaths);
            } else {
                modifiedFile(group, modFile.getFile1(), modFile.getFile2(), "replace", "replace", rootPaths);
            }
        }

        System.out.println();

    }

    private void modifiedFile(int group, FileEntityWrapper newerFile, FileEntityWrapper olderFile, String newerStr,
                              String olderString, ReposRootPaths rootPaths) {
        String rootNewer1 = rootPaths.getRepoDirByName(newerFile.getRepoName());
        String rootOlder2 = rootPaths.getRepoDirByName(olderFile.getRepoName());

        System.out.println(GROUP + group);
        System.out.println();
        System.out.println(OPT + newerStr);
        System.out.println("rm '" + rootOlder2 + olderFile.getPath() + "'");
        System.out.println("cp '" + rootNewer1 + newerFile.getPath() + "' '" + rootOlder2 + "'");
        System.out.println();
        System.out.println(OPT + olderString);
        System.out.println("rm '" + rootNewer1 + newerFile.getPath() + "'");
        System.out.println("cp '" + rootOlder2 + olderFile.getPath() + "' '" + rootNewer1 + "'");
        System.out.println();
    }

    public void outMoved(List<FileMovedDiff> movedFiles, ReposRootPaths rootPaths) {

        int group = 0;
        for (FileMovedDiff modFile : movedFiles) {

            int moveCase = Long.compare(modFile.getFile1().getFile().getAccessed(),
                    modFile.getFile2().getFile().getAccessed());
            group++;
            if (moveCase < 0) {
                movedFile(group, modFile.getFile2(), modFile.getFile1(), "rename to as new one", "rollback to original",
                        rootPaths);
            } else if (moveCase > 0) {
                movedFile(group, modFile.getFile1(), modFile.getFile2(), "rename to as new one", "rollback to original",
                        rootPaths);
            } else {
                movedFile(group, modFile.getFile1(), modFile.getFile2(), "rename", "rename", rootPaths);
            }
        }

        System.out.println();

    }

    private void movedFile(int group, FileEntityWrapper newerFile, FileEntityWrapper olderFile, String newerStr,
                           String olderString, ReposRootPaths rootPaths) {
        String rootNewer1 = rootPaths.getRepoDirByName(newerFile.getRepoName());
        String rootOlder2 = rootPaths.getRepoDirByName(olderFile.getRepoName());

        System.out.println(GROUP + group);
        System.out.println();
        System.out.println(OPT + newerStr);
        System.out.println("mv '" + rootOlder2 + olderFile.getPath() + "' '" + rootOlder2 + newerFile.getPath() + "'");
        System.out.println();
        System.out.println(OPT + olderString);
        System.out.println("mv '" + rootNewer1 + newerFile.getPath() + "' '" + rootNewer1 + olderFile.getPath() + "'");
        System.out.println();
    }

    private void outDirMoved(List<DirMovedDiff> movedDirs, ReposRootPaths rootPaths) {
        int group = 0;
        for (DirMovedDiff modDir : movedDirs) {
            System.out.println(GROUP + ++group);
            System.out.println();
            System.out.println(OPT + " " + modDir.getDir1().getRepoName() + " -> " + modDir.getDir2().getRepoName());
            System.out.println("mv '" + modDir.getDir1().getRelativeDirPath() + "' '"
                    + modDir.getDir2().getRelativeDirPath() + "'");
            System.out.println();
            System.out.println(OPT + " " + modDir.getDir2().getRepoName() + " -> " + modDir.getDir1().getRepoName());
            System.out.println("mv '" + modDir.getDir2().getRelativeDirPath() + "' '"
                    + modDir.getDir1().getRelativeDirPath() + "'");
            System.out.println();
        }
    }

    public void outNew(List<FileNewDiff> newFiles, ReposRootPaths rootPaths) {

        for (FileNewDiff modFile : newFiles) {
            System.out.println(GROUP + modFile.getFile1().getPath());
            System.out.println();

            System.out.println(OPT + "copy to new repo");
            String rootPath = rootPaths.getRepoDirByName(modFile.getFile1().getRepoName());
            String rootPathOther = rootPaths.getRepoDirByName(oppositeRepoName(rootPaths, modFile.getFile1().getRepoName()));
            System.out.println("mkdir -r '" + rootPathOther + "'");
            System.out.println("cp '" + rootPath + modFile.getFile1().getPath() + "' '" + rootPathOther
                    + modFile.getFile1().getPath() + "'");

            System.out.println();
            System.out.println(OPT + "rollback");
            System.out.println("rm '" + rootPath + modFile.getFile1().getPath() + "'");
            System.out.println();
        }
    }

    private String oppositeRepoName(ReposRootPaths rootPaths, String repoName) {
        return rootPaths.isItSource(repoName) ? rootPaths.getTarget() : repoName;
    }

    public void outDuplicates(List<FileDuplicatesDiff> duplicates, ReposRootPaths rootPaths) {
        for (FileDuplicatesDiff modFile : duplicates) {
            System.out.println(GROUP + modFile.getChecksum());
            System.out.println();
            System.out.println(OPT + "remove from repo: "
                    + (modFile.getFiles1() != null ? "[" + modFile.getFiles1().get(0).getRepoName() + "]" : ""));

            for (FileEntityWrapper fileEntity : modFile.getFiles1()) {
                String rootPath = rootPaths.getRepoDirByName(fileEntity.getRepoName());
                System.out.println("rm '" + rootPath + fileEntity.getPath() + "'");
            }

            System.out.println();
            System.out.println(OPT + "remove from repo: "
                    + (modFile.getFiles2() != null ? "[" + modFile.getFiles2().get(0).getRepoName() + "]" : ""));

            for (FileEntityWrapper fileEntity : modFile.getFiles2()) {
                String rootPath = rootPaths.getRepoDirByName(fileEntity.getRepoName());
                System.out.println("rm '" + rootPath + fileEntity.getPath() + "'");
            }

            System.out.println();
        }
    }

}
