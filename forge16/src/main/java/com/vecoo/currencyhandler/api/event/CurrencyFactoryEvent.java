package com.vecoo.currencyhandler.api.event;

import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import java.util.UUID;

@Cancelable
public class CurrencyFactoryEvent extends Event {
    private String source;
    private UUID playerUuid;
    private String currency;
    private int amount;

    public CurrencyFactoryEvent(String source, UUID playerUuid, String currency, int amount) {
        this.source = source;
        this.playerUuid = playerUuid;
        this.currency = currency;
        this.amount = amount;
    }

    public String getSource() {
        return this.source;
    }

    public UUID getPlayerUuid() {
        return this.playerUuid;
    }

    public String getCurrency() {
        return this.currency;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setPlayerUuid(UUID playerUuid) {
        this.playerUuid = playerUuid;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Cancelable
    public static class Set extends CurrencyFactoryEvent {
        public Set(String source, UUID playerUuid, String currency, int amount) {
            super(source, playerUuid, currency, amount);
        }
    }

    @Cancelable
    public static class Give extends CurrencyFactoryEvent {
        public Give(String source, UUID playerUuid, String currency, int amount) {
            super(source, playerUuid, currency, amount);
        }
    }

    @Cancelable
    public static class Take extends CurrencyFactoryEvent {
        public Take(String source, UUID playerUuid, String currency, int amount) {
            super(source, playerUuid, currency, amount);
        }
    }
}
