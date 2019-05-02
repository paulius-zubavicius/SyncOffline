package com.owr.so.diff.model;

import java.util.Map;

public abstract class Entity {

    private transient int repoId;

    private Map<ScanPlugin, Map<String, String>> pluginsData;

    public int getRepoId() {
        return repoId;
    }

    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }

    public Map<ScanPlugin, Map<String, String>> getPluginsData() {
        return pluginsData;
    }

    public void setPluginsData(Map<ScanPlugin, Map<String, String>> pluginsData) {
        this.pluginsData = pluginsData;
    }
}
