package com.vecoo.currencyhandler.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.vecoo.currencyhandler.CurrencyHandler;
import com.vecoo.currencyhandler.api.event.CurrencyReloadEvent;
import com.vecoo.currencyhandler.api.factory.CurrencyFactory;
import com.vecoo.currencyhandler.util.Utils;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.permission.UtilPermission;
import com.vecoo.extralib.player.UtilPlayer;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.common.MinecraftForge;

import java.util.Arrays;
import java.util.UUID;

public class CurrencyHandlerCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("ch")
                .requires(p -> UtilPermission.hasPermission(p, "minecraft.command.ch"))
                .then(Commands.literal("get")
                        .then(Commands.argument("player", StringArgumentType.string())
                                .suggests((s, builder) -> {
                                    for (String nick : s.getSource().getOnlinePlayerNames()) {
                                        if (nick.toLowerCase().startsWith(builder.getRemaining().toLowerCase())) {
                                            builder.suggest(nick);
                                        }
                                    }
                                    return builder.buildFuture();
                                })
                                .then(Commands.argument("currency", StringArgumentType.string())
                                        .suggests((s, builder) -> {
                                            for (String currency : Utils.getAllCurrency()) {
                                                if (currency.toLowerCase().startsWith(builder.getRemaining().toLowerCase())) {
                                                    builder.suggest(currency);
                                                }
                                            }
                                            return builder.buildFuture();
                                        })
                                        .executes(e -> executeGet(e.getSource(), StringArgumentType.getString(e, "currency"), StringArgumentType.getString(e, "player"))))))
                .then(Commands.literal("set")
                        .then(Commands.argument("player", StringArgumentType.string())
                                .suggests((s, builder) -> {
                                    for (String nick : s.getSource().getOnlinePlayerNames()) {
                                        if (nick.toLowerCase().startsWith(builder.getRemaining().toLowerCase())) {
                                            builder.suggest(nick);
                                        }
                                    }
                                    return builder.buildFuture();
                                })
                                .then(Commands.argument("currency", StringArgumentType.string())
                                        .suggests((s, builder) -> {
                                            for (String currency : Utils.getAllCurrency()) {
                                                if (currency.toLowerCase().startsWith(builder.getRemaining().toLowerCase())) {
                                                    builder.suggest(currency);
                                                }
                                            }
                                            return builder.buildFuture();
                                        })
                                        .then(Commands.argument("amount", FloatArgumentType.floatArg(0))
                                                .suggests((s, builder) -> {
                                                    for (int amount : Arrays.asList(100, 500, 1000)) {
                                                        builder.suggest(amount);
                                                    }
                                                    return builder.buildFuture();
                                                })
                                                .executes(e -> executeSet(e.getSource(), StringArgumentType.getString(e, "currency"), StringArgumentType.getString(e, "player"), FloatArgumentType.getFloat(e, "amount")))))))
                .then(Commands.literal("give")
                        .then(Commands.argument("player", StringArgumentType.string())
                                .suggests((s, builder) -> {
                                    for (String nick : s.getSource().getOnlinePlayerNames()) {
                                        if (nick.toLowerCase().startsWith(builder.getRemaining().toLowerCase())) {
                                            builder.suggest(nick);
                                        }
                                    }
                                    return builder.buildFuture();
                                })
                                .then(Commands.argument("currency", StringArgumentType.string())
                                        .suggests((s, builder) -> {
                                            for (String currency : Utils.getAllCurrency()) {
                                                if (currency.toLowerCase().startsWith(builder.getRemaining().toLowerCase())) {
                                                    builder.suggest(currency);
                                                }
                                            }
                                            return builder.buildFuture();
                                        })
                                        .then(Commands.argument("amount", FloatArgumentType.floatArg(0))
                                                .suggests((s, builder) -> {
                                                    for (int amount : Arrays.asList(100, 500, 1000)) {
                                                        builder.suggest(amount);
                                                    }
                                                    return builder.buildFuture();
                                                })
                                                .executes(e -> executeGive(e.getSource(), StringArgumentType.getString(e, "currency"), StringArgumentType.getString(e, "player"), FloatArgumentType.getFloat(e, "amount")))))))
                .then(Commands.literal("take")
                        .then(Commands.argument("player", StringArgumentType.string())
                                .suggests((s, builder) -> {
                                    for (String nick : s.getSource().getOnlinePlayerNames()) {
                                        if (nick.toLowerCase().startsWith(builder.getRemaining().toLowerCase())) {
                                            builder.suggest(nick);
                                        }
                                    }
                                    return builder.buildFuture();
                                })
                                .then(Commands.argument("currency", StringArgumentType.string())
                                        .suggests((s, builder) -> {
                                            for (String currency : Utils.getAllCurrency()) {
                                                if (currency.toLowerCase().startsWith(builder.getRemaining().toLowerCase())) {
                                                    builder.suggest(currency);
                                                }
                                            }
                                            return builder.buildFuture();
                                        })
                                        .then(Commands.argument("amount", FloatArgumentType.floatArg(0))
                                                .suggests((s, builder) -> {
                                                    for (int money : Arrays.asList(100, 500, 1000)) {
                                                        builder.suggest(money);
                                                    }
                                                    return builder.buildFuture();
                                                })
                                                .executes(e -> executeTake(e.getSource(), StringArgumentType.getString(e, "currency"), StringArgumentType.getString(e, "player"), FloatArgumentType.getFloat(e, "amount")))))))
                .then(Commands.literal("reload")
                        .executes(e -> executeReload(e.getSource()))));
    }

    private static int executeGet(CommandSource source, String currency, String target) {
        if (!UtilPlayer.hasUUID(target)) {
            source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getPlayerNotFound()
                    .replace("%player%", target)), false);
            return 0;
        }

        if (!Utils.getAllCurrency().contains(currency)) {
            source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getNotCurrency()), false);
            return 0;
        }

        if (CurrencyHandler.getInstance().getLocale().getCurrencyName().get(currency) == null) {
            source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getLocaleCurrency()), false);
            return 0;
        }

        source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getPlayerBalance()
                .replace("%player%", target)
                .replace("%currency%", CurrencyHandler.getInstance().getLocale().getCurrencyName().get(currency))
                .replace("%amount%", Utils.getFormattedFloat(CurrencyFactory.getCurrency(UtilPlayer.getUUID(target), currency)))), false);
        return 1;
    }

    private static int executeSet(CommandSource source, String currency, String target, float amount) {
        if (!UtilPlayer.hasUUID(target)) {
            source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getPlayerNotFound()
                    .replace("%player%", target)), false);
            return 0;
        }

        if (!Utils.getAllCurrency().contains(currency)) {
            source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getNotCurrency()), false);
            return 0;
        }

        if (CurrencyHandler.getInstance().getLocale().getCurrencyName().get(currency) == null) {
            source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getLocaleCurrency()), false);
            return 0;
        }

        CurrencyFactory.setCurrency(source.getTextName(), UtilPlayer.getUUID(target), currency, amount);
        return 1;
    }

    private static int executeGive(CommandSource source, String currency, String target, float amount) {
        if (!UtilPlayer.hasUUID(target)) {
            source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getPlayerNotFound()
                    .replace("%player%", target)), false);
            return 0;
        }

        if (!Utils.getAllCurrency().contains(currency)) {
            source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getNotCurrency()), false);
            return 0;
        }

        if (CurrencyHandler.getInstance().getLocale().getCurrencyName().get(currency) == null) {
            source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getLocaleCurrency()), false);
            return 0;
        }

        CurrencyFactory.giveCurrency(source.getTextName(), UtilPlayer.getUUID(target), currency, amount);
        return 1;
    }

    private static int executeTake(CommandSource source, String currency, String target, float amount) {
        if (!UtilPlayer.hasUUID(target)) {
            source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getPlayerNotFound()
                    .replace("%player%", target)), false);
            return 0;
        }

        if (!Utils.getAllCurrency().contains(currency)) {
            source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getNotCurrency()), false);
            return 0;
        }

        if (CurrencyHandler.getInstance().getLocale().getCurrencyName().get(currency) == null) {
            source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getLocaleCurrency()), false);
            return 0;
        }

        UUID targerUUID = UtilPlayer.getUUID(target);

        if (CurrencyFactory.getCurrency(targerUUID, currency) - amount < 0) {
            source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getNotBalanceSource()
                    .replace("%player%", target)
                    .replace("%currency%", CurrencyHandler.getInstance().getLocale().getCurrencyName().get(currency))), false);
            return 0;
        }

        CurrencyFactory.takeCurrency(source.getTextName(), targerUUID, currency, amount);
        return 1;
    }

    private static int executeReload(CommandSource source) {
        CurrencyHandler.getInstance().loadConfig();
        CurrencyHandler.getInstance().loadStorage();

        MinecraftForge.EVENT_BUS.post(new CurrencyReloadEvent());

        source.sendSuccess(UtilChat.formatMessage(CurrencyHandler.getInstance().getLocale().getReload()), false);
        return 1;
    }
}