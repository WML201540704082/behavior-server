package com.lnsoft.device.api.stock.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 治理情况统计返回类
 */
@Data
public class GovernanceSituationVO {
	/**
	 * 设备分类
	 */
	@ExcelProperty("设备分类")
	@ApiModelProperty(value = "设备分类")
	private String deviceType;
	/**
	 * 治理情况
	 */
	@ExcelProperty("治理情况")
	@ApiModelProperty(value = "治理情况")
	private String governance;
	/**
	 * 济南市
	 */
	@ExcelProperty("济南市")
	@ApiModelProperty(value = "济南市")
	private String jiNan;
	/**
	 * 青岛市
	 */
	@ExcelProperty("青岛市")
	@ApiModelProperty(value = "青岛市")
	private String qingDao;
	/**
	 * 设备分类
	 */
	@ExcelProperty("淄博市")
	@ApiModelProperty(value = "淄博市")
	private String ziBo;
	/**
	 * 枣庄市
	 */
	@ExcelProperty("枣庄市")
	@ApiModelProperty(value = "枣庄市")
	private String zaoZhuang;
	/**
	 * 东营市
	 */
	@ExcelProperty("东营市")
	@ApiModelProperty(value = "东营市")
	private String dongYing;
	/**
	 * 烟台市
	 */
	@ExcelProperty("烟台市")
	@ApiModelProperty(value = "烟台市")
	private String yanTai;
	/**
	 * 潍坊市
	 */
	@ExcelProperty("潍坊市")
	@ApiModelProperty(value = "潍坊市")
	private String weiFang;
	/**
	 * 济宁市
	 */
	@ExcelProperty("济宁市")
	@ApiModelProperty(value = "济宁市")
	private String jiNing;
	/**
	 * 泰安市
	 */
	@ExcelProperty("泰安市")
	@ApiModelProperty(value = "泰安市")
	private String taiAn;
	/**
	 * 威海市
	 */
	@ExcelProperty("威海市")
	@ApiModelProperty(value = "威海市")
	private String weiHai;
	/**
	 * 设备分类
	 */
	@ExcelProperty("日照市")
	@ApiModelProperty(value = "日照市")
	private String riZhao;
	/**
	 * 莱芜市
	 */
	@ExcelProperty("莱芜市")
	@ApiModelProperty(value = "莱芜市")
	private String laiWu;
	/**
	 * 设备分类
	 */
	@ExcelProperty("临沂市")
	@ApiModelProperty(value = "临沂市")
	private String linYi;
	/**
	 * 设备分类
	 */
	@ExcelProperty("德州市")
	@ApiModelProperty(value = "德州市")
	private String deZhou;
	/**
	 * 聊城市
	 */
	@ExcelProperty("聊城市")
	@ApiModelProperty(value = "聊城市")
	private String liaoCheng;
	/**
	 * 滨州市
	 */
	@ExcelProperty("滨州市")
	@ApiModelProperty(value = "滨州市")
	private String binZhou;
	/**
	 * 菏泽市
	 */
	@ExcelProperty("菏泽市")
	@ApiModelProperty(value = "菏泽市")
	private String heZe;
	/**
	 * 全省
	 */
	@ExcelProperty("全省")
	@ApiModelProperty(value = "全省")
	private String all;



}
