package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.service.ApplyPermissionService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysApplyPermission;
import citic.gack.sso.mapper.ApplyPermissionMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("applyPermissionService")
public class ApplyPermissionServiceImpl implements ApplyPermissionService {
	@Autowired
	private ApplyPermissionMapper applyPermissionMapper;

	@Override
	public SysApplyPermission insert(SysApplyPermission entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setId(UUIDGenerator.getUUID());
			applyPermissionMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("ApplyPermissionService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysApplyPermission entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = applyPermissionMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("ApplyPermissionService:update", e);

		}

	}

	@Override
	public void delete(SysApplyPermission entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			applyPermissionMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("ApplyPermissionService:delete", e);

		}

	}

	@Override
	public List<SysApplyPermission> queryList(SysApplyPermission entity) throws SysException {
		List<SysApplyPermission> list = null;
		try {
			list = applyPermissionMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("ApplyPermissionService:queryList", e);

		}
		return list;
	}

	@Override
	public SysApplyPermission queryBean(SysApplyPermission entity) throws SysException {
		try {
			entity = applyPermissionMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("ApplyPermissionService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysApplyPermission permission, PageInfo pageInfo) throws SysException {
		try {
			List<SysApplyPermission> list = applyPermissionMapper.queryListByPage(permission, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("ApplyPermissionService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysApplyPermission> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysApplyPermission permission : delList) {
					this.delete(permission);
				}
			}
		} catch (Exception e) {
			throw new SysException("ApplyPermissionService:deleteBatch", e);

		}

	}
}
