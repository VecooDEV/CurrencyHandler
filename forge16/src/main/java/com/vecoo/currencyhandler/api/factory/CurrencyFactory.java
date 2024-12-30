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

    public static boolean hasCurrency(UUID playerUUID, String currency, int amount) {
        return getCurrency(playerUUID, currency) >= amount;
    }

    public static int getCurrency(UUID playerUUID, String currency) {
        if (!CurrencyHandler.getInstance().getConfig().getCurrencyPermanentMap().containsKey(currency)) {
            return CurrencyHandler.getInstance().getPlayerProvider().getPlayerStorage(playerUUID).getCurrency(currency);
        }

        return CurrencyHandler.getInstance().getPlayerProvider().getPlayerStoragePermanent(playerUUID).getCurrencyPermanent(currency);
    }

    public static void setCurrency(String source, UUID playerUUID, String currency, int amount, boolean isEvent) {
        CurrencyFactoryEvent event = new CurrencyFactoryEvent.Set(source, playerUUID, currency, amount);

        if (isEvent) {
            MinecraftForge.EVENT_BUS.post(event);
        }

        if (!CurrencyHandler.getInstance().getConfig().getCurrencyPermanentMap().containsKey(currency)) {
            CurrencyHandler.getInstance().getPlayerProvider().getPlayerStorage(event.getPlayerUuid()).setCurrency(event.getCurrency(), event.getAmount());
            return;
        }

        CurrencyHandler.getInstance().getPlayerProvider().getPlayerStoragePermanent(event.getPlayerUuid()).setCurrencyPermanent(event.getCurrency(), event.getAmount());
    }

    public static void giveCurrency(String source, UUID playerUUID, String currency, int amount, boolean isEvent) {
        CurrencyFactoryEvent event = new CurrencyFactoryEvent.Give(source, playerUUID, currency, amount);

        if (isEvent) {
            MinecraftForge.EVENT_BUS.post(event);
        }

        setCurrency(source, event.getPlayerUuid(), event.getCurrency(), getCurrency(event.getPlayerUuid(), event.getCurrency()) + event.getAmount(), false);
    }

    public static void takeCurrency(String source, UUID playerUUID, String currency, int amount, boolean isEvent) {
        CurrencyFactoryEvent event = new CurrencyFactoryEvent.Take(source, playerUUID, currency, amount);

        if (isEvent) {
            MinecraftForge.EVENT_BUS.post(event);
        }

        setCurrency(source, event.getPlayerUuid(), event.getCurrency(), getCurrency(event.getPlayerUuid(), event.getCurrency()) - event.getAmount(), false);
    }

    public static void transferCurrency(String source, UUID playerUUID, UUID targetUUID, String currency, int amount, boolean isEvent) {
        CurrencyFactoryEvent.Transfer event = new CurrencyFactoryEvent.Transfer(source, playerUUID, targetUUID, currency, amount, amount);

        if (isEvent) {
            MinecraftForge.EVENT_BUS.post(event);
        }

        CurrencyFactory.setCurrency(source, event.getPlayerUuid(), event.getCurrency(), CurrencyFactory.getCurrency(event.getPlayerUuid(), event.getCurrency()) - event.getAmount(), false);
        CurrencyFactory.setCurrency(source, event.getTargetUuid(), event.getCurrency(), CurrencyFactory.getCurrency(event.getTargetUuid(), event.getCurrency()) + event.getTargetAmount(), false);
    }
}