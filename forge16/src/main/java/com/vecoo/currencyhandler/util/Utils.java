package com.vecoo.currencyhandler.util;

import com.vecoo.currencyhandler.CurrencyHandler;
import com.vecoo.extralib.permission.UtilPermission;

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
            if (UtilPermission.hasPermission(playerUuid, playerName, permission)) {
                bonus = Math.max(bonus, Integer.parseInt(permission.substring(permission.lastIndexOf('.') + 1)));
            }
        }
        return bonus;
    }
}