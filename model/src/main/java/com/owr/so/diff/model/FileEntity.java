package com.owr.so.diff.model;

import java.io.Serializable;
import java.util.Objects;

public class FileEntity extends Entity implements Serializable {

	private static final long serialVersionUID = -3211564506768018649L;

	private String name;
	private String checksum;
	private long size;
	private long modified;
	private long accessed;

	private transient DirEntity dir;

	/**
	 * @return path of the file without root path prefix
	 */
	public String getPath() {
		return dir.getPath() + "/" + name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getModified() {
		return modified;
	}

	public void setModified(long modified) {
		this.modified = modified;
	}

	public long getAccessed() {
		return accessed;
	}

	public void setAccessed(long accessed) {
		this.accessed = accessed;
	}

	public DirEntity getDir() {
		return dir;
	}

	public void setDir(DirEntity dir) {
		this.dir = dir;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FileEntity that = (FileEntity) o;
		return size == that.size &&
				modified == that.modified &&
				accessed == that.accessed &&
				name.equals(that.name) &&
				checksum.equals(that.checksum);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, checksum, size, modified, accessed);
	}
}
