package com.vecoo.currencyhandler.storage.player;

import com.vecoo.currencyhandler.CurrencyHandler;
import com.vecoo.currencyhandler.api.event.CurrencyUpdateEvent;
import com.vecoo.currencyhandler.api.event.CurrencyUpdatePermanentEvent;
import com.vecoo.extralib.gson.UtilGson;
import com.vecoo.extralib.world.UtilWorld;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.UUID;

public class PlayerProvider {
    private final String filePath;
    private final String filePathPermanent;

    private final HashMap<UUID, PlayerStorage> storageMap;
    private final HashMap<UUID, PlayerStoragePermanent> storagePermanentMap;

    public PlayerProvider(String filePath, String filePathPermanent, MinecraftServer server) {
        this.filePath = UtilWorld.worldDirectory(filePath, server);
        this.filePathPermanent = UtilWorld.worldDirectory(filePathPermanent, server);

        this.storageMap = new HashMap<>();
        this.storagePermanentMap = new HashMap<>();
    }

    public HashMap<UUID, PlayerStorage> getStorageMap() {
        return this.storageMap;
    }

    public HashMap<UUID, PlayerStoragePermanent> getStoragePermanentMap() {
        return this.storagePermanentMap;
    }

    public PlayerStorage getPlayerStorage(UUID playerUUID) {
        if (this.storageMap.get(playerUUID) == null) {
            new PlayerStorage(playerUUID, CurrencyHandler.getInstance().getConfig().getCurrencyMap());
        }
        return this.storageMap.get(playerUUID);
    }

    public PlayerStoragePermanent getPlayerStoragePermanent(UUID playerUUID) {
        if (this.storagePermanentMap.get(playerUUID) == null) {
            new PlayerStoragePermanent(playerUUID, CurrencyHandler.getInstance().getConfig().getCurrencyPermanentMap());
        }
        return this.storagePermanentMap.get(playerUUID);
    }

    public void updatePlayerStorage(PlayerStorage player) {
        this.storageMap.put(player.getUuid(), player);

        MinecraftForge.EVENT_BUS.post(new CurrencyUpdateEvent(player));

        if (!write(player)) {
            getPlayerStorage(player.getUuid());
        }
    }

    public void updatePlayerStorage(PlayerStoragePermanent player) {
        this.storagePermanentMap.put(player.getUuid(), player);

        MinecraftForge.EVENT_BUS.post(new CurrencyUpdatePermanentEvent(player));

        if (!write(player)) {
            getPlayerStoragePermanent(player.getUuid());
        }
    }

    private boolean write(PlayerStorage player) {
        return UtilGson.writeFileAsync(filePath, player.getUuid() + ".json", UtilGson.newGson().toJson(player)).join();
    }

    private boolean write(PlayerStoragePermanent player) {
        return UtilGson.writeFileAsync(filePathPermanent, player.getUuid() + ".json", UtilGson.newGson().toJson(player)).join();
    }

    public void init() {
        String[] list = UtilGson.checkForDirectory(filePath).list();

        if (list == null) {
            return;
        }

        for (String file : list) {
            UtilGson.readFileAsync(filePath, file, el -> {
                PlayerStorage player = UtilGson.newGson().fromJson(el, PlayerStorage.class);
                this.storageMap.put(player.getUuid(), player);
            });
        }
    }

    public void initPermanent() {
        String[] list = UtilGson.checkForDirectory(filePathPermanent).list();

        if (list == null) {
            return;
        }

        for (String file : list) {
            UtilGson.readFileAsync(filePathPermanent, file, el -> {
                PlayerStoragePermanent playerPermanent = UtilGson.newGson().fromJson(el, PlayerStoragePermanent.class);
                this.storagePermanentMap.put(playerPermanent.getUuid(), playerPermanent);
            });
        }
    }
}