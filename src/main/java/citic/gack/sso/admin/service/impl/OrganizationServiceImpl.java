package citic.gack.sso.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.service.OrganizationService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysOrganization;
import citic.gack.sso.mapper.OrganizationMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;
import net.sf.json.JSONArray;

/**
 * 组织机构(OrganizationService)
 * 
 * @author wangjincheng
 * 
 * @data 2013-3-6下午1:56:51
 */

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {
	@Autowired
	private OrganizationMapper organizationMapper;

	@Override
	public SysOrganization insert(SysOrganization entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setOrgId(UUIDGenerator.getUUID());
			organizationMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("OrganizationService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysOrganization entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			entity.setSts(ConstantsUtils.STS_A);
			// 自己不能设置上一级为自己
			if (entity.getOrgId().equals(entity.getParentId())) {
				entity.setParentId(null);
			}
			flag = organizationMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("OrganizationService:update", e);

		}

	}

	@Override
	public void delete(SysOrganization entity) throws SysException {
		try {
			organizationMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("OrganizationService:delete", e);

		}

	}

	@Override
	public List<SysOrganization> queryList(SysOrganization entity) throws SysException {
		List<SysOrganization> list = null;
		try {
			list = organizationMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("OrganizationService:queryList", e);

		}
		return list;
	}

	@Override
	public SysOrganization queryBean(SysOrganization entity) throws SysException {
		try {
			entity = organizationMapper.queryById(entity);

		} catch (Exception e) {
			throw new SysException("OrganizationService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysOrganization entity, PageInfo pageInfo) throws SysException {
		try {
			List<SysOrganization> list = organizationMapper.queryListByPage(entity, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("OrganizationService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysOrganization> delList) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			if (delList != null && delList.size() > 0) {
				List<SysOrganization> orgList = null;
				for (SysOrganization organization : delList) {
					orgList = this.queryList(organization);
					if (orgList == null || orgList.size() == 0) {
						organization.setOperateDate(nowDate);
						organization.setSts(ConstantsUtils.STS_P);
						this.delete(organization);
					}

				}
			}
		} catch (Exception e) {
			throw new SysException("OrganizationService:deleteBatch", e);

		}

	}

	@Override
	public String treejson(boolean isChild, String id) throws SysException {
		String rs = null;
		try {
			SysOrganization model = new SysOrganization();
			if (!isChild && StringUtils.isNotBlank(id)) {
				model.setOrgId(id);
			}
			List<SysOrganization> list;
			list = this.queryList(model);
			List<SysOrganization> rootAreas = new ArrayList<SysOrganization>();
			if (StringUtils.isNotBlank(id)) {
				model.setOrgId(id);
			}
			rootAreas.addAll(list);
			// 构造所有权限
			List<Object> jsonList = new ArrayList<Object>();
			buildJsonList(jsonList, rootAreas, 1, isChild, model);
			JSONArray jo = JSONArray.fromObject(jsonList);
			rs = jo.toString();
		} catch (Exception e) {
			throw new SysException("OrganizationService:treejson", e);

		}

		return rs;
	}

	private void buildJsonList(List<Object> jsonList, List<SysOrganization> datas, int level, //
			boolean isChild, SysOrganization model) throws SysException {
		try {
			if (datas == null)
				return;

			for (SysOrganization obj : datas) {
				String id = obj.getOrgId();
				if (isChild && id.equals(model.getOrgId())) {
					continue;
				}
				String text = obj.getName();
				Map<String, Object> amap = new HashMap<String, Object>();
				List<SysOrganization> children = this.queryList(obj);
				if (children != null && children.size() > 0) {
					amap.put("state", "closed");
					amap.put("attributes", "true");
					if (isChild) {
						buildJsonChildList(amap, children, level++, isChild, model);
					}
				} else {
					amap.put("state", "open");
					amap.put("attributes", "false");
				}
				amap.put("text", text);
				amap.put("id", String.valueOf(id));
				jsonList.add(amap);
			}
		} catch (Exception e) {
			throw new SysException("OrganizationService:buildJsonList", e);

		}

	}

	private void buildJsonChildList(Map<String, Object> parentMap, List<SysOrganization> datas, //
			int level, boolean isChild, SysOrganization model) throws SysException {
		try {
			if (datas == null)
				return;
			List<Object> list = new ArrayList<Object>();
			for (SysOrganization obj : datas) {
				String id = obj.getOrgId();
				if (isChild && id.equals(model.getOrgId())) {
					parentMap.put("state", "open");
					continue;
				}
				String text = obj.getName();
				Map<String, Object> amap = new HashMap<String, Object>();
				List<SysOrganization> children = this.queryList(obj);
				if (children != null && children.size() > 0) {
					amap.put("state", "closed");
					amap.put("attributes", "true");
					if (isChild) {
						buildJsonChildList(amap, children, level + 1, isChild, model);
					}
				} else {
					amap.put("state", "open");
					amap.put("attributes", "false");
				}
				amap.put("text", text);
				amap.put("id", String.valueOf(id));
				list.add(amap);
			}

			parentMap.put("children", list);
		} catch (Exception e) {
			throw new SysException("OrganizationService:buildJsonChildList", e);

		}

	}

	@Override
	public List<SysOrganization> searchOrgByAreaId(String areaId) throws SysException {
		List<SysOrganization> list = null;
		try {
			list = organizationMapper.searchOrgByAreaId(areaId);
		} catch (Exception e) {
			throw new SysException("OrganizationService:searchOrgByAreaId", e);

		}
		return list;
	}
}
