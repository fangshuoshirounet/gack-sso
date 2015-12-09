package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.service.ApplyOperatePermissionService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysApplyOperatePermission;
import citic.gack.sso.mapper.ApplyOperatePermissionMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("applyOperatePermissionService")
public class ApplyOperatePermissionServiceImpl implements ApplyOperatePermissionService {
	@Autowired
	private ApplyOperatePermissionMapper applyOperatePermissionMapper;

	@Override
	public SysApplyOperatePermission insert(SysApplyOperatePermission entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setId(UUIDGenerator.getUUID());
			applyOperatePermissionMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("OperatePermissionService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysApplyOperatePermission entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = applyOperatePermissionMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("ApplyOperatePermissionService:update", e);

		}

	}

	@Override
	public void delete(SysApplyOperatePermission entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			applyOperatePermissionMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("ApplyOperatePermissionService:delete", e);

		}

	}

	@Override
	public List<SysApplyOperatePermission> queryList(SysApplyOperatePermission entity) throws SysException {
		List<SysApplyOperatePermission> list = null;
		try {
			list = applyOperatePermissionMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("ApplyOperatePermissionService:queryList", e);

		}
		return list;
	}

	@Override
	public SysApplyOperatePermission queryBean(SysApplyOperatePermission entity) throws SysException {
		try {
			entity = applyOperatePermissionMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("ApplyOperatePermissionService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysApplyOperatePermission operatePermission, PageInfo pageInfo) throws SysException {
		try {
			List<SysApplyOperatePermission> list = applyOperatePermissionMapper.queryListByPage(operatePermission, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("ApplyOperatePermissionService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysApplyOperatePermission> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysApplyOperatePermission operatePermission : delList) {
					this.delete(operatePermission);
				}
			}
		} catch (Exception e) {
			throw new SysException("ApplyOperatePermissionService:deleteBatch", e);

		}

	}
}
