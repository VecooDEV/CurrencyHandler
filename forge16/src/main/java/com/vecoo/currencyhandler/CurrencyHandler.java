package com.vecoo.currencyhandler;

import com.vecoo.currencyhandler.command.CurrencyHandlerCommand;
import com.vecoo.currencyhandler.config.LocaleConfig;
import com.vecoo.currencyhandler.config.ServerConfig;
import com.vecoo.currencyhandler.listener.CurrencyHandlerListener;
import com.vecoo.currencyhandler.storage.player.PlayerProvider;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CurrencyHandler.MOD_ID)
public class CurrencyHandler {
    public static final String MOD_ID = "currencyhandler";
    private static final Logger LOGGER = LogManager.getLogger("CurrencyHandler");

    private static CurrencyHandler instance;

    private ServerConfig config;
    private LocaleConfig locale;

    private PlayerProvider playerProvider;

    private MinecraftServer server;

    public CurrencyHandler() {
        instance = this;

        this.loadConfig();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CurrencyHandlerListener());
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CurrencyHandlerCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        this.server = event.getServer();
        this.loadStorage();
    }

    public void loadConfig() {
        try {
            this.config = new ServerConfig();
            this.config.init();
            this.locale = new LocaleConfig();
            this.locale.init();
        } catch (Exception e) {
            LOGGER.error("[CurrencyHandler] Error load config.");
        }
    }

    public void loadStorage() {
        try {
            this.playerProvider = new PlayerProvider("/%directory%/storage/CurrencyHandler/players/", "/config/CurrencyHandler/storage/players/", this.server);
            this.playerProvider.init();
        } catch (Exception e) {
            LOGGER.error("[CurrencyHandler] Error load storage.");
        }
    }

    public static CurrencyHandler getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public ServerConfig getConfig() {
        return instance.config;
    }

    public LocaleConfig getLocale() {
        return instance.locale;
    }

    public PlayerProvider getPlayerProvider() {
        return instance.playerProvider;
    }

    public MinecraftServer getServer() {
        return instance.server;
    }
}