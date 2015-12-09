package citic.gack.sso.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.ApplyRoleDTO;
import citic.gack.sso.admin.dto.ApplyRolePermissionDTO;
import citic.gack.sso.admin.dto.OperatePermissionDTO;
import citic.gack.sso.admin.dto.OperationDTO;
import citic.gack.sso.admin.dto.RolePermissionDTO;
import citic.gack.sso.admin.dto.SysUserDTO;
import citic.gack.sso.admin.service.ApplyRolePermissionService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysApplyOperatePermission;
import citic.gack.sso.entity.SysApplyPermission;
import citic.gack.sso.mapper.ApplyOperatePermissionChangeMapper;
import citic.gack.sso.mapper.ApplyOperatePermissionMapper;
import citic.gack.sso.mapper.ApplyPermissionMapper;
import citic.gack.sso.mapper.ApplyRoleMapper;
import citic.gack.sso.mapper.MenuCatalogMapper;
import citic.gack.sso.mapper.MenuMapper;
import citic.gack.sso.mapper.MenuOperationMapper;
import citic.gack.sso.mapper.OperationMapper;
import citic.gack.sso.mapper.RoleMapper;
import citic.gack.sso.mapper.SysUserMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

/**
 * 角色授权(RolePermissionService)
 * 
 * @author wangjincheng
 * 
 * @data 2013-3-14下午2:04:04
 */
@Service("applyRolePermissionService")
public class ApplyRolePermissionServiceImpl implements ApplyRolePermissionService {
	private static final Logger logger = LoggerFactory.getLogger(ApplyRolePermissionServiceImpl.class);
	@Autowired
	private ApplyOperatePermissionMapper applyOperatePermissionMapper;
	@Autowired
	private ApplyOperatePermissionChangeMapper applyOperatePermissionChangeMapper;
	@Autowired
	private MenuOperationMapper menuOperationMapper;
	@Autowired
	private ApplyPermissionMapper applyPermissionMapper;
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private MenuCatalogMapper menuCatalogMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private ApplyRoleMapper applyRoleMapper;
	@Autowired
	private OperationMapper operationMapper;

	private List<OperationDTO> operationsList;

	// 菜单授权

	@Override
	public void saveApplyMenuOperatePermission(Map<String, Object> map) throws SysException {
		try {
			ApplyRolePermissionDTO DTO = (ApplyRolePermissionDTO) map.get("model");

			ApplyRoleDTO applyRole = new ApplyRoleDTO();
			String applyId = UUIDGenerator.getUUID();
			String nowDate = DateTools.getSysDate19();
			applyRole.setCreateDate(nowDate);
			applyRole.setApplyDate(nowDate);
			applyRole.setOperateDate(nowDate);
			applyRole.setSts(ConstantsUtils.STS_A);
			applyRole.setId(applyId);
			applyRole.setRoleId(DTO.getRoleId());
			applyRole.setRoleCode(DTO.getRoleCode());
			applyRole.setRoleName(DTO.getRoleName());
			applyRole.setApplyorId(DTO.getCreatorId());
			applyRole.setApplyor(DTO.getCreator());
			applyRole.setCreator(DTO.getCreator());
			applyRole.setCreatorId(DTO.getCreatorId());
			applyRole.setAuditStatus(ConstantsUtils.AUDIT_STATUS_I);
			applyRole.setApplyType(ConstantsUtils.APPLY_ROLE_TYPE_P);
			applyRoleMapper.save(applyRole);

			DTO.setApplyRoleId(applyId);

			// 1.删除角色操作许可2.删除角色功能许可
			String[] menuIds = (String[]) map.get("menuIds");
			if (menuIds != null && menuIds.length > 0) {
				for (String menuId : menuIds) {
					DTO.setMenuId(menuId);
					String[] menuOperations = (String[]) map.get(menuId);
					if (menuOperations != null && menuOperations.length > 0) {
						// 3.添加角色功能许可
						SysApplyPermission pmsion = savePermission(DTO, "menu");
						// 4.添加角色操作许可
						this.saveOperatePermission(menuOperations, DTO, pmsion, "menu");

					}
				}
			}
		} catch (Exception e) {
			throw new SysException("ApplyRolePermissionServiceImpl:saveMenuOperatePermission", e);

		}
	}

	/**
	 * 添加角色操作许可
	 */
	private int saveOperatePermission(String[] values, ApplyRolePermissionDTO DTO, SysApplyPermission pmsion, String type) {

		int result = 0;
		try {
			SysApplyOperatePermission opm;
			String nowDate = DateTools.getSysDate19();
			for (String mpId : values) {
				opm = new SysApplyOperatePermission();
				opm.setId(UUIDGenerator.getUUID());
				opm.setRoleId(DTO.getRoleId());
				opm.setCreateDate(nowDate);
				opm.setOperateDate(nowDate);
				opm.setSts(ConstantsUtils.STS_A);
				opm.setPermissionId(pmsion.getPermissionId());
				opm.setCreator(DTO.getCreator());
				opm.setCreatorId(DTO.getCreatorId());
				opm.setApplyRoleId(DTO.getApplyRoleId());
				if ("menu".equals(type)) {
					opm.setMenuOperationId(mpId);
				} else {
					opm.setCatalogOperateType(mpId);
				}
				applyOperatePermissionMapper.save(opm);
				result++;
			}
		} catch (Exception e) {
			throw new SysException("ApplyRolePermissionService:saveOperatePermission", e);

		}
		return result;
	}

