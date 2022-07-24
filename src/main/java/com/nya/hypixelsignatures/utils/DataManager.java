package com.nya.hypixelsignatures.utils;

import java.util.List;
import java.util.Map;

import com.nya.hypixelsignatures.data.HypixelData;

import net.mamoe.mirai.console.data.Value;

public class DataManager {
    public static Map<Long, List<CheckType>> getGroups() {
        Value<Map<Long, List<CheckType>>> plugindata = HypixelData.INSTANCE.groups;
        return plugindata.get();
    }
    public static void setGroups(Map<Long, List<CheckType>> data) {
        Value<Map<Long, List<CheckType>>> plugindata = HypixelData.INSTANCE.groups;
        plugindata.set(data);
    }
    public static Boolean getFirstRun() {
        Value<Boolean> plugindata = SubscribeData.INSTANCE.firstRun;
        return plugindata.get();
    }
    public static void setFirstRun(Boolean data) {
        Value<Boolean> plugindata = SubscribeData.INSTANCE.firstRun;
        plugindata.set(data);
    }
}