package com.owr.so.merge.diff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.owr.so.model.FileEntity;

public class ConflictDiff implements Diff , Serializable {

	private static final long serialVersionUID = -2707421901915614824L;

	private List<FileEntity> fml = new ArrayList<>();

	private ConflictReason reason;

	public ConflictDiff(ConflictReason reason, List<FileEntity> fml1, List<FileEntity> fml2) {

		this.reason = reason;
		fml.addAll(fml1);
		fml.addAll(fml2);

	}

	public ConflictDiff(ConflictReason reason, FileEntity fm1, FileEntity fm2) {

		this.reason = reason;
		fml.add(fm1);
		fml.add(fm2);

	}

	@Override
	public Operation getOperation() {
		return Operation.Conflict;
	}

	public List<FileEntity> getFml() {
		return fml;
	}

	public ConflictReason getReason() {
		return reason;
	}
	
	

}
