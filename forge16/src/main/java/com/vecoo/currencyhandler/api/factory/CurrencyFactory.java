package com.vecoo.currencyhandler.api.factory;

import com.vecoo.currencyhandler.CurrencyHandler;
import com.vecoo.currencyhandler.api.event.CurrencyFactoryEvent;
import com.vecoo.currencyhandler.storage.player.PlayerStorage;
import com.vecoo.currencyhandler.storage.player.PlayerStoragePermanent;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.UUID;

public class CurrencyFactory {
    public static HashMap<UUID, PlayerStorage> getMap() {
        return CurrencyHandler.getInstance().getPlayerProvider().getStorageMap();
    }

    public static HashMap<UUID, PlayerStoragePermanent> getMapPermanent() {
        return CurrencyHandler.getInstance().getPlayerProvider().getStoragePermanentMap();
    }

    public static boolean hasCurrency(UUID playerUUID, String currency, float amount) {
        return getCurrency(playerUUID, currency) >= amount;
    }

    public static float getCurrency(UUID playerUUID, String currency) {
        if (!CurrencyHandler.getInstance().getConfig().getCurrencyPermanentMap().containsKey(currency)) {
            return CurrencyHandler.getInstance().getPlayerProvider().getPlayerStorage(playerUUID).getCurrency(currency);
        }

        return CurrencyHandler.getInstance().getPlayerProvider().getPlayerStoragePermanent(playerUUID).getCurrencyPermanent(currency);
    }

    public static void setCurrency(String source, UUID playerUUID, String currency, float amount) {
        CurrencyFactoryEvent event = new CurrencyFactoryEvent.Set(source, playerUUID, currency, amount);
        MinecraftForge.EVENT_BUS.post(event);

        if (!CurrencyHandler.getInstance().getConfig().getCurrencyPermanentMap().containsKey(currency)) {
            CurrencyHandler.getInstance().getPlayerProvider().getPlayerStorage(event.getPlayerUuid()).setCurrency(event.getCurrency(), event.getAmount());
            return;
        }

        CurrencyHandler.getInstance().getPlayerProvider().getPlayerStoragePermanent(event.getPlayerUuid()).setCurrencyPermanent(event.getCurrency(), event.getAmount());
    }

    private static void setCurrency(UUID playerUUID, String currency, float amount) {
        if (!CurrencyHandler.getInstance().getConfig().getCurrencyPermanentMap().containsKey(currency)) {
            CurrencyHandler.getInstance().getPlayerProvider().getPlayerStorage(playerUUID).setCurrency(currency, amount);
            return;
        }

        CurrencyHandler.getInstance().getPlayerProvider().getPlayerStoragePermanent(playerUUID).setCurrencyPermanent(currency, amount);
    }

    public static void giveCurrency(String source, UUID playerUUID, String currency, float amount) {
        CurrencyFactoryEvent event = new CurrencyFactoryEvent.Give(source, playerUUID, currency, amount);
        MinecraftForge.EVENT_BUS.post(event);

        setCurrency(event.getPlayerUuid(), event.getCurrency(), getCurrency(event.getPlayerUuid(), event.getCurrency()) + event.getAmount());
    }

    public static void takeCurrency(String source, UUID playerUUID, String currency, float amount) {
        CurrencyFactoryEvent event = new CurrencyFactoryEvent.Take(source, playerUUID, currency, amount);
        MinecraftForge.EVENT_BUS.post(event);

        setCurrency(event.getPlayerUuid(), event.getCurrency(), getCurrency(event.getPlayerUuid(), event.getCurrency()) - event.getAmount());
    }
}