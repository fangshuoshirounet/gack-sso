package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.service.ActionLogService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysActionLog;
import citic.gack.sso.mapper.SysActionLogMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("actionLogService")
public class ActionLogServiceImpl implements ActionLogService {
	@Autowired
	private SysActionLogMapper actionLogMapper;

	@Override
	public SysActionLog insert(SysActionLog entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setActionTime(nowDate);
			entity.setActionLogId(UUIDGenerator.getUUID());
			actionLogMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("ActionLogService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysActionLog entity) throws SysException {
		int flag = 0;
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setOperateDate(nowDate);
			flag = actionLogMapper.updateById(entity);
		} catch (Exception e) {
			throw new SysException("ActionLogService:update", e);

		}
		return flag;
	}

	@Override
	public void delete(SysActionLog entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setOperateDate(nowDate);
			actionLogMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("ActionLogService:delete", e);

		}

	}

	@Override
	public List<SysActionLog> queryList(SysActionLog entity) throws SysException {
		List<SysActionLog> list = null;
		try {
			list = actionLogMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("ActionLogService:queryList", e);

		}
		return list;
	}

	@Override
	public SysActionLog queryBean(SysActionLog entity) throws SysException {
		try {
			entity = actionLogMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("ActionLogService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysActionLog entity, PageInfo pageInfo) throws SysException {
		try {
			List<SysActionLog> list = actionLogMapper.queryListByPage(entity, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("ActionLogService:queryListByPage", e);

		}

		return pageInfo;
	}

}
