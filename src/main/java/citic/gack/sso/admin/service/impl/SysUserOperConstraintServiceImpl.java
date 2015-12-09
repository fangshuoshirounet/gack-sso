package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.service.SysUserOperConstraintService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysUserOperConstraint;
import citic.gack.sso.mapper.SysUserOperConstraintMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("sysUserOperConstraintService")
public class SysUserOperConstraintServiceImpl implements SysUserOperConstraintService {
	@Autowired
	private SysUserOperConstraintMapper sysUserOperConstraintMapper;

	@Override
	public SysUserOperConstraint insert(SysUserOperConstraint entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setOperConstraintId(UUIDGenerator.getUUID());
			sysUserOperConstraintMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("SysUserOperConstraintService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysUserOperConstraint entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = sysUserOperConstraintMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("SysUserOperConstraintService:update", e);

		}

	}

	@Override
	public void delete(SysUserOperConstraint entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			sysUserOperConstraintMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("SysUserOperConstraintService:delete", e);

		}

	}

	@Override
	public List<SysUserOperConstraint> queryList(SysUserOperConstraint entity) throws SysException {
		List<SysUserOperConstraint> list = null;
		try {
			list = sysUserOperConstraintMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("SysUserOperConstraintService:queryList", e);

		}
		return list;
	}

	@Override
	public SysUserOperConstraint queryBean(SysUserOperConstraint entity) throws SysException {
		try {
			entity = sysUserOperConstraintMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("SysUserOperConstraintService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysUserOperConstraint sysUserOperConstraint, PageInfo pageInfo) throws SysException {
		try {
			List<SysUserOperConstraint> list = sysUserOperConstraintMapper.queryListByPage(sysUserOperConstraint, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("SysUserOperConstraintService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysUserOperConstraint> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysUserOperConstraint sysUserOperConstraint : delList) {
					this.delete(sysUserOperConstraint);
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserOperConstraintService:deleteBatch", e);

		}

	}
}
