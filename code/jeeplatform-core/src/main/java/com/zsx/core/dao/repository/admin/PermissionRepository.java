package com.zsx.core.dao.repository.admin;


import com.zsx.core.entity.admin.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nicky on 2017/11/11.
 */
public interface PermissionRepository extends JpaRepository<Permission,Integer> {

}
