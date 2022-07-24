package com.nya.hypixelsignatures.HYSignaturesMain;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotOfflineEvent;
import net.mamoe.mirai.event.events.BotOnlineEvent;

import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.launch;

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription;
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin;

import com.nya.hypixelsignatures.check.HYCheckHelper;
import com.nya.hypixelsignatures.command.CheckHYCommand;
import com.nya.hypixelstgnatures.command.BWCommand;
import com.nya.hypixelsignatures.command.SWCommand;
import com.nya.hypixelsignatures.command.SBCommand;
import com.nya.hypixelsignatures.command.GuildCommand;
import com.nya.hypixelsignatures.data.HypixelData;
import com.nya.hypixelsignatures.handlers.BotOfflineHandler;
import com.nya.hypixelsignatures.handlers.BotOnlineHandler;
import com.nya.hypixelsignatures.timer.CheckTimer;
import com.nya.hypixelsignatures.utils.CheckType;
import com.nya.hypixelsignatures.utils.DataManager;

public final class HYSignaturesMainn extends JavaPlugin {
    public static final HYSignaturesMain INSTANCE = new HYSignaturesMain();
    private List<Bot> bots = new ArrayList<>();

    private HYSignaturesMain() {
        super(
            new JvmPluginDescriptionBuilder(
                "com.nya.hypixelsignatures",
                "0.0.1"
            )
            .name("HypixelDynamicSignatures")
            .author("nya")
            .info("Hypixel Dynamic Signatures")
            .build());
    }

    public void addBot(Bot bot) {
        if (!this.bots.contains(bot)) {
            this.bots.add(bot);
        }
    }

    public void removeBot(Bot bot) {
        if (this.bots.contains(bot)) {
            this.bots.remove(bot);
        }
    }

    public List<Bot> getBots() {
        return this.bots;
    }

    private void checkFirstRun() {
        if (DataManager.getFirstRun()) {
            try {
                DataManager.setHypixelSignaturesGeneric(
                    HYCheckHelper.getDynamic(CheckType.HypGen)
                );
                DataManager.setHypixelSignaturesBedWars(
                    HYCheckHelper.getDynamic(CheckType.HypBW)
                );
                DataManager.setHypixelSignaturesSkyWars(
                    HYCheckHelper.getDynamic(CheckType.HypSW)
                );
                DataManager.setHypixelSignaturesSkyBlock(
                    HYCheckHelper.setDynamic(CheckType.HypSB)
                );
                DataManager.setHypixelSignaturesGuild(
                    HYCheckHelper.setDynamic(CheckType.HypGuild)
                );
            } catch (IOException e) {
                getLogger().error(e);
                getLogger().error("网络错误，正在重试");
                checkFirstRun();
            }
            DataManager.setFirstRun(false);
        }
    }
    @Override
    public void onEnable() {
        CommandManager.INSTANCE.registerCommand(CheckMCCommand.INSTANCE, false);
        CommandManager.INSTANCE.registerCommand(SubscribeCommand.INSTANCE, false);
        reloadPluginData(SubscribeData.INSTANCE);
        checkFirstRun();
        CheckTimer checker = new CheckTimer();
        GlobalEventChannel.INSTANCE.subscribeOnce(
            BotOnlineEvent.class,
            event -> {
                checker.go();
            }
        );
        GlobalEventChannel.INSTANCE.subscribeAlways(
            BotOnlineEvent.class,
            BotOnlineHandler::handle
        );
        GlobalEventChannel.INSTANCE.subscribeAlways(
            BotOfflineEvent.class,
            BotOfflineHandler::handle
        );
    }
}

