package com.vecoo.currencyhandler.util;

import com.mojang.authlib.GameProfile;
import com.vecoo.currencyhandler.CurrencyHandler;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utils {
    public static List<String> getAllCurrency() {
        List<String> currencyList = new ArrayList<>();

        currencyList.addAll(CurrencyHandler.getInstance().getConfig().getCurrencyMap().keySet());
        currencyList.addAll(CurrencyHandler.getInstance().getConfig().getCurrencyPermanentMap().keySet());

        return currencyList;
    }

    public static int bonusGive(UUID playerUuid, String playerName) {
        int bonus = 0;

        for (String permission : CurrencyHandler.getInstance().getConfig().getPermissionBonusList()) {
            if (PermissionAPI.hasPermission(new GameProfile(playerUuid, playerName), permission, new Context())) {
                bonus = Math.max(bonus, Integer.parseInt(permission.substring(permission.lastIndexOf('.') + 1)));
            }
        }
        return bonus;
    }
}