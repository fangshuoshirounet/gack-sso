package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.service.EnumerateService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysEnumerate;
import citic.gack.sso.mapper.EnumerateMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("statusService")
public class EnumerateServiceImpl implements EnumerateService {
	@Autowired
	private EnumerateMapper enumerateMapper;

	/**
	 * 插入
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 * 
	 */
	@Override
	public SysEnumerate insert(SysEnumerate entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			enumerateMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("EnumerateService:insert", e);

		}
		return entity;
	}

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 * 
	 */
	@Override
	public int update(SysEnumerate entity) throws SysException {
		int flag = 0;
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setOperateDate(nowDate);
			flag = enumerateMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("EnumerateService:update", e);

		}

	}

	/**
	 * 删除
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 * 
	 */
	@Override
	public void delete(SysEnumerate entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setOperateDate(nowDate);
			enumerateMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("EnumerateService:delete", e);

		}

	}

	/**
	 * 查询 集合
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 * 
	 */
	@Override
	public List<SysEnumerate> queryList(SysEnumerate entity) throws SysException {
		List<SysEnumerate> list = null;
		try {
			list = enumerateMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("EnumerateService:queryList", e);

		}
		return list;
	}

	/**
	 * 查询对象
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 * 
	 */
	@Override
	public SysEnumerate queryBean(SysEnumerate entity) throws SysException {
		try {
			entity = enumerateMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("EnumerateService:queryBean", e);

		}
		return entity;
	}

	/**
	 * 分页查询
	 * 
	 * @param status
	 * @param pageInfo
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	@Override
	public PageInfo queryListByPage(SysEnumerate status, PageInfo pageInfo) throws SysException {
		try {
			List<SysEnumerate> list = enumerateMapper.queryListByPage(status, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("EnumerateService:queryListByPage", e);

		}

		return pageInfo;
	}

	/**
	 * 删除
	 * 
	 * @param delList
	 * @return
	 * @throws SysException
	 * 
	 */
	@Override
	public void batchDelete(List<SysEnumerate> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysEnumerate status : delList) {
					this.delete(status);
				}
			}
		} catch (Exception e) {
			throw new SysException("EnumerateService:batchDelete", e);

		}

	}

}