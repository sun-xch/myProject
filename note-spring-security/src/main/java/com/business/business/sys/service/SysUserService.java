package com.business.business.sys.service;

import com.business.business.sys.entity.SysUser;
import com.business.config.security.MyUserDetails;

import java.util.List;

public interface SysUserService {

    public MyUserDetails selectUser(SysUser sysUser);

    public List<SysUser> selectAllUser(SysUser sysUser);

    public List<String> findUrlByUserName(String userName);

}
