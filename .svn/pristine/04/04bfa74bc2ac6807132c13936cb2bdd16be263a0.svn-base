package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.SysUserPermissionDTO;
import citic.gack.sso.admin.service.SysUserConstraintService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysUserConstraint;
import citic.gack.sso.mapper.SysUserConstraintMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("sysUserConstraintService")
public class SysUserConstraintServiceImpl implements SysUserConstraintService {
	@Autowired
	private SysUserConstraintMapper sysUserConstraintMapper;

	@Override
	public SysUserConstraint insert(SysUserConstraint entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setConstraintId(UUIDGenerator.getUUID());
			sysUserConstraintMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("SysUserConstraintService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysUserConstraint entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = sysUserConstraintMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("SysUserOperConstraintService:update", e);

		}

	}

	@Override
	public void delete(SysUserConstraint entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			sysUserConstraintMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("SysUserOperConstraintService:delete", e);

		}

	}

	@Override
	public List<SysUserConstraint> queryList(SysUserConstraint entity) throws SysException {
		List<SysUserConstraint> list = null;
		try {
			list = sysUserConstraintMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("SysUserOperConstraintService:queryList", e);

		}
		return list;
	}

	@Override
	public SysUserConstraint queryBean(SysUserConstraint entity) throws SysException {
		try {
			entity = sysUserConstraintMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("SysUserOperConstraintService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysUserConstraint sysUserConstraint, PageInfo pageInfo) throws SysException {
		try {
			List<SysUserConstraint> list = sysUserConstraintMapper.queryListByPage(sysUserConstraint, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("SysUserOperConstraintService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysUserConstraint> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysUserConstraint sysUserConstraint : delList) {
					this.delete(sysUserConstraint);
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserOperConstraintService:deleteBatch", e);

		}

	}

	@Override
	public List<SysUserConstraint> queryMenuSysUserConstraint(SysUserPermissionDTO mvo) throws SysException {
		List<SysUserConstraint> list = null;
		try {
			list = sysUserConstraintMapper.queryMenuSysUserConstraint(mvo);
		} catch (Exception e) {
			throw new SysException("SysUserOperConstraintService:queryMenuSysUserConstraint", e);

		}
		return list;
	}

}
