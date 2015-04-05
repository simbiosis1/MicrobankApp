package org.simbiosis.microbank.model;

import java.io.Serializable;
import java.util.Date;

public class DepositRptPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -737227142545601763L;
	Date pos;
	long refId;

	public Date getPos() {
		return pos;
	}

	public void setPos(Date pos) {
		this.pos = pos;
	}

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		result = prime * result + (int) (refId ^ (refId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepositRptPk other = (DepositRptPk) obj;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		if (refId != other.refId)
			return false;
		return true;
	}

}
