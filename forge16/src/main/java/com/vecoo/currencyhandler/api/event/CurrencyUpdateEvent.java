package com.vecoo.currencyhandler.api.event;

import com.vecoo.currencyhandler.storage.player.PlayerStorage;
import net.minecraftforge.eventbus.api.Event;

public class CurrencyUpdateEvent extends Event {
    private final PlayerStorage playerStorage;

    public CurrencyUpdateEvent(PlayerStorage playerStorage) {
        this.playerStorage = playerStorage;
    }

    public PlayerStorage getPlayerStorage() {
        return this.playerStorage;
    }
}