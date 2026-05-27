package com.lnsoft.device.api.erp.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.common.tool.CommonUtil;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.device.annotation.TripleApiLogA;
import com.lnsoft.device.api.asset.dto.ProjectManagerDTO;
import com.lnsoft.device.api.asset.entity.ProjectManager;
import com.lnsoft.device.api.asset.service.IProjectManagerService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.erp.ZfiFmXtMainInt;
import com.lnsoft.device.api.erp.ZfiFmXtMainService;
import com.lnsoft.device.api.erp.dto.*;
import com.lnsoft.device.api.erp.entity.*;
import com.lnsoft.device.api.erp.response.ErpPersonAuthResp;
import com.lnsoft.device.api.erp.response.ErpTransEqunrResp;
import com.lnsoft.device.api.erp.response.ErpTransZcbfResp;
import com.lnsoft.device.api.erp.service.IErpMaintainService;
import com.lnsoft.device.api.erp.service.IErpProjectTypeService;
import com.lnsoft.device.api.erp.service.IErpService;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.eums.TripleApiLogValueEnum;
import com.lnsoft.device.eums.TripleTypeEnum;
import com.lnsoft.device.props.ErpMassageProperties;
import com.lnsoft.device.props.ThirdProperties;
import com.lnsoft.device.vo.CiCientitySearchVO;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.lnsoft.device.eums.InterFaceNameEnum.*;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/20 10:44
 * @Description: ErpServiceImpl
 */
