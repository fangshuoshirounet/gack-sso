package citic.gack.sso.admin.service;

import java.util.List;

import citic.gack.sso.admin.dto.MenuDTO;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysMenu;


public interface MenuService  {
    /**
     * 插入
     *
     * @param entity 实体对象
     * @return 实体对象
     * @throws SysException 系统异常
     * @throws AppException 应用异常
     */
    public SysMenu insert(SysMenu entity) throws SysException;
    

    /**
     * 修改
     *
     * @param entity 实体对象
     * @return 更新记录数
     * @throws SysException 系统异常
     * @throws AppException 应用异常
     */
    public int update(SysMenu entity) throws SysException;

    /**
     * 删除
     *
     * @param entity 实体对象
     * @return 删除记录数
     * @throws SysException 系统异常
     * @throws AppException 应用异常
     */
    public void delete(SysMenu entity) throws SysException;

    /**
     * 查询集合
     *
     * @param entity 实体对象
     * @return 实体对象列表
     * @throws SysException 系统异常
     * @throws AppException 应用异常
     */
    public List<SysMenu> queryList(SysMenu entity) throws SysException;

    /**
     * 查询对象
     *
     * @param entity 实体对象
     * @return 实体对象
     * @throws SysException 系统异常
     * @throws AppException 应用异常
     */
    public SysMenu queryBean(SysMenu entity) throws SysException;

    /**
     * 分页查询
     *
     * @param entity  实体对象
     * @param pageInfo 分页对象
     * @return 分页对象
     * @throws SysException 系统异常
     * @throws AppException 应用异常
     */
    public PageInfo queryListByPage(MenuDTO entity, PageInfo pageInfo) throws SysException;

    /**
     * 删除
     *
     * @param delList 实体对象集合
     * @return 删除记录数
     * @throws SysException 系统异常
     * @throws AppException 应用异常
     */
    public void deleteBatch(List<SysMenu> delList) throws SysException;
    
    public MenuDTO insertMenuAndOperation(MenuDTO mvo) throws SysException;
    
    public MenuDTO updateMenuAndOperation(MenuDTO mvo) throws SysException;
    
    public void deleteMenuAndOperation(List<MenuDTO> mvoList) throws SysException;
    public MenuDTO showMenuAndOperation(MenuDTO mvo) throws SysException;
}