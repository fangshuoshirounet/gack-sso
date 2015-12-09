package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.OrgTypeDTO;
import citic.gack.sso.admin.service.OrgTypeService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.mapper.OrgTypeMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("orgTypeService")
public class OrgTypeServiceImpl implements OrgTypeService {
	@Autowired
	private OrgTypeMapper orgTypeMapper;

	@Override
	public OrgTypeDTO insert(OrgTypeDTO orgType) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			orgType.setSts(ConstantsUtils.STS_A);
			orgType.setOperateDate(nowDate);
			orgType.setCreateDate(nowDate);
			orgType.setOrgTypeId(UUIDGenerator.getUUID());
			orgTypeMapper.save(orgType);
		} catch (Exception e) {
			throw new SysException("OrgTypeService:insert", e);

		}
		return orgType;
	}

	@Override
	public void delete(OrgTypeDTO orgType) throws SysException {
		try {
			orgType.setSts(ConstantsUtils.STS_P);
			orgType.setOperateDate(DateTools.getSysDate19());

			orgTypeMapper.deleteById(orgType);
		} catch (Exception e) {
			throw new SysException("OrgTypeService:delete", e);

		}

	}

	@Override
	public int update(OrgTypeDTO orgType) throws SysException {
		int flag = 0;
		try {
			orgType.setOperateDate(DateTools.getSysDate19());
			flag = orgTypeMapper.updateById(orgType);
		} catch (Exception e) {
			throw new SysException("OrgTypeService:update", e);

		}
		return flag;
	}

	@Override
	public List<OrgTypeDTO> queryList(OrgTypeDTO orgType) throws SysException {
		List<OrgTypeDTO> list = null;
		try {
			list = orgTypeMapper.queryList(orgType);
		} catch (Exception e) {
			throw new SysException("OrgTypeService:queryList", e);

		}

		return list;
	}

	@Override
	public PageInfo queryListByPage(OrgTypeDTO orgType, PageInfo pageInfo) throws SysException {
		try {
			List<OrgTypeDTO> list = orgTypeMapper.queryListByPage(orgType, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("OrgTypeService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public OrgTypeDTO queryBean(OrgTypeDTO org) throws SysException {
		try {
			org = orgTypeMapper.queryById(org);
		} catch (Exception e) {
			throw new SysException("OrgTypeService:queryBean", e);

		}

		return org;
	}

	@Override
	public void deleteBatch(List<OrgTypeDTO> delList) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			if (delList != null && delList.size() > 0) {
				for (OrgTypeDTO orgType : delList) {
					orgType.setSts(ConstantsUtils.STS_P);
					orgType.setOperateDate(nowDate);
					this.delete(orgType);
				}
			}
		} catch (Exception e) {
			throw new SysException("OrgTypeService:deleteBatch", e);

		}

	}

}
