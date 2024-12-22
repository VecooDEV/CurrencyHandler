package com.vecoo.currencyhandler.storage.player;

import com.vecoo.currencyhandler.CurrencyHandler;

import java.util.HashMap;
import java.util.UUID;

public class PlayerStoragePermanent {
    private final UUID uuid;
    private final HashMap<String, Float> currencyPermanentMap;

    public PlayerStoragePermanent(UUID playerUUID, HashMap<String, Float> currencyPermanentMap) {
        this.uuid = playerUUID;
        this.currencyPermanentMap = currencyPermanentMap;
        CurrencyHandler.getInstance().getPlayerProvider().updatePlayerStorage(this);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public float getCurrencyPermanent(String currencyPermanent) {
        if (this.currencyPermanentMap.get(currencyPermanent) == null) {
            CurrencyHandler.getLogger().error("[CurrencyHandler] An attempt was made to use a non-existent currency. Please register a currency to use it. Delete old data player for other currency.");
            return 0;
        }

        return this.currencyPermanentMap.get(currencyPermanent);
    }

    public boolean setCurrencyPermanent(String currencyPermanent, float amount) {
        if (this.currencyPermanentMap.get(currencyPermanent) == null) {
            CurrencyHandler.getLogger().error("[CurrencyHandler] An attempt was made to use a non-existent currency. Please register a currency to use it. Delete old data player for other currency.");
            return false;
        }

        this.currencyPermanentMap.put(currencyPermanent, amount);
        CurrencyHandler.getInstance().getPlayerProvider().updatePlayerStorage(this);
        return true;
    }
}