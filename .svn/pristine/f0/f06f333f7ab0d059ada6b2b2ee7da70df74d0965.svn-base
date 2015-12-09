package citic.gack.sso.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.RoleDTO;
import citic.gack.sso.admin.service.SysUserRoleService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysRole;
import citic.gack.sso.entity.SysUserRole;
import citic.gack.sso.mapper.SysUserRoleMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Override
	public SysUserRole insert(SysUserRole entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setSysUserRoleId(UUIDGenerator.getUUID());
			sysUserRoleMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("SysUserRoleService:insert", e);

		}
		return entity;
	}

	@Override
	public PageInfo querySysUserRole(SysUserRole sysUser, PageInfo pageInfo) throws SysException {
		List<RoleDTO> list = new ArrayList<RoleDTO>();
		try {
			list = sysUserRoleMapper.queryUserRoleByPage(sysUser, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("SysUserRoleService:querySysUserRole", e);

		}
		return pageInfo;
	}

	@Override
	public int update(SysUserRole entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = sysUserRoleMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("SysUserRoleService:update", e);

		}

	}

	@Override
	public void delete(SysUserRole entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			sysUserRoleMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("SysUserRoleService:delete", e);

		}

	}

	@Override
	public List<SysUserRole> queryList(SysUserRole entity) throws SysException {
		List<SysUserRole> list = null;
		try {
			list = sysUserRoleMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("SysUserRoleService:queryList", e);

		}
		return list;
	}

	@Override
	public SysUserRole queryBean(SysUserRole entity) throws SysException {
		try {
			entity = sysUserRoleMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("SysUserRoleService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysUserRole sysUserRole, PageInfo pageInfo) throws SysException {
		try {
			List<SysUserRole> list = sysUserRoleMapper.queryListByPage(sysUserRole, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("SysUserRoleService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysUserRole> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysUserRole sysUserRole : delList) {
					this.delete(sysUserRole);
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserRoleService:deleteBatch", e);

		}

	}

	@Override
	public List<SysRole> querySysUserRole(String sysUserId) throws SysException {
		List<SysRole> list = null;
		try {
			list = sysUserRoleMapper.querySysUserRole(sysUserId);
		} catch (Exception e) {
			throw new SysException("SysUserRoleService:querySysUserRole", e);

		}
		return list;
	}
}
