package com.vecoo.currencyhandler.storage.player;

import com.vecoo.currencyhandler.CurrencyHandler;

import java.util.HashMap;
import java.util.UUID;

public class PlayerStorage {
    private final UUID uuid;
    private final HashMap<String, Float> currencyMap;

    public PlayerStorage(UUID playerUUID, HashMap<String, Float> currencyMap) {
        this.uuid = playerUUID;
        this.currencyMap = currencyMap;
        CurrencyHandler.getInstance().getPlayerProvider().updatePlayerStorage(this);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public float getCurrency(String currency) {
        if (this.currencyMap.get(currency) == null) {
            CurrencyHandler.getLogger().error("[CurrencyHandler] An attempt was made to use a non-existent currency. Please register a currency to use it. Delete old data player for other currency.");
            return 0;
        }

        return this.currencyMap.get(currency);
    }

    public boolean setCurrency(String currency, float amount) {
        if (this.currencyMap.get(currency) == null) {
            CurrencyHandler.getLogger().error("[CurrencyHandler] An attempt was made to use a non-existent currency. Please register a currency to use it. Delete old data player for other currency.");
            return false;
        }

        this.currencyMap.put(currency, amount);
        CurrencyHandler.getInstance().getPlayerProvider().updatePlayerStorage(this);
        return true;
    }
}