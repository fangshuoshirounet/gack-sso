package citic.gack.sso.mapper;

import citic.gack.sso.base.BaseMapper;
import citic.gack.sso.entity.RegstUser;

public interface RegstUserMapper extends BaseMapper<RegstUser> {

	public RegstUser queryByEmailOrPhoneNum(String value);
}
