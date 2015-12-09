package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.service.SysUserOperPrivilegeService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysUserOperPrivilege;
import citic.gack.sso.mapper.SysUserOperPrivilegeMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("sysUserOperPrivilegeService")
public class SysUserOperPrivilegeServiceImpl implements SysUserOperPrivilegeService {
	@Autowired
	private SysUserOperPrivilegeMapper sysUserOperPrivilegeMapper;

	@Override
	public SysUserOperPrivilege insert(SysUserOperPrivilege entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setOperPrivilegeId(UUIDGenerator.getUUID());
			sysUserOperPrivilegeMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("SysUserOperPrivilegeService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysUserOperPrivilege entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = sysUserOperPrivilegeMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("SysUserOperPrivilegeService:update", e);

		}

	}

	@Override
	public void delete(SysUserOperPrivilege entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			sysUserOperPrivilegeMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("SysUserOperPrivilegeService:delete", e);

		}

	}

	@Override
	public List<SysUserOperPrivilege> queryList(SysUserOperPrivilege entity) throws SysException {
		List<SysUserOperPrivilege> list = null;
		try {
			list = sysUserOperPrivilegeMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("SysUserOperPrivilegeService:queryList", e);

		}
		return list;
	}

	@Override
	public SysUserOperPrivilege queryBean(SysUserOperPrivilege entity) throws SysException {
		try {
			entity = sysUserOperPrivilegeMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("SysUserOperPrivilegeService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysUserOperPrivilege sysUserOperPrivilege, PageInfo pageInfo) throws SysException {
		try {
			List<SysUserOperPrivilege> list = sysUserOperPrivilegeMapper.queryListByPage(sysUserOperPrivilege, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("SysUserOperPrivilegeService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysUserOperPrivilege> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysUserOperPrivilege sysUserOperPrivilege : delList) {
					this.delete(sysUserOperPrivilege);
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserOperPrivilegeService:deleteBatch", e);

		}

	}
}