@Service
@AllArgsConstructor
public class ErpServiceImpl implements IErpService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErpServiceImpl.class);
	private static final DateTimeFormatter FORMATTER1 = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final DateTimeFormatter FORMATTER2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private ErpMassageProperties erpProperties;
	private IProjectManagerService projectManagerService;
	private IErpMaintainService erpMaintainService;
	private IErpProjectTypeService erpProjectTypeService;
	private ThirdProperties thirdProperties;
	private ICmdbService iCmdbService;


	/**
	 * 成本中心基础数据(实物保管部门,使用保管部门)
	 *
	 * @param maintainCode 维护工厂
	 * @return
	 */
	public List<Map<String, String>> getKostl(String maintainCode) {
		try {

			ZfiFmXtMainInt zfiFmXtMainInt = setPasswordAuthentication();

			String request = String.format("<DATA>" +
				"<INTERFACENAME>%s</INTERFACENAME>" +
				"<INPUT>" +
				"<SWERK>%s</SWERK>" +
				"</INPUT>" +
				"</DATA>", GET_KOSTL.getValue(), maintainCode);
			String response = zfiFmXtMainInt.zfiFmXtMain(request);

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setXIncludeAware(false);
			documentBuilderFactory.setExpandEntityReferences(false);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document parse = documentBuilder.parse(new InputSource(new StringReader(response)));
			String message = "";
			NodeList messageList = parse.getDocumentElement().getElementsByTagName("MESSAGE");
			for (int i = 0; i < messageList.getLength(); i++) {
				Node item = messageList.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					message = item.getTextContent();
				}
			}
			NodeList codeList = parse.getDocumentElement().getElementsByTagName("CODE");
			for (int i = 0; i < codeList.getLength(); i++) {
				Node item = codeList.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					String code = item.getTextContent();
					if (!StringUtils.pathEquals("S", code)) {
						throw new RuntimeException("获取成本中心基础数据失败: " + message);
					}
				}
			}
			NodeList itemList = parse.getDocumentElement().getElementsByTagName("ITEM");
			List<Map<String, String>> returnList = new ArrayList<>();
			for (int i = 0; i < itemList.getLength(); i++) {
				Node item = itemList.item(i);
				Map<String, String> map = new HashMap<>();
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) item;
					// 成本中心编码
					NodeList kostl = element.getElementsByTagName("KOSTL");
					if (kostl.getLength() > 0) {
						map.put("kostl", kostl.item(0).getTextContent());
					}
					// 成本中心描述
					NodeList kostlT = element.getElementsByTagName("KOSTL_T");
					if (kostlT.getLength() > 0) {
						map.put("kostlT", kostlT.item(0).getTextContent());
					}
					// 成本中心全描述
					NodeList kostlLt = element.getElementsByTagName("KOSTL_LT");
					if (kostlLt.getLength() > 0) {
						map.put("kostlLt", kostlLt.item(0).getTextContent());
					}
					// 工厂编码
					NodeList swerk = element.getElementsByTagName("SWERK");
					if (swerk.getLength() > 0) {
						map.put("swerk", swerk.item(0).getTextContent());
					}
				}
				returnList.add(map);
			}
			return returnList;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 功能位置主数据接口
	 *
	 * @param erpTranstplnrDTO
	 * @return
	 */
	@Override
	@TripleApiLogA(value = TripleApiLogValueEnum.ERP_TRANS_TPLNR, tripleType = TripleTypeEnum.ERP)
	public Map<String, String> transTplnr(ErpTranstplnrDTO erpTranstplnrDTO) {
		try {
			// 校验
			String[] split = erpTranstplnrDTO.getTrlnr().split("-");
			if (split.length != 4) {
				throw new Exception("功能位置编码长度错误！");
			}
			if (!StringUtils.pathEquals("06", split[0])) {
				throw new Exception("直辖市公司编码错误！");
			}
			if (!StringUtils.pathEquals("T", split[2])) {
				throw new Exception("专业分类标识错误！");
			}
			if (split[3].length() != 5) {
				throw new Exception("运行位置标识长度错误！");
			}
			Map<String, String> returnMap = new HashMap<>();
			ZfiFmXtMainInt zfiFmXtMainInt = setPasswordAuthentication();
			String request = String.format("<DATA>" +
					"<INTERFACENAME>%s</INTERFACENAME>" +
					"<INPUT>" +
					"<TPLNR>%s</TPLNR>" +
					"<PLTXT>%s</PLTXT>" +
					"<ZSBDYDJ>%s</ZSBDYDJ>" +
					"<BEBER>%s</BEBER>" +
					"<SWERK>%s</SWERK>" +
					"<TPLMA>%s</TPLMA>" +
					"<OPERATION>%s</OPERATION>" +
					"</INPUT>" +
					"</DATA>", TRANS_TPLNR.getValue(), erpTranstplnrDTO.getTrlnr(),
				erpTranstplnrDTO.getPltxt(), erpTranstplnrDTO.getZsbdydj(), erpTranstplnrDTO.getBeber(), erpTranstplnrDTO.getSwerk(),
				erpTranstplnrDTO.getTplma(), erpTranstplnrDTO.getOperation());
			String response = zfiFmXtMainInt.zfiFmXtMain(request);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setXIncludeAware(false);
			documentBuilderFactory.setExpandEntityReferences(false);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document parse = documentBuilder.parse(new InputSource(new StringReader(response)));

			String message = "";
			NodeList messageList = parse.getDocumentElement().getElementsByTagName("MESSAGE");
			for (int i = 0; i < messageList.getLength(); i++) {
				Node item = messageList.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					message = item.getTextContent();
					returnMap.put("message", message);
				}
			}
			NodeList codeList = parse.getDocumentElement().getElementsByTagName("CODE");
			for (int i = 0; i < codeList.getLength(); i++) {
				Node item = codeList.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					String code = item.getTextContent();
					if (!StringUtils.pathEquals("S", code)) {
						throw new RuntimeException("功能位置主数据接口同步失败: " + message);
					}
					returnMap.put("code", code);
				}
			}
			return returnMap;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 获取WBS基础数据
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public List<ProjectManager> getWbs(ProjectManagerDTO dto) {
		try {
			if (Objects.isNull(dto.getProjectCreateStartTime()) || Objects.isNull(dto.getProjectCreateEndTime())) {
				throw new RuntimeException("查询项目创建时间不能为空");
			}

			String startTime = FORMATTER1.format(dto.getProjectCreateStartTime());
			String endTime = FORMATTER1.format(dto.getProjectCreateEndTime());

			ZfiFmXtMainInt zfiFmXtMainInt = setPasswordAuthentication();
			String request = String.format(
				"<DATA>" +
					"<INTERFACENAME>%s</INTERFACENAME>" +
					"<INPUT>" +
					"<SWERK>%s</SWERK>" +
					"<ZZXMLX>%s</ZZXMLX>" +
					"<START_DATE>%s</START_DATE>" +
					"<END_DATE>%s</END_DATE>" +
					"</INPUT>" +
					"</DATA>", GET_WBS.getValue(), dto.getProjectUnitCode(), dto.getProjectTypeCode(), startTime, endTime);
			String response = zfiFmXtMainInt.zfiFmXtMain(request);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setXIncludeAware(false);
			documentBuilderFactory.setExpandEntityReferences(false);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document parse = documentBuilder.parse(new InputSource(new StringReader(response)));

			String message = "";
			NodeList messageList = parse.getDocumentElement().getElementsByTagName("MESSAGE");
			for (int i = 0; i < messageList.getLength(); i++) {
				Node item = messageList.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					message = item.getTextContent();
				}
			}
			NodeList codeList = parse.getDocumentElement().getElementsByTagName("CODE");
			for (int i = 0; i < codeList.getLength(); i++) {
				Node item = codeList.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					String code = item.getTextContent();
					if (!StringUtils.pathEquals("S", code)) {
						throw new RuntimeException("获取WBS基础数据失败: " + message);
					}
				}
			}
			NodeList itemList = parse.getDocumentElement().getElementsByTagName("ITEM");

			ErpMaintain erpMaintainWrapper = new ErpMaintain();
			erpMaintainWrapper.setCode(dto.getProjectUnitCode());
			ErpMaintain erpMaintain = erpMaintainService.getOne(Condition.getQueryWrapper(erpMaintainWrapper));

			ErpProjectType erpProjectTypeWrapper = new ErpProjectType();
			erpProjectTypeWrapper.setProjectType(dto.getProjectTypeCode());
			ErpProjectType erpProjectType = erpProjectTypeService.getOne(Condition.getQueryWrapper(erpProjectTypeWrapper));

			List<ProjectManager> projectManagerList = new ArrayList<>();
			for (int i = 0; i < itemList.getLength(); i++) {
				Node item = itemList.item(i);
				ProjectManager projectManager = new ProjectManager();
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) item;
					// 项目类型
					NodeList zzxmlx = element.getElementsByTagName("ZZXMLX");
					if (zzxmlx.getLength() > 0) {
						String textContent = zzxmlx.item(0).getTextContent();
						projectManager.setProjectTypeCode(textContent);
						projectManager.setProjectType(erpProjectType.getProjectDesc());
					}
					// 项目定义编码
					NodeList pspid = element.getElementsByTagName("PSPID");
					if (pspid.getLength() > 0) {
						projectManager.setProjectDefineCode(pspid.item(0).getTextContent());
					}
					// 项目定义名称
					NodeList pspidT = element.getElementsByTagName("PSPID_T");
					if (pspidT.getLength() > 0) {
						projectManager.setProjectDefine(pspidT.item(0).getTextContent());
					}
					// WBS编码
					NodeList posid = element.getElementsByTagName("POSID");
					if (posid.getLength() > 0) {
						projectManager.setWbsCode(posid.item(0).getTextContent());
					}
					// WBS描述
					NodeList post1 = element.getElementsByTagName("POST1");
					if (post1.getLength() > 0) {
						projectManager.setWbsName(post1.item(0).getTextContent());
					}
					// 工厂编码
					NodeList swerk = element.getElementsByTagName("SWERK");
					if (swerk.getLength() > 0) {
						String textContent = swerk.item(0).getTextContent();
						projectManager.setProjectUnitCode(textContent);
						projectManager.setProjectUnitName(erpMaintain.getName());
					}
					// 创建时间
					NodeList erdat = element.getElementsByTagName("ERDAT");
					if (erdat.getLength() > 0) {
						String textContent = erdat.item(0).getTextContent() + " 00:00:00";
						if (!StringUtils.pathEquals("0000-00-00 00:00:00", textContent)) {
							projectManager.setProjectCreateTime(LocalDateTime.parse(textContent, FORMATTER2));
						}
					}
					// 删除标识
					NodeList loevm = element.getElementsByTagName("LOEVM");
					if (loevm.getLength() > 0) {
						String textContent = loevm.item(0).getTextContent();
						if (textContent == null || "".equals(textContent)) {
							projectManager.setLoevm("Y");
						} else {
							projectManager.setLoevm(textContent);
						}
					}
				}
				projectManagerList.add(projectManager);
			}
			if (projectManagerService.saveOrUpdateBatch(projectManagerList)) {
				return projectManagerList;
			} else {
				throw new RuntimeException("新增获取WBS基础数据失败: " + response);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 设备台账主数据同步接口 TRANS_EQUNR
	 *
	 * @param erpTransEqunr
	 * @return
	 */
	@Override
	@TripleApiLogA(value = TripleApiLogValueEnum.ERP_INSERT_UPDATE_DATA, tripleType = TripleTypeEnum.ERP)
	public ErpTransEqunrResp transEqunr(ErpTransEqunr erpTransEqunr) {
		try {
			if (Objects.isNull(erpTransEqunr.getOperationType())) {
				throw new RuntimeException("操作标示符(operationType)不能为空");
			}

			ErpTransEqunrDTO transEqunr = new ErpTransEqunrDTO();
			transEqunr.setInterfaceName(TRANS_EQUNR.getValue());
			transEqunr.setXtdocId(erpTransEqunr.getXtdocId());
			transEqunr.setXtdocNo(erpTransEqunr.getXtdocNo());

			ErpTransEqunrInputDTO erpTransEqunrInputDTO = new ErpTransEqunrInputDTO();
			List<ErpTransEqunrItemDTO> equnrItemDTOList = Convert.convert(new TypeReference<List<ErpTransEqunrItemDTO>>() {
			}, erpTransEqunr.getErpTransEqunrItemList());
			equnrItemDTOList.stream().forEach(itemDTO -> {
				itemDTO.setZsyn_time(LocalDateTime.now().format(FORMATTER2));
				itemDTO.setOperation(erpTransEqunr.getOperationType().getValue());
			});

			erpTransEqunrInputDTO.setEqunrItemDTOList(equnrItemDTOList);
			transEqunr.setInput(erpTransEqunrInputDTO);

			JAXBContext jaxbContext = JAXBContext.newInstance(ErpTransEqunrDTO.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(transEqunr, stringWriter);

			// 请求数据
			ZfiFmXtMainInt zfiFmXtMainInt = setPasswordAuthentication();
			String response = zfiFmXtMainInt.zfiFmXtMain(String.valueOf(stringWriter));

			// 处理返回数据
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setXIncludeAware(false);
			documentBuilderFactory.setExpandEntityReferences(false);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document parse = documentBuilder.parse(new InputSource(new StringReader(response)));

			ErpTransEqunrResp equnrResp = new ErpTransEqunrResp();
			NodeList messageList = parse.getDocumentElement().getElementsByTagName("MESSAGE");
			for (int i = 0; i < messageList.getLength(); i++) {
				Node item = messageList.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					String message = item.getTextContent();
					equnrResp.setMessage(message);
				}
			}
			NodeList codeList = parse.getDocumentElement().getElementsByTagName("CODE");
			for (int i = 0; i < codeList.getLength(); i++) {
				Node item = codeList.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					String code = item.getTextContent();
					if (StringUtils.pathEquals("E", code)) {
						throw new RuntimeException("设备台账主数据同步ERP接口全部失败: " + equnrResp.getMessage());
					}
					equnrResp.setCode(code);
				}
			}
			NodeList itemList = parse.getDocumentElement().getElementsByTagName("ITEM");
			List<ErpTransEqunrResp.ItemResp> itemResps = new ArrayList<>();
			for (int i = 0; i < itemList.getLength(); i++) {
				Node item = itemList.item(i);
				ErpTransEqunrResp.ItemResp itemResp = new ErpTransEqunrResp.ItemResp();
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) item;
					// 信通设备ID
					NodeList xtbm = element.getElementsByTagName("XTBM");
					if (xtbm.getLength() > 0) {
						itemResp.setXtbm(xtbm.item(0).getTextContent());
					}
					// ERP设备编码
					NodeList equnr = element.getElementsByTagName("EQUNR");
					if (equnr.getLength() > 0) {
						itemResp.setEqunr(equnr.item(0).getTextContent());
					}
					// 同步标识 S成功, E失败
					NodeList tbbs = element.getElementsByTagName("TBBS");
					if (tbbs.getLength() > 0) {
						itemResp.setTbbs(tbbs.item(0).getTextContent());
					}
					// 消息
					NodeList msg = element.getElementsByTagName("MSG");
					if (msg.getLength() > 0) {
						itemResp.setMsg(msg.item(0).getTextContent());
					}
				}
				itemResps.add(itemResp);
			}
			equnrResp.setItemResp(itemResps);
			return equnrResp;
		} catch (Exception e) {
			CommonUtil.StringWriter(e, "设备台账主数据同步接口异常!");
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 资产报废集成接口
	 *
	 * @param erpTransZcbf
	 * @return
	 */
	@Override
	@TripleApiLogA(value = TripleApiLogValueEnum.ERP_TRANS_ZCBF, tripleType = TripleTypeEnum.ERP)
	public ErpTransZcbfResp transZcbf(ErpTransZcbf erpTransZcbf) {
		try {

			ErpTransZcbfDTO erpTransZcbfDTO = new ErpTransZcbfDTO();
			erpTransZcbfDTO.setInterfaceName(TRANS_ZCBF.getValue());
			erpTransZcbfDTO.setXtdocId(erpTransZcbf.getXtdocId());
			erpTransZcbfDTO.setXtdocNo(erpTransZcbf.getXtdocNo());

			List<ErpTransZcbfItemDTO> zcbfItemDTOList = Convert.convert(new TypeReference<List<ErpTransZcbfItemDTO>>() {
			}, erpTransZcbf.getErpTransZcbfItemList());

			ErpTransZcbfInputDTO erpTransZcbfInputDTO = new ErpTransZcbfInputDTO();
			erpTransZcbfInputDTO.setEqunrItemDTOList(zcbfItemDTOList);

			erpTransZcbfDTO.setInput(erpTransZcbfInputDTO);

			JAXBContext jaxbContext = JAXBContext.newInstance(ErpTransZcbfDTO.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(erpTransZcbfDTO, stringWriter);
			// 请求数据
			LOGGER.info("ERP设备报废请求数据: " + stringWriter);

			ZfiFmXtMainInt zfiFmXtMainInt = setPasswordAuthentication();
			String response = zfiFmXtMainInt.zfiFmXtMain(String.valueOf(stringWriter));
			LOGGER.info("ERP设备报废结果: " + response);


			// 处理返回数据
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setXIncludeAware(false);
			documentBuilderFactory.setExpandEntityReferences(false);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document parse = documentBuilder.parse(new InputSource(new StringReader(response)));

			ErpTransZcbfResp resp = new ErpTransZcbfResp();
			NodeList messageList = parse.getDocumentElement().getElementsByTagName("MESSAGE");
			for (int i = 0; i < messageList.getLength(); i++) {
				Node item = messageList.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					String message = item.getTextContent();
					resp.setMessage(message);
				}
			}
			NodeList codeList = parse.getDocumentElement().getElementsByTagName("CODE");
			for (int i = 0; i < codeList.getLength(); i++) {
				Node item = codeList.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					String code = item.getTextContent();
					if (StringUtils.pathEquals("E", code)) {
						throw new RuntimeException("资产报废集成接口全部失败: " + resp.getMessage());
					}
					resp.setCode(code);
				}
			}
			NodeList itemList = parse.getDocumentElement().getElementsByTagName("ITEM");
			List<ErpTransZcbfResp.ItemResp> itemResps = new ArrayList<>();
			for (int i = 0; i < itemList.getLength(); i++) {
				Node item = itemList.item(i);
				ErpTransZcbfResp.ItemResp itemResp = new ErpTransZcbfResp.ItemResp();
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) item;
					// 信通设备ID
					NodeList xtbm = element.getElementsByTagName("XTBM");
					if (xtbm.getLength() > 0) {
						itemResp.setXtbm(xtbm.item(0).getTextContent());
					}
					// ERP设备编码
					NodeList equnr = element.getElementsByTagName("EQUNR");
					if (equnr.getLength() > 0) {
						itemResp.setEqunr(equnr.item(0).getTextContent());
					}
					// 同步标识 S成功, E失败
					NodeList tbbs = element.getElementsByTagName("TBBS");
					if (tbbs.getLength() > 0) {
						itemResp.setTbbs(tbbs.item(0).getTextContent());
					}
					// 消息
					NodeList msg = element.getElementsByTagName("MSG");
					if (msg.getLength() > 0) {
						itemResp.setMsg(msg.item(0).getTextContent());
					}
				}
				itemResps.add(itemResp);
			}
			resp.setItemResp(itemResps);
			return new ErpTransZcbfResp();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 获取人员权限
	 *
	 * @param erpPersonAuth
	 * @return
	 */
	@Override
	public ErpPersonAuthResp personAuth(ErpPersonAuth erpPersonAuth) {
		try {

			ErpPersonAuthResp resp = new ErpPersonAuthResp();
			if (!thirdProperties.getApiErp()) {

				List<ErpPersonAuthResp.ItemResp> itemResps = new ArrayList<>();
				ErpPersonAuthResp.ItemResp itemResp = new ErpPersonAuthResp.ItemResp();
				itemResp.setDepartment("济南-数字化部门");
				itemResp.setBname("123456789");
				itemResp.setNameTextc("王子欣");

				itemResps.add(itemResp);
				resp.setCode("S");
				resp.setMessage("");
				resp.setItemResp(itemResps);
				return resp;
			}
			// 组装参数
			ErpPersonAuthDTO erpPersonAuthDTO = new ErpPersonAuthDTO();
			erpPersonAuthDTO.setInterfaceName(GET_AUTH.getValue());

			ErpPersonAuthInputDTO erpPersonAuthInputDTO = new ErpPersonAuthInputDTO();
			erpPersonAuthInputDTO.setKostl(erpPersonAuth.getKostl());
			erpPersonAuthInputDTO.setWfId(erpPersonAuth.getWfId());
			erpPersonAuthInputDTO.setNodeId(erpPersonAuth.getNodeId());
			erpPersonAuthDTO.setErpPersonAuthInputDTO(erpPersonAuthInputDTO);

			JAXBContext jaxbContext = JAXBContext.newInstance(ErpPersonAuthDTO.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(erpPersonAuthDTO, stringWriter);
			// 请求数据
			ZfiFmXtMainInt zfiFmXtMainInt = setPasswordAuthentication();
			String response = zfiFmXtMainInt.zfiFmXtMain(String.valueOf(stringWriter));

			// 处理返回数据
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setXIncludeAware(false);
			documentBuilderFactory.setExpandEntityReferences(false);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document parse = documentBuilder.parse(new InputSource(new StringReader(response)));

			NodeList messageList = parse.getDocumentElement().getElementsByTagName("MESSAGE");
			for (int i = 0; i < messageList.getLength(); i++) {
				Node item = messageList.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					String message = item.getTextContent();
					resp.setMessage(message);
				}
			}
			NodeList codeList = parse.getDocumentElement().getElementsByTagName("CODE");
			for (int i = 0; i < codeList.getLength(); i++) {
				Node item = codeList.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					String code = item.getTextContent();
					if (StringUtils.pathEquals("E", code)) {
						throw new RuntimeException("获取人员权限失败: " + resp.getMessage());
					}
					resp.setCode(code);
				}
			}
			NodeList itemList = parse.getDocumentElement().getElementsByTagName("ITEM");
			List<ErpPersonAuthResp.ItemResp> itemResps = new ArrayList<>();
			for (int i = 0; i < itemList.getLength(); i++) {
				Node item = itemList.item(i);
				ErpPersonAuthResp.ItemResp itemResp = new ErpPersonAuthResp.ItemResp();
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) item;
					// 用户账号
					NodeList bname = element.getElementsByTagName("BNAME");
					if (bname.getLength() > 0) {
						itemResp.setBname(bname.item(0).getTextContent());
					}
					// 用户名称
					NodeList nameTextc = element.getElementsByTagName("NAME_TEXTC");
					if (nameTextc.getLength() > 0) {
						itemResp.setNameTextc(nameTextc.item(0).getTextContent());
					}
					// 所在部门
					NodeList department = element.getElementsByTagName("DEPARTMENT");
					if (department.getLength() > 0) {
						itemResp.setDepartment(department.item(0).getTextContent());
					}
				}
				itemResps.add(itemResp);
			}
			resp.setItemResp(itemResps);
			return resp;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * ERP调用修改ERP资产编码 erp => xtyth
	 *
	 * @param erpUpdateAnlnr
	 * @return
	 */
	@Override
	@TripleApiLogA(value = TripleApiLogValueEnum.ERP_UPDATE_ANLNR, tripleType = TripleTypeEnum.ERP)
	public String updateAnlnr(ErpUpdateAnlnr erpUpdateAnlnr) {
		try {
			List<CiCientitySearchVO> ciCientitySearchVOList = new ArrayList<>();

			// 查询条件1 ERP设备台账编码
			CiCientitySearchVO ciCientitySearchVO1 = new CiCientitySearchVO();
			ciCientitySearchVO1.setAttrName(CmdbAttrConstant.DEVICE_CODE_ERP);
			ciCientitySearchVO1.setAttrValue(erpUpdateAnlnr.getEqunr());
			ciCientitySearchVO1.setExpression(Expression.EQUAL);
			ciCientitySearchVOList.add(ciCientitySearchVO1);

			// 查询条件2 设备编码
			String deviceCode = erpUpdateAnlnr.getXtbmNo();
			if (org.apache.commons.lang3.StringUtils.isNotBlank(deviceCode)) {
				CiCientitySearchVO ciCientitySearchVO2 = new CiCientitySearchVO();
				ciCientitySearchVO2.setAttrName(CmdbAttrConstant.DEVICE_CODE);
				ciCientitySearchVO2.setAttrValue(erpUpdateAnlnr.getXtbmNo());
				ciCientitySearchVO2.setExpression(Expression.EQUAL);
				ciCientitySearchVOList.add(ciCientitySearchVO2);
			}
			Query query = new Query();
			query.setCurrent(1);
			query.setSize(10);

			FeignCiCientity ciCientityList = iCmdbService.getCiCientityListByClaccify(ciCientitySearchVOList, query);

			List<Map<String, Object>> data = ciCientityList.getData();
			if (CollectionUtils.isEmpty(data)) {
				throw new RuntimeException("未查到ERP设备台账编码为 " + erpUpdateAnlnr.getEqunr() + " 的设备台账");
			}

			// 获取数据
			Map<String, Object> map = data.get(0);

			String oldAssetCodeErp = (String) map.get(CmdbAttrConstant.ASSET_CODE_ERP);
			String newAssetCodeErp = erpUpdateAnlnr.getNewAnlnr();
			if (StringUtils.pathEquals(oldAssetCodeErp, newAssetCodeErp)) {
				return "新ERP资产编码和旧ERP资产编码相同, 无需更新!";
			}

			// 组装参数
			Long id = (Long) map.get(CmdbAttrConstant.ID);
			Map<String, Object> requestMap = new HashMap<>();
			requestMap.put(CmdbAttrConstant.UUID, map.get(CmdbAttrConstant.UUID));
			requestMap.put(CmdbAttrConstant.ID, id);
			requestMap.put(CmdbAttrConstant.CI_ID, map.get(CmdbAttrConstant.CI_ID));
			requestMap.put(CmdbAttrConstant.ASSET_CODE_ERP, erpUpdateAnlnr.getNewAnlnr());

			// 修改参数
			Map<Long, Map<String, Object>> entity = new HashMap<>();
			entity.put(id, requestMap);
			Map<String, Object> returnMap = iCmdbService.cientityBatchupdate(entity, TransactionActionType.UPDATE);
			Boolean committed = (Boolean) returnMap.get("committed");
			if (Objects.nonNull(committed) && committed) {
				return "更新完成!";
			}
			return "更新失败!";
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 设置账号密码
	 */
	private ZfiFmXtMainInt setPasswordAuthentication() {
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(erpProperties.getUserName(), erpProperties.getPassWord().toCharArray());
			}
		});

		ZfiFmXtMainService zfifmxtmainService = new ZfiFmXtMainService();
		return zfifmxtmainService.getZfiFmXtMain();
	}
}
