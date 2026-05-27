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
@XmlType(propOrder = {"xtbm", "xtbmNo", "swid", "eqktx","zsb001","zsb002","zsb010","zsb004","stat","zsb005","stort","eqart","sbfl",
	"herst","herld","posid","zsb006","tplnr","zcabn_ztpm1005","zcabn_ztpm1006","beber","inbdt","typbz","serge","baujj","baumm", "swerk","anlnr","equnr",
	"tbbs","zsyn_time","operation","zsb011"})
public class ErpTransEqunrItemDTO {

	/**
	 * 信通设备ID == UUID 必填
	 */
	private String xtbm;

	/**
	 * 信通设备编码
	 */
	private String xtbmNo;


	/**
	 * 实物ID
	 */
	private String swid;

	/**
	 * 设备名称
	 */
	private String eqktx;

	/**
	 * 使用保管部门 == 编码
	 */
	private String zsb001;

	/**
	 * 实物管理部门 == 编码
	 */
	private String zsb002;

	/**
	 * 使用保管人
	 */
	private String zsb010;

	/**
	 * 电压等级
	 */
	private String zsb004;

	/**
	 * 设备状态
	 */
	private String stat;

	/**
	 * 设备增加方式
	 */
	private String zsb005;

	/**
	 * 设备变动方式
	 */
	private String stort;

	/**
	 * 技术对象类型
	 */
	private String eqart;

	/**
	 * 设备分类
	 */
	private String sbfl;

	/**
	 * 制造商
	 */
	private String herst;

	/**
	 * 制造国家
	 */
	private String herld;

	/**
	 * WBS元素
	 */
	private String posid;

	/**
	 * 设备存放地点 = 功能位置编码
	 */
	private String zsb006;

	/**
	 * 功能位置 = 功能位置编码
	 */
	private String tplnr;

	/**
	 * 数量
	 */
	private Integer zcabn_ztpm1005;

	/**
	 * 单位 = 名称
	 */
	private String zcabn_ztpm1006;

	/**
	 * 工厂区域 = 编码 市公司传003，县公司传004
	 */
	private String beber;

	/**
	 * 投运日期 格式 = YYYYMMDD
	 */
	private String inbdt;

	/**
	 * 制造商设备型号
	 */
	private String typbz;

	/**
	 * 制造商设备铭牌号
	 */
	private String serge;

	/**
	 * 制造（出厂、竣工）年份
	 */
	private String baujj;

	/**
	 * 制造（出厂、竣工）月份
	 */
	private String baumm;

	/**
	 * 维护工厂 = 维护工厂编码
	 */
	private String swerk;


	/**
	 * ERP资产编码
	 */
	private String anlnr;


	/**
	 * ERP设备台账编码
	 */
	private String equnr;


	/**
	 * 同步标志位
	 */
	private String tbbs;

	/**
	 * 同步时间
	 */
	private String zsyn_time;

	/**
	 * 操作标示符
	 */
	private String operation;

	/**
	 * 线站标识
	 */
	private String zsb011;


	@XmlElement(name = "XTBM")
	public void setXtbm(String xtbm) {
		this.xtbm = xtbm;
	}

	@XmlElement(name = "XTBM_NO")
	public void setXtbmNo(String xtbmNo) {
		this.xtbmNo = xtbmNo;
	}

	@XmlElement(name = "SWID")
	public void setSwid(String swid) {
		this.swid = swid;
	}

	@XmlElement(name = "EQKTX")
	public void setEqktx(String eqktx) {
		this.eqktx = eqktx;
	}

	@XmlElement(name = "ZSB001")
	public void setZsb001(String zsb001) {
		this.zsb001 = zsb001;
	}

	@XmlElement(name = "ZSB002")
	public void setZsb002(String zsb002) {
		this.zsb002 = zsb002;
	}

	@XmlElement(name = "ZSB010")
	public void setZsb010(String zsb010) {
		this.zsb010 = zsb010;
	}

	@XmlElement(name = "ZSB004")
	public void setZsb004(String zsb004) {
		this.zsb004 = zsb004;
	}

	@XmlElement(name = "STAT")
	public void setStat(String stat) {
		this.stat = stat;
	}

	@XmlElement(name = "ZSB005")
	public void setZsb005(String zsb005) {
		this.zsb005 = zsb005;
	}
	@XmlElement(name = "STORT")
	public void setStort(String stort) {
		this.stort = stort;
	}

	@XmlElement(name = "EQART")
	public void setEqart(String eqart) {
		this.eqart = eqart;
	}

