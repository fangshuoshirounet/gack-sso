package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.SysUserDTO;
import citic.gack.sso.admin.service.SysUserService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.cache.CacheManager;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.mapper.SysUserMapper;
import citic.gack.sso.mapper.SysUserRoleMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Override
	public SysUserDTO insert(SysUserDTO entity) throws SysException {
		try {// 1.添加系统用户
			CacheManager cacheManager = CacheManager.getInstance();
			if ("Y".equals(entity.getInitPwdFlag())) {
				String password = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.sysconfig", "990001");
				entity.setPassword(password);
			}
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setSysUserId(UUIDGenerator.getUUID());
			entity.setPwdSetTime(nowDate);
			entity.setCreateDate(nowDate);

			sysUserMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("SysUserService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysUserDTO entity) throws SysException {
		int flag = 0;
		try {
			CacheManager cacheManager = CacheManager.getInstance();
			if ("Y".equals(entity.getInitPwdFlag())) {
				String password = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.sysconfig", "990001");
				entity.setPassword(password);
			}
			entity.setOperateDate(DateTools.getSysDate19());
			flag = sysUserMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("SysUserService:update", e);

		}
	}

	@Override
	public void delete(SysUserDTO entity) throws SysException {
		try {
			sysUserMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("SysUserService:delete", e);

		}

	}

	@Override
	public List<SysUserDTO> queryList(SysUserDTO entity) throws SysException {
		List<SysUserDTO> list = null;
		try {
			list = sysUserMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("SysUserService:queryList", e);

		}
		return list;
	}

	@Override
	public SysUserDTO queryBean(SysUserDTO entity) throws SysException {
		try {
			entity = sysUserMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("SysUserService:queryBean", e);

		}
		return entity;
	}

	@Override
	public SysUserDTO searchSysUserByName(String username) throws SysException {
		SysUserDTO sysuser = null;
		try {
			sysuser = sysUserMapper.searchSysUserByName(username);
		} catch (Exception e) {
			throw new SysException("SysUserService:searchSysUserByName", e);

		}
		return sysuser;
	}

	@Override
	public boolean pwdreset(SysUserDTO sysUser) throws SysException {
		boolean result = false;
		try {
			String pwd = sysUser.getPassword();
			String nowDate = DateTools.getSysDate19();
			if (StringUtils.isNotBlank(sysUser.getSysUserId())) {
				sysUser = sysUserMapper.queryById(sysUser);
				sysUser.setOperateDate(nowDate);
				sysUser.setPassword(pwd);
				sysUserMapper.updatePwdById(sysUser);
				result = true;
			}
		} catch (Exception e) {
			throw new SysException("SysUserService:pwdreset", e);

		}
		return result;
	}

	@Override
	public void deleteBatch(List<SysUserDTO> delList) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			for (SysUserDTO user : delList) {
				sysUserRoleMapper.deleteUserRoleByUserId(user.getSysUserId());
				user.setOperateDate(nowDate);
				sysUserMapper.deleteById(user);
			}
		} catch (Exception e) {
			throw new SysException("SysUserService:deleteBatch", e);

		}

	}

	@Override
	public PageInfo queryListByPage(SysUserDTO sysUser, PageInfo pageInfo) throws SysException {
		try {
			List<SysUserDTO> list = sysUserMapper.queryListByPage(sysUser, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("SysUserService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public List<SysUserDTO> sarchSysUserByOrgId(String orgId) throws SysException {
		List<SysUserDTO> list = null;
		try {
			list = sysUserMapper.sarchSysUserByOrgId(orgId);
		} catch (Exception e) {
			throw new SysException("SysUserService:queryList", e);

		}
		return list;
	}

	@Override
	public List<SysUserDTO> selectUsersByOrgIds(String[] orgIds) throws SysException {
		List<SysUserDTO> list = null;
		try {
			list = sysUserMapper.selectUsersByOrgIds(orgIds);
		} catch (Exception e) {
			throw new SysException("SysUserService:selectUsersByOrgIds", e);

		}
		return list;
	}
}
