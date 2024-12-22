package com.vecoo.currencyhandler.config;

import com.vecoo.currencyhandler.CurrencyHandler;
import com.vecoo.extralib.gson.UtilGson;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class ServerConfig {
    private HashMap<String, Float> currencyMap;
    private HashMap<String, Float> currencyPermanentMap;

    public ServerConfig() {
        this.currencyMap = new HashMap<>();
        this.currencyPermanentMap = new HashMap<>();

        this.currencyMap.put("money", 0F);
        this.currencyMap.put("tokens", 0F);
        this.currencyMap.put("points", 0F);
        this.currencyPermanentMap.put("bucks", 0F);
    }

    public HashMap<String, Float> getCurrencyMap() {
        return this.currencyMap;
    }

    public HashMap<String, Float> getCurrencyPermanentMap() {
        return this.currencyPermanentMap;
    }

    private void write() {
        UtilGson.writeFileAsync("/config/CurrencyHandler/", "config.json", UtilGson.newGson().toJson(this)).join();
    }

    public void init() {
        try {
            CompletableFuture<Boolean> future = UtilGson.readFileAsync("/config/CurrencyHandler/", "config.json", el -> {
                ServerConfig config = UtilGson.newGson().fromJson(el, ServerConfig.class);

                this.currencyMap = config.getCurrencyMap();
                this.currencyPermanentMap = config.getCurrencyPermanentMap();
            });
            if (!future.join()) {
                write();
            }
        } catch (Exception e) {
            CurrencyHandler.getLogger().error("[CurrencyHandler] Error in config.");
            write();
        }
    }
}