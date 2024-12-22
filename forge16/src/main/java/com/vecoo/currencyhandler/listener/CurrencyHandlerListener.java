package com.vecoo.currencyhandler.listener;

import com.vecoo.currencyhandler.CurrencyHandler;
import com.vecoo.currencyhandler.api.event.CurrencyFactoryEvent;
import com.vecoo.currencyhandler.util.Utils;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.player.UtilPlayer;
import net.minecraft.command.CommandSource;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CurrencyHandlerListener {
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onCurrencyFactorySet(CurrencyFactoryEvent.Set event) {
        CommandSource source = Utils.getSource(event.getSource());

        source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getSetBalanceSource()
                .replace("%player%", UtilPlayer.getPlayerName(event.getPlayerUuid()))
                .replace("%currency%", CurrencyHandler.getInstance().getLocale().getCurrencyName().get(event.getCurrency()))
                .replace("%amount%", String.valueOf(event.getAmount()))), false);

        UtilPlayer.sendMessageOffline(event.getPlayerUuid(), UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getSetBalancePlayer()
                .replace("%player%", source.getTextName())
                .replace("%currency%", CurrencyHandler.getInstance().getLocale().getCurrencyName().get(event.getCurrency()))
                .replace("%amount%", String.valueOf(event.getAmount()))), CurrencyHandler.getInstance().getServer());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onCurrencyFactoryGive(CurrencyFactoryEvent.Give event) {
        CommandSource source = Utils.getSource(event.getSource());

        source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getGiveBalanceSource()
                .replace("%player%", UtilPlayer.getPlayerName(event.getPlayerUuid()))
                .replace("%currency%", CurrencyHandler.getInstance().getLocale().getCurrencyName().get(event.getCurrency()))
                .replace("%amount%", String.valueOf(event.getAmount()))), false);

        UtilPlayer.sendMessageOffline(event.getPlayerUuid(), UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getGiveBalancePlayer()
                .replace("%player%", source.getTextName())
                .replace("%currency%", CurrencyHandler.getInstance().getLocale().getCurrencyName().get(event.getCurrency()))
                .replace("%amount%", String.valueOf(event.getAmount()))), CurrencyHandler.getInstance().getServer());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onCurrencyFactoryTake(CurrencyFactoryEvent.Take event) {
        CommandSource source = Utils.getSource(event.getSource());

        source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getTakeBalanceSource()
                .replace("%player%", UtilPlayer.getPlayerName(event.getPlayerUuid()))
                .replace("%currency%", CurrencyHandler.getInstance().getLocale().getCurrencyName().get(event.getCurrency()))
                .replace("%amount%", String.valueOf(event.getAmount()))), false);

        UtilPlayer.sendMessageOffline(event.getPlayerUuid(), UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getTakeBalancePlayer()
                .replace("%player%", source.getTextName())
                .replace("%currency%", CurrencyHandler.getInstance().getLocale().getCurrencyName().get(event.getCurrency()))
                .replace("%amount%", String.valueOf(event.getAmount()))), CurrencyHandler.getInstance().getServer());
    }
}
