package com.owr.frs.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.owr.frs.model.Repositories;
import com.owr.frs.model.RepositoryMeta;

public class RepoStorage extends Storage<Repositories> {

	private static final String REPOSITORIES_FILE_NAME = "repositories";

	public void addRepo(Repositories repositories, String repoName, String path) throws IOException {

		if (getRepoByName(repositories.getRepos(), repoName) != null) {
			throw new RuntimeException("Already added: " + repoName);
		}

		repositories.getRepos().add(new RepositoryMeta(repoName, path));
		Path repoFile = Paths.get(REPOSITORIES_FILE_NAME);
		save(repoFile, repositories);
	}

	public void removeRepo(Repositories repositories, String repoName) throws IOException {
		List<RepositoryMeta> repos = repositories.getRepos();

		repos.remove(getRepoByName(repos, repoName));

		Path repoFile = Paths.get(REPOSITORIES_FILE_NAME);
		save(repoFile, repositories);
	}

	private RepositoryMeta getRepoByName(List<RepositoryMeta> repos, String repoName) {
		for (RepositoryMeta repo : repos) {
			if (repoName.equalsIgnoreCase(repo.getName())) {
				return repo;
			}
		}

		return null;
	}

	public Repositories load() throws IOException {
		Path repoFile = Paths.get(REPOSITORIES_FILE_NAME);

		File f = repoFile.toFile();
		if (!f.exists()) {
			f.createNewFile();
		}
		Repositories r = load(repoFile, Repositories.class);
		if (r == null) {
			r = new Repositories();
		}
		return r;
	}

}
