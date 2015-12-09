package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.service.PermissionService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysPermission;
import citic.gack.sso.mapper.PermissionMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	public SysPermission insert(SysPermission entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setPermissionId(UUIDGenerator.getUUID());
			permissionMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("PermissionService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysPermission entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = permissionMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("PermissionService:update", e);

		}

	}

	@Override
	public void delete(SysPermission entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			permissionMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("PermissionService:delete", e);

		}

	}

	@Override
	public List<SysPermission> queryList(SysPermission entity) throws SysException {
		List<SysPermission> list = null;
		try {
			list = permissionMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("PermissionService:queryList", e);

		}
		return list;
	}

	@Override
	public SysPermission queryBean(SysPermission entity) throws SysException {
		try {
			entity = permissionMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("PermissionService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysPermission permission, PageInfo pageInfo) throws SysException {
		try {
			List<SysPermission> list = permissionMapper.queryListByPage(permission, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("PermissionService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysPermission> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysPermission permission : delList) {
					this.delete(permission);
				}
			}
		} catch (Exception e) {
			throw new SysException("PermissionService:deleteBatch", e);

		}

	}
}
