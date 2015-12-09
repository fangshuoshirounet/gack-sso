package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.service.OperatePermissionService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysOperatePermission;
import citic.gack.sso.mapper.OperatePermissionMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("operatePermissionService")
public class OperatePermissionServiceImpl implements OperatePermissionService {
	@Autowired
	private OperatePermissionMapper operatePermissionMapper;

	@Override
	public SysOperatePermission insert(SysOperatePermission entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setOperatePermissionId(UUIDGenerator.getUUID());
			operatePermissionMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("OperatePermissionService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysOperatePermission entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = operatePermissionMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("OperatePermissionService:update", e);

		}

	}

	@Override
	public void delete(SysOperatePermission entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			operatePermissionMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("OperatePermissionService:delete", e);

		}

	}

	@Override
	public List<SysOperatePermission> queryList(SysOperatePermission entity) throws SysException {
		List<SysOperatePermission> list = null;
		try {
			list = operatePermissionMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("OperatePermissionService:queryList", e);

		}
		return list;
	}

	@Override
	public SysOperatePermission queryBean(SysOperatePermission entity) throws SysException {
		try {
			entity = operatePermissionMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("OperatePermissionService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysOperatePermission operatePermission, PageInfo pageInfo) throws SysException {
		try {
			List<SysOperatePermission> list = operatePermissionMapper.queryListByPage(operatePermission, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("OperatePermissionService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysOperatePermission> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysOperatePermission operatePermission : delList) {
					this.delete(operatePermission);
				}
			}
		} catch (Exception e) {
			throw new SysException("OperatePermissionService:deleteBatch", e);

		}

	}
}
