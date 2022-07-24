package com.nya.hypixelsignatures.handlers;

import com.nya.hypixelsignatures.MCUpdateCheckMain;

import net.mamoe.mirai.event.events.BotOnlineEvent;

public class BotOnlineHandler {
    public static void handle(BotOnlineEvent event) {
        MCUpdateCheckMain.INSTANCE.addBot(event.getBot());
        MCUpdateCheckMain.INSTANCE.getLogger()
            .info(String.format("已添加Bot %d进入Bot列表", event.getBot().getId()));
    }
}