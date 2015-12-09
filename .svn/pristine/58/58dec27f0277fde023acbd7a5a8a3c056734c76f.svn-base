package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.ApplyUserDTO;
import citic.gack.sso.admin.dto.RoleDTO;
import citic.gack.sso.admin.dto.SysUserDTO;
import citic.gack.sso.admin.service.ApplyUserService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.cache.CacheManager;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysApplyUserRole;
import citic.gack.sso.entity.SysUserRole;
import citic.gack.sso.mapper.ApplyUserMapper;
import citic.gack.sso.mapper.ApplyUserRoleMapper;
import citic.gack.sso.mapper.SysUserMapper;
import citic.gack.sso.mapper.SysUserRoleMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("applyUserService")
public class ApplyUserServiceImpl implements ApplyUserService {
	@Autowired
	private ApplyUserMapper applyUserMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private ApplyUserRoleMapper applyUserRoleMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Override
	public ApplyUserDTO insert(ApplyUserDTO entity) throws SysException {
		// 1.添加系统用户
		try {
			CacheManager cacheManager = CacheManager.getInstance();
			if (StringUtils.isBlank(entity.getSysUserId())) {
				if ("Y".equals(entity.getInitPwdFlag())) {
					String password = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.sysconfig", "990001");
					entity.setPassword(password);
				}
				entity.setSysUserId(UUIDGenerator.getUUID());
			}
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setApplyDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setId(UUIDGenerator.getUUID());
			entity.setPwdSetTime(nowDate);
			entity.setCreateDate(nowDate);

			applyUserMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("ApplyUserService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(ApplyUserDTO entity) throws SysException {
		int flag = 0;
		try {
			String nowDate = DateTools.getSysDate19();
			ApplyUserDTO tmp = applyUserMapper.queryById(entity);
			if (ConstantsUtils.APPLY_USER_TYPE_U.equals(tmp.getApplyType()) && ConstantsUtils.AUDIT_STATUS_A.equals(entity.getAuditStatus())) {

				SysUserDTO sysuser = new SysUserDTO();
				BeanUtils.copyProperties(sysuser, tmp);
				sysuser.setOperateDate(nowDate);
				sysuser.setCreateDate(nowDate);
				sysuser.setOperator(tmp.getAuditor());
				sysuser.setOperatorId(tmp.getAuditorId());
				if (tmp != null && ConstantsUtils.CHANGE_TYPE_U.equals(tmp.getChangeType())) {
					sysUserMapper.updateByApply(sysuser);
				} else if (tmp != null && ConstantsUtils.CHANGE_TYPE_A.equals(tmp.getChangeType())) {
					sysUserMapper.save(sysuser);
				}
			} else {
				try {

					String sysUserId = tmp.getSysUserId();
					String applyUserId = tmp.getId();
					List<RoleDTO> lists = applyUserRoleMapper.queryApplyUserRole(applyUserId, sysUserId);
					if (lists != null) {
						for (RoleDTO role : lists) {
							if (!"增加".equals(role.getFlagType()) && !"减少".equals(role.getFlagType())) {
								role.setFlagType("原有");
							}
							role.setApplyUserId(applyUserId);
							applyUserRoleMapper.saveApplyUserRoleChange(role);
						}
					}
					// 删除系统员工的员工角色
					int result = sysUserRoleMapper.deleteUserRoleByUserId(sysUserId);
					if (result >= 0) {
						// 添加员工角色
						SysApplyUserRole applyUserRole = new SysApplyUserRole();
						applyUserRole.setApplyUserId(applyUserId);
						List<SysApplyUserRole> list = applyUserRoleMapper.queryList(applyUserRole);
						if (list != null) {
							SysUserRole userRole = null;
							for (SysApplyUserRole role : list) {
								userRole = new SysUserRole();
								userRole.setSts(ConstantsUtils.STS_A);
								userRole.setCreateDate(nowDate);
								userRole.setOperator(nowDate);
								userRole.setSysUserId(sysUserId);
								userRole.setRoleId(role.getRoleId());
								userRole.setSysUserRoleId(UUIDGenerator.getUUID());
								sysUserRoleMapper.save(userRole);
							}
						}

					}
				} catch (Exception e) {
					throw new SysException("SysUserPermissionService:update", e);

				}

				SysUserDTO sysuser = new SysUserDTO();
				BeanUtils.copyProperties(sysuser, tmp);
				sysuser.setOperateDate(nowDate);
				sysuser.setCreateDate(nowDate);
				sysuser.setOperator(tmp.getAuditor());
				sysuser.setOperatorId(tmp.getAuditorId());
				if (tmp != null && ConstantsUtils.CHANGE_TYPE_U.equals(tmp.getChangeType())) {
					sysUserMapper.updateByApply(sysuser);
				} else if (tmp != null && ConstantsUtils.CHANGE_TYPE_A.equals(tmp.getChangeType())) {
					sysUserMapper.save(sysuser);
				}

			}
			flag = applyUserMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("ApplyUserService:update", e);

		}

	}

	@Override
	public int updateBatch(List<ApplyUserDTO> delList) throws SysException {
		int flag = 0;
		try {
			String nowDate = DateTools.getSysDate19();
			for (ApplyUserDTO user : delList) {
				user.setOperateDate(nowDate);
				user.setAuditDate(nowDate);
				int updateCount = this.update(user);
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
	public void delete(ApplyUserDTO entity) throws SysException {
		try {
			applyUserMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("ApplyUserService:delete", e);

		}

	}

	@Override
	public List<ApplyUserDTO> queryList(ApplyUserDTO entity) throws SysException {
		List<ApplyUserDTO> list = null;
		try {
			list = applyUserMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("ApplyUserService:queryList", e);

		}
		return list;
	}

	@Override
	public ApplyUserDTO queryBean(ApplyUserDTO entity) throws SysException {
		try {
			entity = applyUserMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("ApplyUserService:queryBean", e);

		}
		return entity;
	}

	@Override
	public void deleteBatch(List<ApplyUserDTO> delList) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			for (ApplyUserDTO user : delList) {
				user.setOperateDate(nowDate);
				applyUserMapper.deleteById(user);
			}
		} catch (Exception e) {
			throw new SysException("ApplyUserService:deleteBatch", e);

		}

	}

	@Override
	public PageInfo queryListByPage(ApplyUserDTO sysUser, PageInfo pageInfo) throws SysException {
		try {
			List<ApplyUserDTO> list = applyUserMapper.queryListByPage(sysUser, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("ApplyUserService:queryListByPage", e);

		}

		return pageInfo;
	}

}
