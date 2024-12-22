package com.vecoo.currencyhandler.config;

import com.vecoo.currencyhandler.CurrencyHandler;
import com.vecoo.extralib.gson.UtilGson;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class LocaleConfig {
    private String reload = "&e(!) Configs have been reloaded. Depended modes reloaded.";

    private String playerBalance = "&e(!) Player %player% has %amount% %currency%.";
    private String setBalanceSource = "&e(!) Player %player%'s balance has been changed by %amount% %currency%.";
    private String setBalancePlayer = "&e(!) Your balance has been changed by %amount% %currency%.";
    private String giveBalanceSource = "&e(!) Player %player%'s balance has been added by %amount% %currency%.";
    private String giveBalancePlayer = "&e(!) Player %amount% %currency% have been added to your balance.";
    private String takeBalanceSource = "&e(!) Player %player%'s balance has been subtracted by %amount% %currency%.";
    private String takeBalancePlayer = "&e(!) Player %amount% %currency% have been subtracted from your balance.";
    private String notBalanceSource = "&c(!) Player %player% does not have enough %currency% to take.";
    private String notBalancePlayer = "&c(!) You do not have enough %currency%.";
    private String playerNotFound = "&c(!) Player %player% not found.";
    private String notCurrency = "&c(!) Currency not found.";
    private String localeCurrency = "&c(!) Please update the localization for this currency.";

    private HashMap<String, String> currencyName;

    public LocaleConfig() {
        this.currencyName = new HashMap<>();

        this.currencyName.put("money", "Money");
        this.currencyName.put("tokens", "Tokens");
        this.currencyName.put("points", "Points");
        this.currencyName.put("bucks", "Bucks");
    }

    public String getPlayerBalance() {
        return this.playerBalance;
    }

    public String getGiveBalancePlayer() {
        return this.giveBalancePlayer;
    }

    public String getGiveBalanceSource() {
        return this.giveBalanceSource;
    }

    public String getReload() {
        return this.reload;
    }

    public String getPlayerNotFound() {
        return this.playerNotFound;
    }

    public String getTakeBalancePlayer() {
        return this.takeBalancePlayer;
    }

    public String getTakeBalanceSource() {
        return this.takeBalanceSource;
    }

    public String getSetBalancePlayer() {
        return this.setBalancePlayer;
    }

    public String getSetBalanceSource() {
        return this.setBalanceSource;
    }

    public String getNotBalanceSource() {
        return this.notBalanceSource;
    }

    public String getNotBalancePlayer() {
        return this.notBalancePlayer;
    }

    public String getNotCurrency() {
        return this.notCurrency;
    }

    public String getLocaleCurrency() {
        return this.localeCurrency;
    }

    public HashMap<String, String> getCurrencyName() {
        return this.currencyName;
    }

    private void write() {
        UtilGson.writeFileAsync("/config/CurrencyHandler/", "locale.json", UtilGson.newGson().toJson(this)).join();
    }

    public void init() {
        try {
            CompletableFuture<Boolean> future = UtilGson.readFileAsync("/config/CurrencyHandler/", "locale.json", el -> {
                LocaleConfig config = UtilGson.newGson().fromJson(el, LocaleConfig.class);

                this.reload = config.getReload();
                this.playerBalance = config.getPlayerBalance();
                this.giveBalancePlayer = config.getGiveBalancePlayer();
                this.giveBalanceSource = config.getGiveBalanceSource();
                this.playerNotFound = config.getPlayerNotFound();
                this.takeBalancePlayer = config.getTakeBalancePlayer();
                this.takeBalanceSource = config.getTakeBalanceSource();
                this.setBalancePlayer = config.getSetBalancePlayer();
                this.setBalanceSource = config.getSetBalanceSource();
                this.notBalanceSource = config.getNotBalanceSource();
                this.giveBalancePlayer = config.getGiveBalancePlayer();
                this.notBalancePlayer = config.getNotBalancePlayer();
                this.notCurrency = config.getNotCurrency();
                this.currencyName = config.getCurrencyName();
                this.localeCurrency = config.getLocaleCurrency();
            });
            if (!future.join()) {
                write();
            }
        } catch (Exception e) {
            CurrencyHandler.getLogger().error("[CurrencyHandler] Error in locale config.");
            write();
        }
    }
}