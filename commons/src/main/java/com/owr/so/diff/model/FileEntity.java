package com.owr.so.diff.model;

import java.util.Objects;

public class FileEntity {

	private String name;
	private String checksum;
	private long size;
	private long modified;
	private long accessed;

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		FileEntity that = (FileEntity) o;
		return size == that.size && modified == that.modified && accessed == that.accessed && name.equals(that.name)
				&& checksum.equals(that.checksum);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, checksum, size, modified, accessed);
	}

	@Override
	public String toString() {
		return "FileEntity [name=" + name + "]";
	}

}
