package com.owr.so.merge.diff;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Deprecated
public class SimpleDiff /*implements Diff, Serializable*/ {

 	private static final long serialVersionUID = 659631169942015805L;
 	
	private Operation operation;
	protected RepoFile target;
	protected RepoFile source;

	public SimpleDiff(Operation op, RepoFile source, RepoFile target) {
		this.source = source;
		this.target = target;
		this.operation = op;
	}

	public RepoFile getSource() {
		return source;
	}

	public RepoFile getTarget() {
		return target;
	}

	/**
	 * Will swap target and source
	 */
	public void swapTargetSource() {
		RepoFile tmp = target;
		target = source;
		source = tmp;

	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE).build();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(source).append(target).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SimpleDiff))
			return false;
		if (obj == this)
			return true;

		SimpleDiff absDiff = (SimpleDiff) obj;
		return new EqualsBuilder().append(source, absDiff.getSource()).append(target, absDiff.getSource()).isEquals();
	}

//	@Override
//	public Operation getOperation() {
//		return operation;
//	}

}
