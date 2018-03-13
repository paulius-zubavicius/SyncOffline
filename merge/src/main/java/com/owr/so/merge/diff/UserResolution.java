package com.owr.so.merge.diff;

public abstract class UserResolution {

	private DiffAction action = DiffAction.UPDATE;

	public DiffAction getAction() {
		return action;
	}

	public void setAction(DiffAction action) {
		this.action = action;
	}

}
