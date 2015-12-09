package citic.gack.sso.mapper;

import java.util.List;

import citic.gack.sso.admin.dto.SysUserDTO;
import citic.gack.sso.base.BaseMapper;

public interface SysUserMapper extends BaseMapper<SysUserDTO> {
	public SysUserDTO doGetAuthenticationInfo(String username);

	public SysUserDTO searchSysUserByName(String username);

	public List<SysUserDTO> sarchSysUserByOrgId(String orgId);

	public List<SysUserDTO> selectUsersByOrgIds(String[] orgIds);

	/**
	 * <br>
	 * 描 述：根据申请修改用户信息 <br>
	 * 作 者：wangjincheng <br>
	 * 历 史: (版本)
	 * 
	 * @param sysuser
	 * @return
	 */
	public int updateByApply(SysUserDTO sysuser);

	/**
	 * <br>
	 * 描 述：修改密码 <br>
	 * 作 者：wangjincheng <br>
	 * 版 本：V1.0 <br>
	 * 时 间：2015年10月23日 上午11:50:34
	 * 
	 * @param sysuser
	 * @return
	 */
	public int updatePwdById(SysUserDTO sysuser);

}