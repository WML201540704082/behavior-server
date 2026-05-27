package com.lnsoft.device.api.erp.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/30 14:47
 * @Description: ErpTransEqunrItemDTO
 */
@XmlRootElement(name = "ITEM")
@XmlType(propOrder = {"sqsj", "xh", "xtbm", "sqr", "equnr", "bfbl", "sqdms", "bfyy", "xmbm", "xmmc", "sqbm", "clgx", "zsbzt"})
public class ErpTransZcbfItemDTO {

	/**
	 * 信通设备ID == UUID 必填
	 */
	private String xtbm;

	/**
	 * 序号
	 */
	private String xh;

	/**
	 * 申请日期
	 */
	private String sqsj;

	/**
	 * 报废单据申请人
	 */
	private String sqr;

	/**
	 * ERP设备编码
	 */
	private String equnr;

	/**
	 * 报废比例
	 */
	private Double bfbl;

	/**
	 * 报废申请单描述信息
	 */
	private String sqdms;

	/**
	 * 报废原因(数据字典)
	 */
	private String bfyy;

	/**
	 * 项目编号
	 */
	private String xmbm;

	/**
	 * 项目名称
	 */
	private String xmmc;

	/**
	 * 申请部门 == 保管部门
	 */
	private String sqbm;

	/**
	 * 残值处理及资产更新方案
	 */
	private String clgx;

	/**
	 * 设备状态
	 */
	private String zsbzt;


	@XmlElement(name = "XTBM")
	public void setXtbm(String xtbm) {
		this.xtbm = xtbm;
	}

	@XmlElement(name = "XH")
	public void setXh(String xh) {
		this.xh = xh;
	}

	@XmlElement(name = "SQSJ")
	public void setSqsj(String sqsj) {
		this.sqsj = sqsj;
	}

	@XmlElement(name = "SQR")
	public void setSqr(String sqr) {
		this.sqr = sqr;
	}

	@XmlElement(name = "EQUNR")
	public void setEqunr(String equnr) {
		this.equnr = equnr;
	}

	@XmlElement(name = "BFBL")
	public void setBfbl(Double bfbl) {
		this.bfbl = bfbl;
	}

	@XmlElement(name = "SQDMS")
	public void setSqdms(String sqdms) {
		this.sqdms = sqdms;
	}

	@XmlElement(name = "BFYY")
	public void setBfyy(String bfyy) {
		this.bfyy = bfyy;
	}

	@XmlElement(name = "XMBM")
	public void setXmbm(String xmbm) {
		this.xmbm = xmbm;
	}

	@XmlElement(name = "XMMC")
	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	@XmlElement(name = "SQBM")
	public void setSqbm(String sqbm) {
		this.sqbm = sqbm;
	}

	@XmlElement(name = "CLGX")
	public void setClgx(String clgx) {
		this.clgx = clgx;
	}

	@XmlElement(name = "ZSBZT")
	public void setZsbzt(String zsbzt) {
		this.zsbzt = zsbzt;
	}

	public String getXtbm() {
		return xtbm;
	}

	public String getXh() {
		return xh;
	}

	public String getSqsj() {
		return sqsj;
	}

	public String getSqr() {
		return sqr;
	}

	public String getEqunr() {
		return equnr;
	}

	public Double getBfbl() {
		return bfbl;
	}

	public String getSqdms() {
		return sqdms;
	}

	public String getBfyy() {
		return bfyy;
	}

	public String getXmbm() {
		return xmbm;
	}

	public String getXmmc() {
		return xmmc;
	}

	public String getSqbm() {
		return sqbm;
	}

	public String getClgx() {
		return clgx;
	}

	public String getZsbzt() {
		return zsbzt;
	}

	@Override
	public String toString() {
		return "ErpTransZcbfItemDTO{" +
			"xtbm='" + xtbm + '\'' +
			", xh='" + xh + '\'' +
			", sqsj='" + sqsj + '\'' +
			", sqr='" + sqr + '\'' +
			", equnr='" + equnr + '\'' +
			", bfbl='" + bfbl + '\'' +
			", sqdms='" + sqdms + '\'' +
			", bfyy='" + bfyy + '\'' +
			", xmbm='" + xmbm + '\'' +
			", xmmc='" + xmmc + '\'' +
			", sqbm='" + sqbm + '\'' +
			", clgx='" + clgx + '\'' +
			", zsbzt='" + zsbzt + '\'' +
			'}';
	}
}
