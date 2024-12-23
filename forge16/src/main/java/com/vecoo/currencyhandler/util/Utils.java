package com.vecoo.currencyhandler.util;

import com.vecoo.currencyhandler.CurrencyHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<String> getAllCurrency() {
        List<String> currencyList = new ArrayList<>();

        currencyList.addAll(CurrencyHandler.getInstance().getConfig().getCurrencyMap().keySet());
        currencyList.addAll(CurrencyHandler.getInstance().getConfig().getCurrencyPermanentMap().keySet());

        return currencyList;
    }

    public static CommandSource getSource(String sourceName) {
        ServerPlayerEntity player = CurrencyHandler.getInstance().getServer().getPlayerList().getPlayerByName(sourceName);
        return (player != null) ? player.createCommandSourceStack() : CurrencyHandler.getInstance().getServer().createCommandSourceStack();
    }

    public static String getFormattedFloat(float value) {
        String format = String.format("%.3f", value)
                .replaceAll("\\.?0+$", "");

        if (format.endsWith(",")) {
            format = format.replace(",", "");
        }

        return format;
    }
}