	@XmlElement(name = "SBFL")
	public void setSbfl(String sbfl) {
		this.sbfl = sbfl;
	}

	@XmlElement(name = "HERST")
	public void setHerst(String herst) {
		this.herst = herst;
	}

	@XmlElement(name = "HERLD")
	public void setHerld(String herld) {
		this.herld = herld;
	}

	@XmlElement(name = "POSID")
	public void setPosid(String posid) {
		this.posid = posid;
	}

	@XmlElement(name = "ZSB006")
	public void setZsb006(String zsb006) {
		this.zsb006 = zsb006;
	}

	@XmlElement(name = "TPLNR")
	public void setTplnr(String tplnr) {
		this.tplnr = tplnr;
	}

	@XmlElement(name = "ZCABN_ZTPM1005")
	public void setZcabn_ztpm1005(Integer zcabn_ztpm1005) {
		this.zcabn_ztpm1005 = zcabn_ztpm1005;
	}

	@XmlElement(name = "ZCABN_ZTPM1006")
	public void setZcabn_ztpm1006(String zcabn_ztpm1006) {
		this.zcabn_ztpm1006 = zcabn_ztpm1006;
	}

	@XmlElement(name = "BEBER")
	public void setBeber(String beber) {
		this.beber = beber;
	}

	@XmlElement(name = "INBDT")
	public void setInbdt(String inbdt) {
		this.inbdt = inbdt;
	}

	@XmlElement(name = "TYPBZ")
	public void setTypbz(String typbz) {
		this.typbz = typbz;
	}

	@XmlElement(name = "SERGE")
	public void setSerge(String serge) {
		this.serge = serge;
	}

	@XmlElement(name = "BAUJJ")
	public void setBaujj(String baujj) {
		this.baujj = baujj;
	}

	@XmlElement(name = "BAUMM")
	public void setBaumm(String baumm) {
		this.baumm = baumm;
	}

	@XmlElement(name = "SWERK")
	public void setSwerk(String swerk) {
		this.swerk = swerk;
	}

	@XmlElement(name = "ANLNR")
	public void setAnlnr(String anlnr) {
		this.anlnr = anlnr;
	}

	@XmlElement(name = "EQUNR")
	public void setEqunr(String equnr) {
		this.equnr = equnr;
	}

	@XmlElement(name = "TBBS")
	public void setTbbs(String tbbs) {
		this.tbbs = tbbs;
	}

	@XmlElement(name = "ZSYN_TIME")
	public void setZsyn_time(String zsyn_time) {
		this.zsyn_time = zsyn_time;
	}

	@XmlElement(name = "OPERATION")
	public void setOperation(String operation) {
		this.operation = operation;
	}

	@XmlElement(name = "ZSB011")
	public void setZsb011(String zsb011) {
		this.zsb011 = zsb011;
	}

	public String getXtbm() {
		return xtbm;
	}

	public String getXtbmNo() {
		return xtbmNo;
	}

	public String getSwid() {
		return swid;
	}

	public String getEqktx() {
		return eqktx;
	}

	public String getZsb001() {
		return zsb001;
	}

	public String getZsb002() {
		return zsb002;
	}

	public String getZsb010() {
		return zsb010;
	}

	public String getZsb004() {
		return zsb004;
	}

	public String getStat() {
		return stat;
	}

	public String getZsb005() {
		return zsb005;
	}

	public String getStort() {
		return stort;
	}

	public String getEqart() {
		return eqart;
	}

	public String getSbfl() {
		return sbfl;
	}

	public String getHerst() {
		return herst;
	}

	public String getHerld() {
		return herld;
	}

	public String getPosid() {
		return posid;
	}

	public String getZsb006() {
		return zsb006;
	}

	public String getTplnr() {
		return tplnr;
	}

	public Integer getZcabn_ztpm1005() {
		return zcabn_ztpm1005;
	}

	public String getZcabn_ztpm1006() {
		return zcabn_ztpm1006;
	}

	public String getBeber() {
		return beber;
	}

	public String getInbdt() {
		return inbdt;
	}

	public String getTypbz() {
		return typbz;
	}

	public String getSerge() {
		return serge;
	}

	public String getBaujj() {
		return baujj;
	}

	public String getBaumm() {
		return baumm;
	}

	public String getSwerk() {
		return swerk;
	}

	public String getAnlnr() {
		return anlnr;
	}

	public String getEqunr() {
		return equnr;
	}

	public String getTbbs() {
		return tbbs;
	}

	public String getZsyn_time() {
		return zsyn_time;
	}

	public String getZsb011() {
		return zsb011;
	}

	public String getOperation() {
		return operation;
	}




}
