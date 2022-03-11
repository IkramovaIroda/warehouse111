package com.project.warehouse.entity;

import com.project.warehouse.dto.NavbarItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class NavbarList {
    public List<NavbarItem> getList(String parent){
        switch (parent) {
            case "dashboard"->{
                return Arrays.asList(
                        new NavbarItem("Most sale","/dashboard/most-sold"),
                        new NavbarItem("Notifications", "/dashboard/notifications")
                );
            }
        }
        return new ArrayList<>();
    }
}
