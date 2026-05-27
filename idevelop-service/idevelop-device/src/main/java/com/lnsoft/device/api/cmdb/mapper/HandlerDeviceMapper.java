package com.lnsoft.device.api.cmdb.mapper;

import com.lnsoft.device.api.stock.dto.SimpleSafeAccessSwitchesDTO;
import com.lnsoft.device.api.cmdb.entity.FileInfo;
import com.lnsoft.system.entity.Dept;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HandlerDeviceMapper {

	@Select("select id from idevelop_resource_cabinets where room_id = #{roomId} and cabinets_name = #{cabinetsName} limit 1")
	String findCabinetsUuid(@Param("roomId") String roomId, @Param("cabinetsName") String cabinetsName);

	@Select("select uuid from idevelop_resource_room where region_code = #{regionCode} and room_name = #{roomName} limit 1")
	String findComputerRoomUuid(@Param("roomName") String roomName, @Param("regionCode") String regionCode);

	@Select("select * from idevelop_dept where parent_id = (select id FROM idevelop_dept WHERE region_code = #{regionCode} limit 1) and full_name <> '国网胜利(东营)供电有限公司'")
	List<Dept> findDeptByParent(@Param("regionCode") String regionCode);

	int selectSortCodeByTypeCode(@Param("claccify") String claccify, @Param("type") String type);

	@Select("select i6000_code from idevelop_hardware_basic_tree where device_type = #{deviceType} limit 1")
	String selectI6000ByDeviceCode(@Param("deviceType") String deviceType);

	@Select(" select net_flag from o_idevelop_cmdb_data_type where device_type = #{deviceType} limit 1")
	String selectNetFlagByDeviceType(@Param("deviceType") String deviceType);

	SimpleSafeAccessSwitchesDTO getInfoByIP(@Param("ip") String ip);

	@Insert("insert into (file_name,file_status,export_json,export_time) values (#{fileName},#{fileStatus},#{exportJson},#{exportTime})")
	int addexportFileInfo(FileInfo dto);

	@Select("select id,file_name fileName,file_status fileStatus,export_json exportJson,export_time exportTime" +
		"from idevelop_export_file")
	List<FileInfo> findFileInfoList();

	@Update("update idevelop_device_warning set status = 1 where basic_device_code = #{basicDeviceCode}")
	int updateStatus(@Param("basicDeviceCode") String basicDeviceCode);
}
