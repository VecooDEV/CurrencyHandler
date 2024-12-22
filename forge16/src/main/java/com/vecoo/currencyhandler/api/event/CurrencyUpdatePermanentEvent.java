package com.vecoo.currencyhandler.api.event;

import com.vecoo.currencyhandler.storage.player.PlayerStoragePermanent;
import net.minecraftforge.eventbus.api.Event;

public class CurrencyUpdatePermanentEvent extends Event {
    private final PlayerStoragePermanent playerStoragePermanent;

    public CurrencyUpdatePermanentEvent(PlayerStoragePermanent playerStoragePermanent) {
        this.playerStoragePermanent = playerStoragePermanent;
    }

    public PlayerStoragePermanent getPlayerStoragePermanent() {
        return this.playerStoragePermanent;
    }
}