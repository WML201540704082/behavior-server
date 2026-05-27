package com.lnsoft.device.api.safeaccess.entity;
//导入 java 类


import com.lnsoft.core.mp.base.BaseEntity;
import lombok.Data;

/**
 * SysTRadiusConnection的POJO类
 *
 * @author lnsoft  [Tue Aug 02 17:37:29 CST 2016]
 */
@Data
public class SysTRadiusConnection extends BaseEntity {

	/**
	 * 属性strcId
	 */
	private String strcId;

	/**
	 * 属性orgNo
	 */
	private String orgNo;

	/**
	 * 属性radiusIp
	 */
	private String radiusIp;

	/**
	 * 属性radiusPort
	 */
	private String radiusPort;

	/**
	 * 属性radiusName
	 */
	private String radiusName;

	/**
	 * 属性radiusUser
	 */
	private String radiusUser;

	/**
	 * 属性radiusPasswd
	 */
	private String radiusPasswd;

	/**
	 * 属性bakCol1
	 */
	private String bakCol1;

	/**
	 * 属性bakCol2
	 */
	private String bakCol2;

	/**
	 * 属性bakCol3
	 */
	private String bakCol3;

	/**
	 * 属性bakCol4
	 */
	private String bakCol4;

	/**
	 * SysTRadiusConnection构造函数
	 */
	public SysTRadiusConnection() {
		super();
	}

	/**
	 * SysTRadiusConnection完整的构造函数
	 */
	public SysTRadiusConnection(String strcId, String orgNo, String radiusIp, String radiusPort, String radiusName, String radiusUser, String radiusPasswd) {
		this.strcId = strcId;
		this.orgNo = orgNo;
		this.radiusIp = radiusIp;
		this.radiusPort = radiusPort;
		this.radiusName = radiusName;
		this.radiusUser = radiusUser;
		this.radiusPasswd = radiusPasswd;
	}

	/**
	 * 属性 strcId 的get方法
	 *
	 * @return String
	 */
	public String getStrcId() {
		return strcId;
	}

	/**
	 * 属性 strcId 的set方法
	 *
	 * @return
	 */
	public void setStrcId(String strcId) {
		if (strcId != null && strcId.trim().length() == 0) {
			this.strcId = null;
		} else {
			this.strcId = strcId;
		}
	}

	/**
	 * 属性 orgNo 的get方法
	 *
	 * @return String
	 */
	public String getOrgNo() {
		return orgNo;
	}

	/**
	 * 属性 orgNo 的set方法
	 *
	 * @return
	 */
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	/**
	 * 属性 radiusIp 的get方法
	 *
	 * @return String
	 */
	public String getRadiusIp() {
		return radiusIp;
	}

	/**
	 * 属性 radiusIp 的set方法
	 *
	 * @return
	 */
	public void setRadiusIp(String radiusIp) {
		this.radiusIp = radiusIp;
	}

	/**
	 * 属性 radiusPort 的get方法
	 *
	 * @return String
	 */
	public String getRadiusPort() {
		return radiusPort;
	}

	/**
	 * 属性 radiusPort 的set方法
	 *
	 * @return
	 */
	public void setRadiusPort(String radiusPort) {
		this.radiusPort = radiusPort;
	}

	/**
	 * 属性 radiusName 的get方法
	 *
	 * @return String
	 */
	public String getRadiusName() {
		return radiusName;
	}

	/**
	 * 属性 radiusName 的set方法
	 *
	 * @return
	 */
	public void setRadiusName(String radiusName) {
		this.radiusName = radiusName;
	}

	/**
	 * 属性 radiusUser 的get方法
	 *
	 * @return String
	 */
	public String getRadiusUser() {
		return radiusUser;
	}

	/**
	 * 属性 radiusUser 的set方法
	 *
	 * @return
	 */
	public void setRadiusUser(String radiusUser) {
		this.radiusUser = radiusUser;
	}

	/**
	 * 属性 radiusPasswd 的get方法
	 *
	 * @return String
	 */
	public String getRadiusPasswd() {
		return radiusPasswd;
	}

	/**
	 * 属性 radiusPasswd 的set方法
	 *
	 * @return
	 */
	public void setRadiusPasswd(String radiusPasswd) {
		this.radiusPasswd = radiusPasswd;
	}

	/**
	 * 属性 bakCol1 的get方法
	 *
	 * @return String
	 */
	public String getBakCol1() {
		return bakCol1;
	}

	/**
	 * 属性 bakCol1 的set方法
	 *
	 * @return
	 */
	public void setBakCol1(String bakCol1) {
		this.bakCol1 = bakCol1;
	}

	/**
	 * 属性 bakCol2 的get方法
	 *
	 * @return String
	 */
	public String getBakCol2() {
		return bakCol2;
	}

	/**
	 * 属性 bakCol2 的set方法
	 *
	 * @return
	 */
	public void setBakCol2(String bakCol2) {
		this.bakCol2 = bakCol2;
	}

	/**
	 * 属性 bakCol3 的get方法
	 *
	 * @return String
	 */
	public String getBakCol3() {
		return bakCol3;
	}

	/**
	 * 属性 bakCol3 的set方法
	 *
	 * @return
	 */
	public void setBakCol3(String bakCol3) {
		this.bakCol3 = bakCol3;
	}

	/**
	 * 属性 bakCol4 的get方法
	 *
	 * @return String
	 */
	public String getBakCol4() {
		return bakCol4;
	}

	/**
	 * 属性 bakCol4 的set方法
	 *
	 * @return
	 */
	public void setBakCol4(String bakCol4) {
		this.bakCol4 = bakCol4;
	}

	/**
	 * Hibernate通过该方法判断对象是否相等
	 *
	 * @return boolean
	 */
//	@Override
//	public boolean equals(Object o) {
//		if (this == o) {
//			return true;
//		}
//
//		if (o == null || !(o instanceof SysTRadiusConnection)) {
//			return false;
//		}
//
//		if (getStrcId() == null) {
//			return false;
//		}
//
//		SysTRadiusConnection other = (SysTRadiusConnection) o;
//		return new EqualsBuilder()
//			.append(this.getStrcId(), other.getStrcId())
//			.isEquals();
//	}

	/**
	 * toString方法
	 *
	 * @return String
	 */
	@Override
	public String toString() {

		return new StringBuffer()
			.append("strcId" + ":" + getStrcId())
			.append("orgNo" + ":" + getOrgNo())
			.append("radiusIp" + ":" + getRadiusIp())
			.append("radiusPort" + ":" + getRadiusPort())
			.append("radiusName" + ":" + getRadiusName())
			.append("radiusUser" + ":" + getRadiusUser())
			.append("radiusPasswd" + ":" + getRadiusPasswd())
			.append("bakCol1" + ":" + getBakCol1())
			.append("bakCol2" + ":" + getBakCol2())
			.append("bakCol3" + ":" + getBakCol3())
			.append("bakCol4" + ":" + getBakCol4())
			.toString();

	}


	/**
	 * hashcode方法
	 *
	 * @return int
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
