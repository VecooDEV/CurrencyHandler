package com.vecoo.currencyhandler.config;

import com.vecoo.currencyhandler.CurrencyHandler;
import com.vecoo.extralib.gson.UtilGson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ServerConfig {
    private List<String> currenciesBonus = Arrays.asList("money", "bucks");
    private HashMap<String, Integer> currencyMap;
    private HashMap<String, Integer> currencyPermanentMap;
    private List<String> permissionCommisionList = Arrays.asList("minecraft.attribute.bonus.5", "minecraft.attribute.bonus.10", "minecraft.attribute.bonus.15");

    public ServerConfig() {
        this.currencyMap = new HashMap<>();
        this.currencyPermanentMap = new HashMap<>();

        this.currencyMap.put("money", 0);
        this.currencyMap.put("tokens", 0);
        this.currencyMap.put("points", 0);
        this.currencyPermanentMap.put("bucks", 0);
    }

    public List<String> getCurrenciesBonus() {
        return this.currenciesBonus;
    }

    public HashMap<String, Integer> getCurrencyMap() {
        return this.currencyMap;
    }

    public HashMap<String, Integer> getCurrencyPermanentMap() {
        return this.currencyPermanentMap;
    }

    public List<String> getPermissionCommisionList() {
        return this.permissionCommisionList;
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
                this.permissionCommisionList = config.getPermissionCommisionList();
                this.currenciesBonus = config.getCurrenciesBonus();
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