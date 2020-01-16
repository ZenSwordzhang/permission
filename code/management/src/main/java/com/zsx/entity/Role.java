package com.zsx.entity;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class Role {

    public Long id;
    public Long bit;
    public String name;

    /** 用户ID */
    public List<Long> userIds;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
