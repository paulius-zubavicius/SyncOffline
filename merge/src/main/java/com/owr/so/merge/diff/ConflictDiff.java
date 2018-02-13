package com.owr.so.merge.diff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.owr.so.model.FileMetaDepricated;

public class ConflictDiff implements Diff , Serializable {

	private static final long serialVersionUID = -2707421901915614824L;

	private List<FileMetaDepricated> fml = new ArrayList<>();

	private ConflictReason reason;

	public ConflictDiff(ConflictReason reason, List<FileMetaDepricated> fml1, List<FileMetaDepricated> fml2) {

		this.reason = reason;
		fml.addAll(fml1);
		fml.addAll(fml2);

	}

	public ConflictDiff(ConflictReason reason, FileMetaDepricated fm1, FileMetaDepricated fm2) {

		this.reason = reason;
		fml.add(fm1);
		fml.add(fm2);

	}

	@Override
	public Operation getOperation() {
		return Operation.Conflict;
	}

	public List<FileMetaDepricated> getFml() {
		return fml;
	}

	public ConflictReason getReason() {
		return reason;
	}
	
	

}
