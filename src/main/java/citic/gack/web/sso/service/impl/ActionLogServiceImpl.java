package citic.gack.web.sso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.web.sso.base.PageInfo;
import citic.gack.web.sso.base.UUIDGenerator;
import citic.gack.web.sso.base.exception.SysException;
import citic.gack.web.sso.entity.ActionLog;
import citic.gack.web.sso.mapper.ActionLogMapper;
import citic.gack.web.sso.service.ActionLogService;
import citic.gack.web.sso.utils.ConstantsUtils;
import citic.gack.web.sso.utils.DateTools;

@Service("actionLogService")
public class ActionLogServiceImpl implements ActionLogService {
	@Autowired
	private ActionLogMapper actionLogMapper;

	@Override
	public ActionLog insert(ActionLog entity) throws SysException {
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
	public int update(ActionLog entity) throws SysException {
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
	public void delete(ActionLog entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setOperateDate(nowDate);
			actionLogMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("ActionLogService:delete", e);

		}

	}

	@Override
	public List<ActionLog> queryList(ActionLog entity) throws SysException {
		List<ActionLog> list = null;
		try {
			list = actionLogMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("ActionLogService:queryList", e);

		}
		return list;
	}

	@Override
	public ActionLog queryBean(ActionLog entity) throws SysException {
		try {
			entity = actionLogMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("ActionLogService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(ActionLog entity, PageInfo pageInfo) throws SysException {
		try {
			List<ActionLog> list = actionLogMapper.queryListByPage(entity, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("ActionLogService:queryListByPage", e);

		}

		return pageInfo;
	}

}
