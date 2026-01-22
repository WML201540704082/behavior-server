package com.lnsoft.seata.storage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * storage
 *
 * @author guozhao
 */
@Data
@TableName("tb_storage")
public class Storage implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String commodityCode;
	private Long count;

}
