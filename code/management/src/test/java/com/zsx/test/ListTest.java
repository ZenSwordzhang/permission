package com.zsx.test;

import com.zsx.entity.Role;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ListTest {

    @Test
    void testRemoveAll() {
        List<String> list1 = Lists.list("a", "b", "c", "d", "e", "f");
        System.out.println(list1);
        List<String> list2 = List.of("f", "g", "h", "i");
        List<String> list3 = List.of("a", "b", "l", "m");
        List<String> list4 = List.of("f", "g", "o", "p");

        List<String> list = Lists.newArrayList();
        list.addAll(list1);
        list.addAll(list2);
        System.out.println(list);

        list.removeAll(list3);
        list.removeAll(list4);
        System.out.println(list);

    }

    @Test
    void testStreamingProgrammingUse() {
        Role role1 = new Role();
        role1.id = 1L;
        role1.bit = 1L;
        role1.name = "zhangsan";
        role1.userIds = List.of(1L, 2L, 3L, 4L);

        Role role2 = new Role();
        role2.id = 2L;
        role2.bit = 2L;
        role2.name = "zhangsan";
        role2.userIds = List.of(3L, 4L, 5L, 6L);

        Role role3 = new Role();
        role3.id = 3L;
        role3.bit = 4L;
        role3.name = "lisi";
        role3.userIds = List.of(7L, 8L, 9L, 10L);

        Role role4 = new Role();
        role4.id = 4L;
        role4.bit = 8L;
        role4.name = "lisi";
        role4.userIds = List.of(11L, 12L, 13L, 14L);

        List<Role> roles = List.of(role1, role2, role3, role4);

        List<Role> oldRoles = Lists.newArrayList();
        oldRoles.add(null);
        oldRoles.addAll(roles);
        List<Role> newRoles = roles.stream().filter(Objects :: nonNull).collect(Collectors.toList());
        System.out.println(newRoles);

        Map<String, List<Role>> collect = newRoles.stream()
                .collect(Collectors.groupingBy(Role :: getName));
        System.out.println(collect);

        Map<String, List<Role>> collect1 = roles.stream().filter(Objects :: nonNull)
                .collect(Collectors.groupingBy(Role :: getName));
        System.out.println(collect1);

        AtomicInteger count = new AtomicInteger();

        roles.stream().filter(Objects :: nonNull)
            .collect(Collectors.groupingBy(Role :: getName))
            .forEach((name, groupRoles) -> {
                    System.out.println("count: " + count.getAndIncrement() + "," + name + ":" + groupRoles);
                    var ref = new Object() {
                        Long permissionBit = 0L;
                    };
                    groupRoles.forEach(role -> {
                        ref.permissionBit = ref.permissionBit | role.bit;
                    });
                    System.out.println("permissionBit:" + ref.permissionBit);
                }
            );


//
//        List<List<Long>> lists = roles.stream().map(role -> role.userIds).collect(Collectors.toList());
//        lists.forEach(System.out::println);
//
//        List<Long> userIds = roles.stream().map(role -> role.userIds).flatMap(Collection :: stream).distinct().collect(Collectors.toList());
//        System.out.println(userIds);
//
//        List<Role> roles1 = null;
//        // NullPointerException
//        Assertions.assertThrows(NullPointerException.class, ()-> roles1.stream());
//
//        List<Long> userIds1 = List.of(1L, 3L, 5L);
//        List<Role> roles2 = roles.stream().filter(role -> userIds1.contains(role.id)).collect(Collectors.toList());
//        System.out.println(roles2);


    }
}
