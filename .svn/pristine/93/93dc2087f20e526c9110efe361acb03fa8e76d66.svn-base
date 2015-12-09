package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.DutyDTO;
import citic.gack.sso.admin.service.DutyService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.mapper.DutyMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("DutyDTOService")
public class DutyServiceImpl implements DutyService {
	@Autowired
	private DutyMapper dutyMapper;

	@Override
	public DutyDTO insert(DutyDTO entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setDutyId(UUIDGenerator.getUUID());
			dutyMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("DutyService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(DutyDTO entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			entity.setSts(ConstantsUtils.STS_A);
			flag = dutyMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("DutyService:update", e);

		}

	}

	@Override
	public void delete(DutyDTO entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			dutyMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("DutyService:delete", e);

		}

	}

	@Override
	public List<DutyDTO> queryList(DutyDTO entity) throws SysException {
		List<DutyDTO> list = null;
		try {
			list = dutyMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("DutyService:queryList", e);

		}
		return list;
	}

	@Override
	public DutyDTO queryBean(DutyDTO entity) throws SysException {
		try {
			entity = dutyMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("DutyService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(DutyDTO position, PageInfo pageInfo) throws SysException {
		try {
			List<DutyDTO> list = dutyMapper.queryListByPage(position, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("DutyService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<DutyDTO> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (DutyDTO position : delList) {
					this.delete(position);
				}
			}
		} catch (Exception e) {
			throw new SysException("DutyService:deleteBatch", e);

		}

	}
}
