package dev.cryptic.aspect.item;

import dev.cryptic.aspect.Aspect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab TUTORIAL_TAB = new CreativeModeTab("riftrealms") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ZIRCON.get());
        }
    };

    public static final CreativeModeTab ASPECTS = new CreativeModeTab("aspects") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ZIRCON.get());
        }
    };

    public static final CreativeModeTab ASPECTS_TOOLS = new CreativeModeTab("aspects_tools") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ZIRCON.get());
        }
    };

    public static final ResourceLocation BACKGROUND_TAB = new ResourceLocation(Aspect.MODID, "textures/vfx/uv_test.png");
    public static final CreativeModeTab ASPECTS_BLOCKS = new CreativeModeTab("aspects_blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ZIRCON.get());
        }

        @Override
        public ResourceLocation getBackgroundImage() {
            return BACKGROUND_TAB;
        }
    };
}
