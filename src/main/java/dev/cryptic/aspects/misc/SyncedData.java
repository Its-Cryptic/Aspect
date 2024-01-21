package dev.cryptic.aspects.misc;

import dev.cryptic.aspects.api.flux.AspectType;
import dev.cryptic.aspects.entity.fluxentity.AbstractFluxEntity;
import dev.cryptic.aspects.entity.fluxentity.golem.AbstractGolem;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SyncedData {

    private SyncedData() {
    }
    public static long serverTime;
    public static long levelTime;
    public static long dayTime;
    public static HashMap<Player, PlayerData> playerData = new HashMap<>();

    public static void setServerTime(long serverTime) {
        SyncedData.serverTime = serverTime;
    }

    public static void setLevelTime(long levelTime) {
        SyncedData.levelTime = levelTime;
    }

    public static void setDayTime(long dayTime) {
        SyncedData.dayTime = dayTime;
    }

    public static void setPlayerData(Player player, PlayerData data) {
        SyncedData.playerData.put(player, data);
    }

    public static PlayerData getPlayerData(Player player) {
        PlayerData playerData = SyncedData.playerData.get(player);
        if (playerData == null) {
            playerData = new PlayerData();
            SyncedData.playerData.put(player, playerData);
        }
        return playerData;
    }

    public static class PlayerData {

        public float flux;
        public int maxFlux;
        public int aspectLevel;
        public AspectType aspectType;
        public int soul;
        public int maxSoul;
        private List<GolemData> golems = new ArrayList<>();

        public PlayerData() {
        }
        public void setFlux(float flux) {
            this.flux = flux;
        }
        public float getFlux() {
            return flux;
        }
        public void setMaxFlux(int maxFlux) {
            this.maxFlux = maxFlux;
        }
        public int getMaxFlux() {
            return maxFlux;
        }
        public void setAspectLevel(int aspectLevel) {
            this.aspectLevel = aspectLevel;
        }
        public int getAspectLevel() {
            return aspectLevel;
        }
        public void setAspectType(AspectType aspectType) {
            this.aspectType = aspectType;
        }
        public AspectType getAspectType() {
            return aspectType;
        }
        public int getSoul() {
            return maxSoul - getTotalImbuedSoul();
        }
        public void setMaxSoul(int maxSoul) {
            this.maxSoul = maxSoul;
        }
        public int getMaxSoul() {
            return maxSoul;
        }

        public void addGolem(AbstractGolem golemEntity, int imbuedSoul) {
            this.golems.add(new GolemData(golemEntity, imbuedSoul));
        }

        public void removeGolem(AbstractGolem golemEntity) {
            this.golems.removeIf(golemData -> golemData.getGolemUUID().equals(golemEntity.getUUID()));
        }

        public List<GolemData> getGolems() {
            return new ArrayList<>(this.golems);
        }

        public int getTotalImbuedSoul() {
            int total = 0;
            for (GolemData golemData : this.golems) {
                total += golemData.getImbuedSoul();
            }
            return total;
        }
    }

    public static class GolemData {
        private AbstractGolem golemEntity;
        private int imbuedSoul;

        public GolemData(AbstractGolem golemEntity, int imbuedSoul) {
            this.golemEntity = golemEntity;
            this.imbuedSoul = imbuedSoul;
        }

        public AbstractGolem getGolemEntity() {
            return golemEntity;
        }

        public UUID getGolemUUID() {
            return golemEntity.getUUID();
        }

        public int getImbuedSoul() {
            return imbuedSoul;
        }
    }

}