	/**
	 * 添加角色功能许可
	 */
	// ***************
	private SysApplyPermission savePermission(ApplyRolePermissionDTO DTO, String type) {

		SysApplyPermission pmsion = new SysApplyPermission();
		String id = UUIDGenerator.getUUID();
		try {
			String nowDate = DateTools.getSysDate19();
			pmsion.setCreateDate(nowDate);
			pmsion.setOperateDate(nowDate);
			pmsion.setApplyRoleId(DTO.getApplyRoleId());
			pmsion.setSts(ConstantsUtils.STS_A);
			pmsion.setId(id);
			pmsion.setPermissionId(id);
			pmsion.setCreator(DTO.getCreator());
			if ("menu".equals(type)) {
				pmsion.setMenuId(DTO.getMenuId());
			} else {
				pmsion.setMenuCatalogId(DTO.getMenuCatalogId());
			}
			pmsion.setRoleId(DTO.getRoleId());

			applyPermissionMapper.save(pmsion);
		} catch (Exception e) {
			throw new SysException("ApplyRolePermissionService:savePermission", e);

		}
		return pmsion;
	}

	@Override
	public PageInfo queryApplyRolePermissionChangeByPage(ApplyRolePermissionDTO mvo, PageInfo pageInfo) throws SysException {
		try {
			List<RolePermissionDTO> recordList = applyOperatePermissionChangeMapper.queryApplyRoleOperatePermission(mvo.getApplyRoleId());
			pageInfo.setRows(recordList);
			return pageInfo;
		} catch (Exception e) {
			throw new SysException("ApplyRolePermissionServiceImpl:queryApplyRolePermissionChangeByPage", e);

		}
	}

	@Override
	public PageInfo queryApplyRolePermission(ApplyRolePermissionDTO mvo, PageInfo pageInfo) throws SysException {
		try {
			SysUserDTO sysUserMVO = new SysUserDTO();

			/** 1.查询当前角色的菜单操作权限 */
			logger.debug("获取鉴权信息：开始获取【{}】的角色菜单操作权限信息", mvo.getRoleName());
			List<OperatePermissionDTO> roleMenuPermissionList = applyOperatePermissionMapper.queryApplyRoleMenuPermission(mvo.getApplyRoleId(), mvo.getRoleId());
			for (OperatePermissionDTO opVO : roleMenuPermissionList) {
				if ("增加".equals(opVO.getFlagType()) || "减少".equals(opVO.getFlagType())) {
					opVO.setOperationName(opVO.getOperationName() + "(" + opVO.getFlagType() + ")");
				}
				sysUserMVO.addSetPerm(opVO);
			}
			initGeneralRolePermission(sysUserMVO, pageInfo);

		} catch (Exception e) {
			throw new SysException("ApplyRolePermissionServiceImpl:searchRoleView", e);

		}

		return pageInfo;
	}

	/**
	 * 加载一般角色的功能视图
	 * 
	 * @param sysUser
	 * @param PageInfo
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initGeneralRolePermission(SysUserDTO sysUser, PageInfo pageInfo) {
		List<RolePermissionDTO> recordList = new ArrayList<RolePermissionDTO>();
		try {
			Set<String> set = sysUser.getSetPerm();
			Iterator<String> it = set.iterator();
			RolePermissionDTO roleDTO;
			Map<String, RolePermissionDTO> recordMap = new HashMap<String, RolePermissionDTO>();
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			while (it.hasNext()) {
				String messages = it.next();
				String[] message = messages.split("-");
				roleDTO = new RolePermissionDTO();
				roleDTO.setMenuId(message[0]);
				if (recordMap.get(roleDTO.getMenuId()) == null) {
					roleDTO.setMenuName(message[2]);
					roleDTO.setMenuUrl(message[3]);
					recordList.add(roleDTO);
					recordMap.put(roleDTO.getMenuId(), roleDTO);
				}
				List<String> list = map.get(message[0]);
				if (list == null) {
					list = new ArrayList<String>();
					list.add(message[4]);
					map.put(message[0], list);
				} else {
					list.add(message[4]);
					map.put(message[0], list);
				}
			}

			for (RolePermissionDTO user : recordList) {
				List<String> opName = map.get(user.getMenuId());
				Collections.sort(opName);
				if (opName != null)
					user.setPermissionHtml(opName.toString());
			}
			Collections.sort(recordList, new Comparator() {
				@Override
				public int compare(Object a, Object b) {
					String one = ((RolePermissionDTO) a).getMenuId();
					String two = ((RolePermissionDTO) b).getMenuId();
					return one.compareTo(two);
				}
			});
		} catch (Exception e) {
			throw new SysException("RolePermissionService:initGeneralRolePermission", e);

		}

		pageInfo.setRows(recordList);
	}

}
