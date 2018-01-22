package org.simple.entity;

/**
 * 按日访问统计
 */
public class StatDayAccessDO extends BaseStatAccessDO {
	private static final long serialVersionUID = 8061204849924462866L;
	private String idStatDayAccess;
	private Integer lastIncrease;

	public String getIdStatDayAccess() {
		return idStatDayAccess;
	}

	public void setIdStatDayAccess(String idStatDayAccess) {
		this.idStatDayAccess = idStatDayAccess;
	}

	public Integer getLastIncrease() {
		return lastIncrease;
	}

	public void setLastIncrease(Integer lastIncrease) {
		this.lastIncrease = lastIncrease;
	}
}
