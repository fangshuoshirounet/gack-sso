package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.ShortcutMenuDTO;
import citic.gack.sso.admin.service.ShortcutMenuService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysShortcutMenu;
import citic.gack.sso.mapper.ShortcutMenuMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("shortcutMenuService")
public class ShortcutMenuServiceImpl implements ShortcutMenuService {
	@Autowired
	private ShortcutMenuMapper shortcutMenuMapper;

	@Override
	public SysShortcutMenu insert(SysShortcutMenu entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setShortcutMenuId(UUIDGenerator.getUUID());
			shortcutMenuMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("ShortcutMenuService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysShortcutMenu entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = shortcutMenuMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("ShortcutMenuService:update", e);

		}

	}

	@Override
	public void update(List<SysShortcutMenu> menus) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			for (SysShortcutMenu menu : menus) {
				menu.setOperateDate(nowDate);
				if (StringUtils.equals(menu.getSts(), "A")) {
					menu.setCreateDate(DateTools.getSysDate19());
					menu.setShortcutMenuId(UUIDGenerator.getUUID());
					insert(menu);
				} else {
					delete(menu);
				}
			}
		} catch (Exception e) {
			throw new SysException("ShortcutMenuService:update", e);

		}

	}

	@Override
	public void delete(SysShortcutMenu entity) throws SysException {
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			shortcutMenuMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("ShortcutMenuService:delete", e);

		}

	}

	@Override
	public List<SysShortcutMenu> queryList(SysShortcutMenu entity) throws SysException {
		List<SysShortcutMenu> list = null;
		try {
			list = shortcutMenuMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("ShortcutMenuService:queryList", e);

		}
		return list;
	}

	@Override
	public SysShortcutMenu queryBean(SysShortcutMenu entity) throws SysException {
		try {
			entity = shortcutMenuMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("ShortcutMenuService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysShortcutMenu shortcutMenu, PageInfo pageInfo) throws SysException {
		try {
			List<SysShortcutMenu> list = shortcutMenuMapper.queryListByPage(shortcutMenu, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("ShortcutMenuService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysShortcutMenu> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysShortcutMenu shortcutMenu : delList) {
					this.delete(shortcutMenu);
				}
			}
		} catch (Exception e) {
			throw new SysException("ShortcutMenuService:deleteBatch", e);

		}

	}

	@Override
	public List<ShortcutMenuDTO> queryList(ShortcutMenuDTO entity) throws SysException {
		List<ShortcutMenuDTO> list = null;
		try {
			list = shortcutMenuMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("ShortcutMenuService:queryList", e);

		}
		return list;
	}
}
