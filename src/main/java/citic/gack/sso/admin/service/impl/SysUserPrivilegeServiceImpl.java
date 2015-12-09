package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.SysUserPermissionDTO;
import citic.gack.sso.admin.service.SysUserPrivilegeService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysUserPrivilege;
import citic.gack.sso.mapper.SysUserPrivilegeMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("sysUserPrivilegeService")
public class SysUserPrivilegeServiceImpl implements SysUserPrivilegeService {
	@Autowired
	private SysUserPrivilegeMapper sysUserPrivilegeMapper;

	@Override
	public SysUserPrivilege insert(SysUserPrivilege entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setPrivilegeId(UUIDGenerator.getUUID());
			sysUserPrivilegeMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("SysUserPrivilegeService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysUserPrivilege entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = sysUserPrivilegeMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("SysUserPrivilegeService:update", e);

		}

	}

	@Override
	public void delete(SysUserPrivilege entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			sysUserPrivilegeMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("SysUserPrivilegeService:delete", e);

		}

	}

	@Override
	public List<SysUserPrivilege> queryList(SysUserPrivilege entity) throws SysException {
		List<SysUserPrivilege> list = null;
		try {
			list = sysUserPrivilegeMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("SysUserPrivilegeService:queryList", e);

		}
		return list;
	}

	@Override
	public SysUserPrivilege queryBean(SysUserPrivilege entity) throws SysException {
		try {
			entity = sysUserPrivilegeMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("SysUserPrivilegeService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysUserPrivilege sysUserPrivilege, PageInfo pageInfo) throws SysException {
		try {
			List<SysUserPrivilege> list = sysUserPrivilegeMapper.queryListByPage(sysUserPrivilege, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("SysUserPrivilegeService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysUserPrivilege> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysUserPrivilege sysUserPrivilege : delList) {
					this.delete(sysUserPrivilege);
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserPrivilegeService:deleteBatch", e);

		}

	}

	@Override
	public List<SysUserPrivilege> queryMenuSysUserPrivilege(SysUserPermissionDTO DTO) throws SysException {
		List<SysUserPrivilege> list = null;
		try {
			list = sysUserPrivilegeMapper.queryMenuSysUserPrivilege(DTO);
		} catch (Exception e) {
			throw new SysException("SysUserPrivilegeService:queryMenuSysUserPrivilege", e);

		}
		return list;
	}
}
