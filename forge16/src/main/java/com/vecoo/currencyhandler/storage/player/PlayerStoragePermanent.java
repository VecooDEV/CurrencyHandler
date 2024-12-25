package com.vecoo.currencyhandler.storage.player;

import com.vecoo.currencyhandler.CurrencyHandler;

import java.util.HashMap;
import java.util.UUID;

public class PlayerStoragePermanent {
    private final UUID uuid;
    private final HashMap<String, Integer> currencyPermanentMap;

    public PlayerStoragePermanent(UUID playerUUID, HashMap<String, Integer> currencyPermanentMap) {
        this.uuid = playerUUID;
        this.currencyPermanentMap = currencyPermanentMap;
        CurrencyHandler.getInstance().getPlayerProvider().updatePlayerStorage(this);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public int getCurrencyPermanent(String currencyPermanent) {
        if (this.currencyPermanentMap.get(currencyPermanent) == null) {
            CurrencyHandler.getLogger().error("[CurrencyHandler] An attempt was made to use a non-existent currency. Please register a currency to use it. Delete old data player for other currency.");
            return 0;
        }

        return this.currencyPermanentMap.get(currencyPermanent);
    }

    public void setCurrencyPermanent(String currencyPermanent, int amount) {
        if (this.currencyPermanentMap.get(currencyPermanent) == null) {
            CurrencyHandler.getLogger().error("[CurrencyHandler] An attempt was made to use a non-existent currency. Please register a currency to use it. Delete old data player for other currency.");
            return;
        }

        this.currencyPermanentMap.put(currencyPermanent, amount);
        CurrencyHandler.getInstance().getPlayerProvider().updatePlayerStorage(this);
    }
}