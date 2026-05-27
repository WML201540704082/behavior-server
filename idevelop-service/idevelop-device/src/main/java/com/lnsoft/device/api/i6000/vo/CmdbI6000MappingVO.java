package com.lnsoft.device.api.i6000.vo;

import com.lnsoft.cmdb.entity.CmdbCiAttr;
import com.lnsoft.device.api.i6000.entity.I6000CiAttr;
import lombok.Data;

import java.util.List;

@Data
public class CmdbI6000MappingVO {

	private List<CmdbCiAttr> cmdbCiAttrList;

	private List<I6000CiAttr> i6000CiAttrList;
}
