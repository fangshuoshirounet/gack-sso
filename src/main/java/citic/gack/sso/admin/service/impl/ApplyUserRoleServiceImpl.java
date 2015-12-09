package citic.gack.sso.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.ApplyUserDTO;
import citic.gack.sso.admin.dto.RoleDTO;
import citic.gack.sso.admin.service.ApplyUserRoleService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysApplyUserRole;
import citic.gack.sso.mapper.ApplyUserRoleMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("applyUserRoleService")
public class ApplyUserRoleServiceImpl implements ApplyUserRoleService {
	@Autowired
	private ApplyUserRoleMapper applyUserRoleMapper;

	@Override
	public SysApplyUserRole insert(SysApplyUserRole entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setId(UUIDGenerator.getUUID());
			applyUserRoleMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("ApplyUserRoleService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysApplyUserRole entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = applyUserRoleMapper.updateById(entity);
		} catch (Exception e) {
			throw new SysException("ApplyUserRoleService:update", e);

		}
		return flag;
	}

	@Override
	public void delete(SysApplyUserRole entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			applyUserRoleMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("ApplyUserRoleService:delete", e);

		}

	}

	@Override
	public List<SysApplyUserRole> queryList(SysApplyUserRole entity) throws SysException {
		List<SysApplyUserRole> list = null;
		try {
			list = applyUserRoleMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("ApplyUserRoleService:queryList", e);

		}
		return list;
	}

	@Override
	public SysApplyUserRole queryBean(SysApplyUserRole entity) throws SysException {
		try {
			entity = applyUserRoleMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("ApplyUserRoleService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysApplyUserRole applyUserRole, PageInfo pageInfo) throws SysException {
		try {
			List<SysApplyUserRole> list = applyUserRoleMapper.queryListByPage(applyUserRole, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("ApplyUserRoleService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysApplyUserRole> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysApplyUserRole applyUserRole : delList) {
					this.delete(applyUserRole);
				}
			}
		} catch (Exception e) {
			throw new SysException("ApplyUserRoleService:deleteBatch", e);

		}

	}

	@Override
	public PageInfo querySysUserRole(ApplyUserDTO sysUser, PageInfo pageInfo) throws SysException {
		List<RoleDTO> list = new ArrayList<RoleDTO>();
		try {
			List<RoleDTO> lists = applyUserRoleMapper.queryApplyUserRoleByPage(sysUser, pageInfo);
			if (lists != null) {
				for (RoleDTO role : lists) {
					if (!"增加".equals(role.getFlagType()) && !"减少".equals(role.getFlagType())) {
						role.setFlagType("原有");
					}
					list.add(role);
				}
			}
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("ApplyUserRoleService:querySysUserRole", e);

		}
		return pageInfo;
	}

	@Override
	public PageInfo queryApplyUserRoleChangeByPage(ApplyUserDTO sysUser, PageInfo pageInfo) throws SysException {
		try {
			List<RoleDTO> list = applyUserRoleMapper.queryApplyUserRoleChangeByPage(sysUser, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("ApplyUserRoleService:queryApplyUserRoleChangeByPage", e);

		}
		return pageInfo;
	}

}
