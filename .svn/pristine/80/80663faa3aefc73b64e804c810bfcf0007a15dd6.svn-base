package citic.gack.sso.admin.service;

import java.util.List;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

import citic.gack.sso.admin.dto.MenuDTO;
import citic.gack.sso.admin.dto.SysUserDTO;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysRole;


public interface SecurityService  {
    /**
     * 用户认证
     * @param username 用户名
     * @return 系统用户
     * @throws SysException 系统异常
     * @throws AppException 应用异常
     */
    public SysUserDTO doGetAuthenticationInfo(String username) throws SysException;

    /**
     * 用户鉴权
     * @param sysUser 系统用户
     * @return 用户鉴权信息
     * @throws SysException 系统异常
     * @throws AppException 应用异常
     */
    public SimpleAuthorizationInfo doGetAuthorizationInfo(SysUserDTO sysUser) throws SysException;
    
    
    
    /**
     * 用户鉴权
     * @param sysUser 系统用户
     * @return 用户鉴权信息
     * @throws SysException 系统异常
     * @throws AppException 应用异常
     */
    public SimpleAuthorizationInfo doGetAuthorizationInfoByRole(SysUserDTO sysUser, SysRole role) throws SysException ;

    /**
     * 查询系统的正常状态的菜单信息，用于系统初始化
     *
     * @return 系统正常状态的菜单信息列表
     * @throws SysException 系统异常
     * @throws AppException 应用异常
     */
    public List<MenuDTO> queryActiveMenu() throws SysException;
}
