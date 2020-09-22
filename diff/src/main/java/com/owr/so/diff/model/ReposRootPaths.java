package com.owr.so.diff.model;

public class ReposRootPaths {

    private String source;
    private String target;

    private String sourceRootDir;
    private String targetRootDir;

    public ReposRootPaths(RepoMetaData meta1, RepoMetaData meta2) {
        source = meta1.getRepoName();
        target = meta2.getRepoName();
        sourceRootDir = meta1.getRepoRootDir();
        targetRootDir = meta2.getRepoRootDir();
    }

    public boolean isItSource(String repoName) {
        return source.equals(repoName);
    }

    public boolean isItTarget(String repoName) {
        return target.equals(repoName);
    }

    public String getRepoDirByName(String repoName) {
        if (source.equals(repoName)) {
            return sourceRootDir;
        }

        if (target.equals(repoName)) {
            return targetRootDir;
        }

        throw new RuntimeException("No case for: " + repoName);
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public String getSourceRootDir() {
        return sourceRootDir;
    }

    public String getTargetRootDir() {
        return targetRootDir;
    }
}
