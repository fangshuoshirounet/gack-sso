package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.RoleDTO;
import citic.gack.sso.admin.service.RoleService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.mapper.RoleMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public RoleDTO insert(RoleDTO entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setRoleId(UUIDGenerator.getUUID());
			roleMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("RoleService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(RoleDTO entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = roleMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("RoleService:update", e);

		}

	}

	@Override
	public void delete(RoleDTO entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			roleMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("RoleService:delete", e);

		}

	}

	@Override
	public List<RoleDTO> queryList(RoleDTO entity) throws SysException {
		List<RoleDTO> list = null;
		try {
			list = roleMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("RoleService:queryList", e);

		}
		return list;
	}

	@Override
	public RoleDTO queryBean(RoleDTO entity) throws SysException {
		try {
			entity = roleMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("RoleService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(RoleDTO role, PageInfo pageInfo) throws SysException {
		try {
			List<RoleDTO> list = roleMapper.queryListByPage(role, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("RoleService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<RoleDTO> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (RoleDTO role : delList) {
					this.delete(role);
				}
			}
		} catch (Exception e) {
			throw new SysException("RoleService:deleteBatch", e);

		}

	}

}
