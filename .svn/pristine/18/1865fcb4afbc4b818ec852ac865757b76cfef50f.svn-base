package citic.gack.sso.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.ApplyRoleDTO;
import citic.gack.sso.admin.dto.OperatePermissionDTO;
import citic.gack.sso.admin.dto.RoleDTO;
import citic.gack.sso.admin.dto.RolePermissionDTO;
import citic.gack.sso.admin.dto.SysUserDTO;
import citic.gack.sso.admin.service.ApplyRoleService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysOperatePermission;
import citic.gack.sso.entity.SysPermission;
import citic.gack.sso.mapper.ApplyOperatePermissionChangeMapper;
import citic.gack.sso.mapper.ApplyOperatePermissionMapper;
import citic.gack.sso.mapper.ApplyPermissionMapper;
import citic.gack.sso.mapper.ApplyRoleMapper;
import citic.gack.sso.mapper.OperatePermissionMapper;
import citic.gack.sso.mapper.PermissionMapper;
import citic.gack.sso.mapper.RoleMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("applyRoleService")
public class ApplyRoleServiceImpl implements ApplyRoleService {
	@Autowired
	private ApplyRoleMapper applyRoleMapper;
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private ApplyPermissionMapper applyPermissionMapper;

	@Autowired
	private PermissionMapper permissionMapper;

	@Autowired
	private ApplyOperatePermissionMapper applyOperatePermissionMapper;

	@Autowired
	private ApplyOperatePermissionChangeMapper applyOperatePermissionChangeMapper;

	@Autowired
	private OperatePermissionMapper operatePermissionMapper;

	@Override
	public ApplyRoleDTO insert(ApplyRoleDTO entity) throws SysException {
		try {
			String roleId = UUIDGenerator.getUUID();
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setApplyDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setId(roleId);
			if (StringUtils.isBlank(entity.getRoleId())) {
				entity.setRoleId(roleId);
			}

			applyRoleMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("RoleService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(ApplyRoleDTO entity) throws SysException {
		int flag = 0;
		try {
			String nowDate = DateTools.getSysDate19();
			ApplyRoleDTO tmp = applyRoleMapper.queryById(entity);
			if (ConstantsUtils.APPLY_ROLE_TYPE_R.equals(tmp.getApplyType()) && ConstantsUtils.AUDIT_STATUS_A.equals(entity.getAuditStatus())) {

				RoleDTO role = new RoleDTO();
				BeanUtils.copyProperties(role, tmp);
				role.setOperateDate(nowDate);
				role.setCreateDate(nowDate);
				role.setOperator(tmp.getAuditor());
				role.setOperatorId(tmp.getAuditorId());
				if (tmp != null && ConstantsUtils.CHANGE_TYPE_U.equals(tmp.getChangeType())) {
					roleMapper.updateById(role);
				} else if (tmp != null && ConstantsUtils.CHANGE_TYPE_A.equals(tmp.getChangeType())) {
					roleMapper.save(role);
				}

			} else if (ConstantsUtils.APPLY_ROLE_TYPE_P.equals(tmp.getApplyType()) && ConstantsUtils.AUDIT_STATUS_A.equals(entity.getAuditStatus())) {

				String roleId = tmp.getRoleId();
				String applyRoleId = tmp.getId();
				List<SysPermission> permissionList = applyPermissionMapper.queryApplyRoleMenuPermission(applyRoleId);
				List<SysOperatePermission> operatePermissionList = applyOperatePermissionMapper.queryApplyRoleOperatePermission(applyRoleId);
				permissionMapper.deleteByRoleId(roleId);
				operatePermissionMapper.deleteByRoleId(roleId);

				for (SysPermission permission : permissionList) {
					permission.setCreatorId(tmp.getAuditorId());
					permission.setCreator(tmp.getAuditor());
					permission.setOperator(tmp.getAuditor());
					permission.setOperatorId(tmp.getAuditorId());
					permission.setCreateDate(nowDate);
					permission.setOperateDate(nowDate);
					permission.setSts(ConstantsUtils.STS_A);
					permissionMapper.save(permission);
				}
				for (SysOperatePermission operatePermission : operatePermissionList) {
					operatePermission.setCreatorId(tmp.getAuditorId());
					operatePermission.setCreator(tmp.getAuditor());
					operatePermission.setOperator(tmp.getAuditor());
					operatePermission.setOperatorId(tmp.getAuditorId());
					operatePermission.setCreateDate(nowDate);
					operatePermission.setOperateDate(nowDate);
					operatePermission.setSts(ConstantsUtils.STS_A);
					operatePermissionMapper.save(operatePermission);
				}
				queryApplyRolePermission(tmp.getId(), tmp.getRoleId());

			}

			flag = applyRoleMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("ApplyUserService:update", e);

		}

	}

	public void queryApplyRolePermission(String applyRoleId, String roleId) throws SysException {
		List<RolePermissionDTO> recordList = null;
		try {
			SysUserDTO sysUserMVO = new SysUserDTO();

			List<OperatePermissionDTO> roleMenuPermissionList = applyOperatePermissionMapper.queryApplyRoleMenuPermission(applyRoleId, roleId);
			for (OperatePermissionDTO opVO : roleMenuPermissionList) {
				if ("增加".equals(opVO.getFlagType()) || "减少".equals(opVO.getFlagType())) {
					opVO.setOperationName(opVO.getOperationName() + "(" + opVO.getFlagType() + ")");
				}
				sysUserMVO.addSetPerm(opVO);
			}

			recordList = new ArrayList<RolePermissionDTO>();

			Set<String> set = sysUserMVO.getSetPerm();
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
				if (opName != null)
					user.setPermissionHtml(opName.toString());
				user.setApplyRoleId(applyRoleId);
				applyOperatePermissionChangeMapper.save(user);
			}

		} catch (Exception e) {
			throw new SysException("ApplyRolePermissionServiceImpl:queryApplyRolePermission", e);

		}

	}

	@Override
	public int updateBatch(List<ApplyRoleDTO> delList) throws SysException {
		int flag = 0;
		try {
			String nowDate = DateTools.getSysDate19();
			for (ApplyRoleDTO role : delList) {
				role.setOperateDate(nowDate);
				role.setAuditDate(nowDate);
				int updateCount = this.update(role);
				if (updateCount > 0) {
					flag = flag + 1;
				}
			}

			return flag;
		} catch (Exception e) {
			throw new SysException("ApplyUserService:updateBatch", e);

		}
	}

	@Override
	public void delete(ApplyRoleDTO entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			applyRoleMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("RoleService:delete", e);

		}

	}

	@Override
	public List<ApplyRoleDTO> queryList(ApplyRoleDTO entity) throws SysException {
		List<ApplyRoleDTO> list = null;
		try {
			list = applyRoleMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("RoleService:queryList", e);

		}
		return list;
	}

	@Override
	public ApplyRoleDTO queryBean(ApplyRoleDTO entity) throws SysException {
		try {
			entity = applyRoleMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("RoleService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(ApplyRoleDTO role, PageInfo pageInfo) throws SysException {
		try {
			List<ApplyRoleDTO> list = applyRoleMapper.queryListByPage(role, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("RoleService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<ApplyRoleDTO> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (ApplyRoleDTO role : delList) {
					this.delete(role);
				}
			}
		} catch (Exception e) {
			throw new SysException("RoleService:deleteBatch", e);

		}

	}

}
