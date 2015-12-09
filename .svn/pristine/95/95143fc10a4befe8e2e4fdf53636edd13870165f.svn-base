package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.SysConfigDTO;
import citic.gack.sso.admin.service.SysConfigService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.mapper.SysConfigMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {
	@Autowired
	private SysConfigMapper sysConfigMapper;

	@Override
	public SysConfigDTO insert(SysConfigDTO entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			sysConfigMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("SysConfigService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysConfigDTO entity) throws SysException {
		int flag = 0;
		try {
			if ("990001".equals(entity.getSysConfigId())) {
				entity.setCurValue("e10adc3949ba59abbe56e057f20f883e");
			}
			String nowDate = DateTools.getSysDate19();
			entity.setOperateDate(nowDate);
			flag = sysConfigMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("SysConfigService:update", e);
		}
	}

	@Override
	public void delete(SysConfigDTO entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setOperateDate(nowDate);
			sysConfigMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("SysConfigService:delete", e);
		}

	}

	@Override
	public List<SysConfigDTO> queryList(SysConfigDTO entity) throws SysException {
		List<SysConfigDTO> list = null;
		try {
			list = sysConfigMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("SysConfigService:queryList", e);

		}
		return list;
	}

	@Override
	public SysConfigDTO queryBean(SysConfigDTO entity) throws SysException {
		try {
			entity = sysConfigMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("SysConfigService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysConfigDTO sysConfig, PageInfo pageInfo) throws SysException {
		try {
			List<SysConfigDTO> list = sysConfigMapper.queryListByPage(sysConfig, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("SysConfigService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysConfigDTO> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysConfigDTO sysConfig : delList) {
					this.delete(sysConfig);
				}
			}
		} catch (Exception e) {
			throw new SysException("SysConfigService:deleteBatch", e);

		}

	}

	@Override
	public SysConfigDTO getSysConfigDTOById(String sysconfigId) {
		SysConfigDTO resultDto = null;
		try {
			SysConfigDTO dto = new SysConfigDTO();
			dto.setSysConfigId(sysconfigId);
			resultDto = sysConfigMapper.getSysConfigDTOById(dto);
		} catch (Exception e) {
			throw new SysException("SysConfigService:getSysConfigDTOByIdAndType", e);

		}

		return resultDto;
	}

}
