package citic.gack.sso.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import citic.gack.sso.admin.dto.ApplyUserDTO;
import citic.gack.sso.admin.dto.RoleDTO;
import citic.gack.sso.base.BaseMapper;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysApplyUserRole;

public interface ApplyUserRoleMapper extends BaseMapper<SysApplyUserRole> {

	/**
	 * 查询系统用户拥有的角色
	 * 
	 * @param page
	 *            分页
	 * @param ApplyUserDTO
	 *            参数
	 * @return 角色列表
	 * @throws SysException
	 *             系统异常
	 */

	public List<RoleDTO> queryApplyUserRoleByPage(@Param("condition") ApplyUserDTO condition, @Param("page") PageInfo page);

	/**
	 * <br>
	 * 描 述：查询系统用户拥有的角色 <br>
	 * 作 者：wangjincheng <br>
	 * 版 本：V1.0 <br>
	 * 时 间：2015年9月30日 上午10:50:47
	 * 
	 * @param applyUserId
	 * @param sysUserId
	 * @return 角色列表
	 */
	public List<RoleDTO> queryApplyUserRole(@Param("applyUserId") String applyUserId, @Param("sysUserId") String sysUserId);

	/**
	 * <br>
	 * 描 述：保存申请角色变更历史 <br>
	 * 作 者：wangjincheng <br>
	 * 版 本：V1.0 <br>
	 * 时 间：2015年9月30日 上午10:50:32
	 * 
	 * @param RoleDTO
	 * @return
	 */
	public int saveApplyUserRoleChange(RoleDTO role);

	/**
	 * <br>
	 * 描 述：查询用户申请历史记录的角色 <br>
	 * 作 者：wangjincheng <br>
	 * 版 本：V1.0 <br>
	 * 时 间：2015年9月30日 上午11:03:31
	 * 
	 * @param condition
	 * @param page
	 * @return
	 */
	public List<RoleDTO> queryApplyUserRoleChangeByPage(@Param("condition") ApplyUserDTO condition, @Param("page") PageInfo page);

}