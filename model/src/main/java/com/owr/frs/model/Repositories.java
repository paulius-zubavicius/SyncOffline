package com.owr.frs.model;

import java.util.ArrayList;
import java.util.List;

public class Repositories {

	private List<RepositoryMeta> repos = new ArrayList<>();

	public List<RepositoryMeta> getRepos() {
		return repos;
	}

	public void setRepos(List<RepositoryMeta> repos) {
		this.repos = repos;
	}

	public String getPath(String repoName) {
		return getRepoMeta(repoName).getPath();
	}

	public RepositoryMeta getRepoMeta(String repoName) {

		for (RepositoryMeta repo : repos) {
			if (repoName.equalsIgnoreCase(repo.getName())) {
				return repo;
			}
		}

		throw new RuntimeException("Repository not found: " + repoName);
	}

	public boolean isItExist(String repoName) {

		for (RepositoryMeta repo : repos) {
			if (repoName.equalsIgnoreCase(repo.getName())) {
				return true;
			}
		}

		return false;
	}

}
