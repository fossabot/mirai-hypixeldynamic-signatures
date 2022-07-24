package com.nya.hypixelsignatures.handlers;

import com.nya.hypixelsignatures.HYCheckMain;

import net.mamoe.mirai.event.events.BotOfflineEvent;

public class BotOfflineHandler {
    public static void handle(BotOfflineEvent event) {
        MCUpdateCheckMain.INSTANCE.removeBot(event.getBot());
        MCUpdateCheckMain.INSTANCE.getLogger()
            .info(String.format("已从Bot列表移除Bot %id", event.getBot().getId()));
    }
}