package com.shch.lz.ssm.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Link at 14:09 on 3/29/18.
 */
@AllArgsConstructor
public enum DataSourceEnum {
    MASTER("masterDataSource", true), SLAVE("slaveDataSource", false),;

    @Setter
    @Getter
    private String name;

    @Setter
    private boolean master;

    public boolean isMaster() {
        return master;
    }

    public String getDefault() {
        String defaultDataSource = "";
        for (DataSourceEnum dataSourceEnum : DataSourceEnum.values()) {
            if (!"".equals(defaultDataSource)) {
                break;
            }
            if (dataSourceEnum.master) {
                defaultDataSource = dataSourceEnum.getName();
            }
        }
        return defaultDataSource;
    }
}
