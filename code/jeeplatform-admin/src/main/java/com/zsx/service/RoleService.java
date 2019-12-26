package com.zsx.service;

import com.zsx.core.dao.repository.admin.RoleRepository;
import com.zsx.core.entity.admin.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nicky on 2017/12/2.
 */
@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    /**
     *
     * @param ids
     * @return
     */
    //@RedisCache
    public List<Role> findAll(List<Integer> ids){
        return roleRepository.findAllById(ids);
    }
}
