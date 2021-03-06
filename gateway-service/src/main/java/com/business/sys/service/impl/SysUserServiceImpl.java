package com.business.sys.service.impl;

import com.business.common.rest.RestTableResult;
import com.business.common.rest.ResultCodeMsg;
import com.business.sys.dao.SysUserDao;
import com.business.sys.dto.SysUserDto;
import com.business.sys.entity.SysUser;
import com.business.sys.service.ISysUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    private SysUserDao sysUserDao;

    @Override
    public SysUser info(SysUser sysUser) {
        return sysUserDao.info(sysUser);
    }

    @Override
    public RestTableResult<SysUserDto> getUserList(SysUserDto sysUserDto) {
        List<SysUserDto> userList = sysUserDao.getUserList(sysUserDto);
        return new RestTableResult(ResultCodeMsg.SUCCESS.code(),ResultCodeMsg.SUCCESS.msg(),userList,Long.valueOf(userList.size()));
    }

    @Override
    public void addSingleUser(SysUser sysUser) {
        sysUser.setUuid("2");
        sysUser.setUsername("ls");
        sysUser.setPassword(passwordEncoder.encode("123456"));
        sysUser.setPhone("13000000000");
        sysUser.setName("李四");
        int i = sysUserDao.addSingleUser(sysUser);
        System.out.println(i);
    }

    @Override
    public void selectiveUpdateUser(SysUser sysUser) {
        sysUserDao.selectiveUpdateUser(sysUser);
    }

}
