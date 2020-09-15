package com.owr.so.diff.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Deprecated
public class DirEntity implements Serializable {

	private static final long serialVersionUID = 118318597202971234L;

//	private String path;

	private List<FileEntity> files = new ArrayList<>();

	public DirEntity() {

	}

//	public DirEntity(String path) {
//		this.path = path;
//	}
//
//	public String getPath() {
//		return path;
//	}
//
//	public void setPath(String path) {
//		this.path = path;
//	}

	public List<FileEntity> getFiles() {
		return files;
	}

	public void setFiles(List<FileEntity> files) {
		this.files = files;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof DirEntity))
			return false;
		DirEntity dirEntity = (DirEntity) o;
		return /* path.equals(dirEntity.path) && */ files.equals(dirEntity.files);
	}

	@Override
	public int hashCode() {
		return Objects.hash(/* path, */ files);
	}

	@Override
	public String toString() {
		return "DirEntity [files=" + (files != null ? files.size() : 0) + "]";
	}

}
