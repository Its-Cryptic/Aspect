package dev.cryptic.aspects.api.capabilities;

import dev.cryptic.aspects.Aspect;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluxCapabilityAttacher {
    private static class FluxCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

        // This will be the object's ID in the player's nbt under ForgeCaps
        public static final ResourceLocation ID = new ResourceLocation(Aspect.MODID, "flux");

        private final IFluxCapability capability = new FluxCapability(null);
        private final LazyOptional<IFluxCapability> optional = LazyOptional.of(() -> capability);

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return CapabilityRegistry.FLUX_CAPABILITY.orEmpty(cap, this.optional);
        }

        void invalidate() {
            this.optional.invalidate();
        }

        @Override
        public CompoundTag serializeNBT() {
            return this.capability.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.capability.deserializeNBT(nbt);
        }

    }

    public static void attach(final AttachCapabilitiesEvent<Entity> event) {
        final FluxCapabilityProvider provider = new FluxCapabilityProvider();
        event.addCapability(FluxCapabilityProvider.ID, provider);
    }
}
