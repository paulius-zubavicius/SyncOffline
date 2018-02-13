package com.owr.so.merge.diff;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RepoFile implements Serializable {

	private static final long serialVersionUID = -7192398703705372475L;

	private String repo;
	private String path;

	public RepoFile(String repo, String path) {
		this.repo = repo;
		this.path = path;
	}

	public String getRepo() {
		return repo;
	}

	public void setRepo(String repo) {
		this.repo = repo;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE).build();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(repo).append(path).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RepoFile))
			return false;
		if (obj == this)
			return true;

		RepoFile rf = (RepoFile) obj;
		return new EqualsBuilder().append(repo, rf.getRepo()).append(path, rf.getPath()).isEquals();
	}

}
