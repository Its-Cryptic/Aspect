package dev.cryptic.aspects.api.client;

public class ClientThirstData {
    private static int playerEnergy;

    public static void set (int energy) {
        ClientThirstData.playerEnergy = energy;
    }

    public static int getPlayerThirst() {
        return playerEnergy;
    }
}
