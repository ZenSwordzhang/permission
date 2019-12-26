package com.zsx.service;

import com.zsx.core.dao.repository.admin.PermissionPageRepository;
import com.zsx.core.entity.admin.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by Nicky on 2017/12/3.
 */
@Service
public class PermissionPageService {
    @Autowired
    PermissionPageRepository permissionPageRepository;

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
     * 获取所有的权限信息并分页显示
     *
     * @param pageNo   当前页面数
     * @param pageSize 每一页面的页数
     * @return
     */
    public Page<Permission> findAll(int pageNo, int pageSize, Sort.Direction dir, String str) {
        PageRequest pageRequest = buildPageRequest(pageNo, pageSize, dir, str);
        Page<Permission> permissions = permissionPageRepository.findAll(pageRequest);
        return permissions;
    }

}
