package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.OperationDTO;
import citic.gack.sso.admin.service.OperationService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.mapper.OperationMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("operationService")
public class OperationServiceImpl implements OperationService {
	@Autowired
	private OperationMapper operationMapper;

	@Override
	public OperationDTO insert(OperationDTO entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setOperationId(UUIDGenerator.getUUID());
			operationMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("OperationService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(OperationDTO entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = operationMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("OperationService:update", e);

		}

	}

	@Override
	public void delete(OperationDTO entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			operationMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("OperationService:delete", e);

		}

	}

	@Override
	public List<OperationDTO> queryList(OperationDTO entity) throws SysException {
		List<OperationDTO> list = null;
		try {
			list = operationMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("OperationService:queryList", e);

		}
		return list;
	}

	@Override
	public OperationDTO queryBean(OperationDTO entity) throws SysException {
		try {
			entity = operationMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("OperationService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(OperationDTO entity, PageInfo pageInfo) throws SysException {
		try {
			List<OperationDTO> list = operationMapper.queryListByPage(entity, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("OperationService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void batchDelete(List<OperationDTO> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (OperationDTO operation : delList) {
					this.delete(operation);
				}
			}
		} catch (Exception e) {
			throw new SysException("OperationService:batchDelete", e);

		}

	}
}
