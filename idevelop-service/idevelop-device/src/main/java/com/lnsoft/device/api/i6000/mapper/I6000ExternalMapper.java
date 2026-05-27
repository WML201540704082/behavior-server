package com.lnsoft.device.api.i6000.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.api.i6000.entity.I6000CmdbMapping;
import com.lnsoft.device.api.i6000.entity.I6000External;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author xyzadmin
 */
public interface I6000ExternalMapper extends BaseMapper<I6000External> {
	/**
	 * @param page
	 * @param i6000External
	 * @return
	 */
    List<I6000External> selectExternalPage(IPage<I6000External> page, I6000External i6000External);

	void deleteExternal(List<Long> idList);

	@Select("SELECT count(*) FROM `idevelop_cientity_ls` WHERE cientity_uuid = #{uuid}")
	Integer getCount(String uuid);

}
