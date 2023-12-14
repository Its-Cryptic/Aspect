package dev.cryptic.aspects.api.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerFluxProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerFlux> PLAYER_FLUX = CapabilityManager.get(new CapabilityToken<PlayerFlux>() { });

    private PlayerFlux flux = null;
    private final LazyOptional<PlayerFlux> optional = LazyOptional.of(this::createPlayerFlux);

    private PlayerFlux createPlayerFlux() {
        if (this.flux == null) {
            this.flux = new PlayerFlux();
        }

        return this.flux;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_FLUX) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerFlux().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerFlux().loadNBTData(nbt);
    }
}
