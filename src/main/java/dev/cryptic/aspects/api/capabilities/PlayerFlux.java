package dev.cryptic.aspects.api.capabilities;

import net.minecraft.nbt.CompoundTag;

public class PlayerFlux {
    private int flux = 0;
    private int MAX_FLUX = 100;

    public int getFlux() {
        return flux;
    }

    public int getMaxFlux() {
        return MAX_FLUX;
    }

    public void addFlux(int fluxAdded) {
        this.flux = Math.min(flux + fluxAdded, MAX_FLUX);
    }

    public void removeFlux(int fluxRemoved) {
        this.flux = Math.max(flux - fluxRemoved, 0);
    }

    public void setFlux(int flux) {
        this.flux = Math.min(flux, MAX_FLUX);
    }

    public void copyFrom(PlayerFlux source) {
        this.flux = source.flux;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("flux", flux);
    }

    public void loadNBTData(CompoundTag nbt) {
        flux = nbt.getInt("flux");
    }
}
