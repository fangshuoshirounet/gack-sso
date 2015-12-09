package citic.gack.sso.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.service.AreaService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysArea;
import citic.gack.sso.mapper.AreaMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;
import net.sf.json.JSONArray;

/**
 * 系统区域(AreaService)
 * 
 * @author wangjincheng
 * 
 * @data 2013-2-20下午2:04:39
 */
@Service("areaService")
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaMapper areaMapper;

	@Override
	public SysArea insert(SysArea entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setAreaId(UUIDGenerator.getUUID());
			areaMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("AreaService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysArea entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			entity.setSts(ConstantsUtils.STS_A);
			// 自己不能设置上一级为自己
			if (entity.getAreaId().equals(entity.getParentId())) {
				entity.setParentId(null);
			}
			flag = areaMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("AreaService:update", e);

		}

	}

	@Override
	public void delete(SysArea entity) throws SysException {
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			areaMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("AreaService:delete", e);

		}

	}

	@Override
	public List<SysArea> queryList(SysArea entity) throws SysException {
		List<SysArea> list = null;
		try {
			list = areaMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("AreaService:queryList", e);

		}
		return list;
	}

	@Override
	public SysArea queryBean(SysArea entity) throws SysException {
		try {
			entity = areaMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("AreaService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysArea entity, PageInfo pageInfo) throws SysException {
		try {
			List<SysArea> list = areaMapper.queryListByPage(entity, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("AreaService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysArea> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				List<SysArea> areaList = null;
				for (SysArea area : delList) {
					areaList = this.queryList(area);
					if (areaList == null || areaList.size() == 0) {
						area.setOperateDate(DateTools.getSysDate19());
						area.setSts(ConstantsUtils.STS_P);
						this.delete(area);
					}
				}
			}
		} catch (Exception e) {
			throw new SysException("AreaService:deleteBatch", e);

		}

	}

	@Override
	public String treejson(boolean isChild, String id) throws SysException {
		String rs = null;
		try {
			SysArea model = new SysArea();
			if (!isChild && StringUtils.isNotBlank(id)) {
				model.setAreaId(id);
			}
			List<SysArea> list;
			list = this.queryList(model);
			List<SysArea> rootAreas = new ArrayList<SysArea>();
			if (StringUtils.isNotBlank(id)) {
				model.setAreaId(id);
			}
			rootAreas.addAll(list);
			// 构造所有权限
			List<Object> jsonList = new ArrayList<Object>();
			buildJsonList(jsonList, rootAreas, 1, isChild, model);
			JSONArray jo = JSONArray.fromObject(jsonList);
			rs = jo.toString();
		} catch (Exception e) {
			throw new SysException("AreaService:treejson", e);

		}

		return rs;

	}

	private void buildJsonList(List<Object> jsonList, List<SysArea> datas, int level, boolean isChild, SysArea model) throws SysException {
		try {
			if (datas == null)
				return;
			for (SysArea obj : datas) {
				String id = obj.getAreaId();
				if (isChild && id.equals(model.getAreaId())) {
					continue;
				}
				String text = obj.getName();
				Map<String, Object> amap = new HashMap<String, Object>();
				List<SysArea> children = this.queryList(obj);
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
				amap.put("id", id);
				jsonList.add(amap);
			}
		} catch (Exception e) {
			throw new SysException("AreaService:buildJsonList", e);

		}

	}

	private void buildJsonChildList(Map<String, Object> parentMap, List<SysArea> datas, int level, boolean isChild, SysArea model) throws SysException {
		try {
			if (datas == null)
				return;
			List<Object> list = new ArrayList<Object>();
			for (SysArea obj : datas) {
				String id = obj.getAreaId();
				String text = obj.getName();
				if (isChild && id.equals(model.getAreaId())) {
					parentMap.put("state", "open");
					continue;
				}
				Map<String, Object> amap = new HashMap<String, Object>();
				List<SysArea> children = this.queryList(obj);
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
			throw new SysException("AreaService:buildJsonChildList", e);

		}

	}
}
