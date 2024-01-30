package dev.cryptic.aspect.api.capabilities.aspect;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.capabilities.CapabilityRegistry;
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

public class AspectCapabilityAttacher {
    private static class AspectCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

        // This will be the object's ID in the player's nbt under ForgeCaps
        public static final ResourceLocation ID = new ResourceLocation(Aspect.MODID, "aspect");

        private final IAspectCapability capability = new AspectCapability(null);
        private final LazyOptional<IAspectCapability> optional = LazyOptional.of(() -> capability);

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return CapabilityRegistry.ASPECT_CAPABILITY.orEmpty(cap, this.optional);
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
        final AspectCapabilityProvider provider = new AspectCapabilityProvider();
        event.addCapability(AspectCapabilityProvider.ID, provider);
    }
}
