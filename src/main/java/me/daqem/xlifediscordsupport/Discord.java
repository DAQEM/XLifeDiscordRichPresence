package me.daqem.xlifediscordsupport;

import net.arikia.dev.drpc.*;

public class Discord {


    public static long now = XLifeDiscordSupport.gameStarted;

    private static boolean initialized = false;

    public static void initDiscord() {
        if (initialized)
            return;
        DiscordRPC.discordInitialize("738493451137581216", new DiscordEventHandlers(), true);
        initialized = true;
    }


    public static void setPresence() {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.details = "Want to play yourself?";
        presence.state = "DaqEm.com/XLife";
        presence.largeImageKey = "logo_modpack_transparant";
        presence.smallImageKey = "overworld";
        presence.largeImageText = "X Life";
        presence.smallImageText = "by DAQEM";
        presence.startTimestamp = now;
        DiscordRPC.discordUpdatePresence(presence);
    }

    protected static void shutdown() {
        DiscordRPC.discordShutdown();
    }
}
