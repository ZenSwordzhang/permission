package com.zsx.service;


import com.google.common.collect.Lists;
import com.zsx.core.dao.repository.admin.RolePageRepository;
import com.zsx.core.entity.admin.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsx on 2019/12/12.
 */
@Service
public class RolePageService {

    @Autowired
    RolePageRepository roleRepository;

    /**
     * 构建PageRequest对象
     *
     * @param num
     * @param size
     * @param asc
     * @param string
     * @return
     */
    private PageRequest buildPageRequest(int num, int size, Sort.Direction asc,
                                         String string) {
        return PageRequest.of(num - 1, size, asc, string);
    }

    /**
     * 获取所有的菜单信息并分页显示
     *
     * @param pageNo   当前页面数
     * @param pageSize 每一页面的页数
     * @return
     */
    public Page<Role> findAll(int pageNo, int pageSize, Sort.Direction dir, String str) {
        PageRequest pageRequest = buildPageRequest(pageNo, pageSize, dir, str);
        Page<Role> roles = roleRepository.findAll(pageRequest);
        return roles;
    }

    public List<Role> findAllRole() {
        Iterable<Role> roles = roleRepository.findAll();
        List<Role> myList = Lists.newArrayList(roles);
        return myList;
    }

    /**
     * 根据角色id查找角色信息
     *
     * @param roleId
     * @return
     */
    public Role findByRoleId(String roleId) {
        return roleRepository.findById(Integer.parseInt(roleId)).get();
    }

    /**
     * 保存角色信息
     *
     * @param role
     */
    public void doSave(Role role) {
        roleRepository.save(role);
    }


}
