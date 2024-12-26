package com.vecoo.currencyhandler.listener;

import com.vecoo.currencyhandler.CurrencyHandler;
import com.vecoo.currencyhandler.api.event.CurrencyFactoryEvent;
import com.vecoo.currencyhandler.util.Utils;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.player.UtilPlayer;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Arrays;
import java.util.List;

public class CurrencyHandlerListener {
    public static List<String> ignoreEvent = Arrays.asList("ExtraGTS", "ExtraEconomy", "ExtraShop", "Donate", "Other");

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onCurrencyFactorySet(CurrencyFactoryEvent.Set event) {
        if (ignoreEvent.contains(event.getSource())) {
            return;
        }

        CommandSource source = UtilPlayer.getSource(event.getSource(), CurrencyHandler.getInstance().getServer());

        source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getSetBalanceSource()
                .replace("%player%", UtilPlayer.getPlayerName(event.getPlayerUuid()))
                .replace("%currency%", CurrencyHandler.getInstance().getLocale().getCurrencyName().get(event.getCurrency()))
                .replace("%amount%", String.valueOf(event.getAmount()))), false);

        UtilPlayer.sendMessageUuid(event.getPlayerUuid(), UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getSetBalancePlayer()
                .replace("%player%", source.getTextName())
                .replace("%currency%", CurrencyHandler.getInstance().getLocale().getCurrencyName().get(event.getCurrency()))
                .replace("%amount%", String.valueOf(event.getAmount()))), CurrencyHandler.getInstance().getServer());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onCurrencyFactoryGive(CurrencyFactoryEvent.Give event) {
        if (ignoreEvent.contains(event.getSource())) {
            return;
        }

        CommandSource source = UtilPlayer.getSource(event.getSource(), CurrencyHandler.getInstance().getServer());

        for (String currency : CurrencyHandler.getInstance().getConfig().getCurrenciesBonus()) {
            if (!Utils.getAllCurrency().contains(currency)) {
                source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getNotCurrency()), false);
                return;
            }
        }

        int amount = event.getAmount();
        StringTextComponent bonusMessageSource = new StringTextComponent("");
        StringTextComponent bonusMessagePlayer = new StringTextComponent("");

        int bonus = Utils.bonusGive(event.getPlayerUuid(), UtilPlayer.getPlayerName(event.getPlayerUuid()));

        if (CurrencyHandler.getInstance().getConfig().getCurrenciesBonus().contains(event.getCurrency()) && bonus > 0) {
            amount += amount * bonus / 100;

            bonusMessageSource = UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getBonusMessageSource()
                    .replace("%bonus%", String.valueOf(bonus)));
            bonusMessagePlayer = UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getBonusMessagePlayer()
                    .replace("%bonus%", String.valueOf(bonus)));

            event.setAmount(amount);
        }

        source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getGiveBalanceSource()
                .replace("%player%", UtilPlayer.getPlayerName(event.getPlayerUuid()))
                .replace("%currency%", CurrencyHandler.getInstance().getLocale().getCurrencyName().get(event.getCurrency()))
                .replace("%amount%", String.valueOf(amount))).append(bonusMessageSource), false);

        UtilPlayer.sendMessageUuid(event.getPlayerUuid(), (StringTextComponent) UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getGiveBalancePlayer()
                .replace("%player%", source.getTextName())
                .replace("%currency%", CurrencyHandler.getInstance().getLocale().getCurrencyName().get(event.getCurrency()))
                .replace("%amount%", String.valueOf(amount))).append(bonusMessagePlayer), CurrencyHandler.getInstance().getServer());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onCurrencyFactoryTake(CurrencyFactoryEvent.Take event) {
        if (ignoreEvent.contains(event.getSource())) {
            return;
        }

        CommandSource source = UtilPlayer.getSource(event.getSource(), CurrencyHandler.getInstance().getServer());

        source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getTakeBalanceSource()
                .replace("%player%", UtilPlayer.getPlayerName(event.getPlayerUuid()))
                .replace("%currency%", CurrencyHandler.getInstance().getLocale().getCurrencyName().get(event.getCurrency()))
                .replace("%amount%", String.valueOf(event.getAmount()))), false);

        UtilPlayer.sendMessageUuid(event.getPlayerUuid(), UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getTakeBalancePlayer()
                .replace("%player%", source.getTextName())
                .replace("%currency%", CurrencyHandler.getInstance().getLocale().getCurrencyName().get(event.getCurrency()))
                .replace("%amount%", String.valueOf(event.getAmount()))), CurrencyHandler.getInstance().getServer());
    }
}