package com.business.sys.dao;

import com.business.sys.dto.SysMenuDto;
import com.business.sys.entity.SysMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuDao {


    public List<SysMenuDto> getAuthMenu(SysMenuDto sysMenuDto);

    public List<SysMenu> getAllMenu(SysMenu sysMenu);

    public int insertMenu(SysMenu sysMenu);

    public int updateMenu(SysMenu sysMenu);

    public List<SysMenuDto> getMenuAndBindRole(SysMenuDto sysMenuDto);
}
