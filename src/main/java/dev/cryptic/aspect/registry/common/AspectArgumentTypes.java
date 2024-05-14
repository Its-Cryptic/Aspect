package dev.cryptic.aspect.registry.common;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.common.commands.arguments.AspectTypeArgument;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AspectArgumentTypes {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, Aspect.MODID);

    public static final RegistryObject<ArgumentTypeInfo<AspectTypeArgument,?>> ASPECT_TYPE = COMMAND_ARGUMENT_TYPES.register("aspect_arg_type", () -> SingletonArgumentInfo.contextFree(AspectTypeArgument::aspect));


    public static void registerArgumentTypes() {
        ArgumentTypeInfos.registerByClass(AspectTypeArgument.class, ASPECT_TYPE.get());
    }
    public static void init(IEventBus modEventBus) {
        COMMAND_ARGUMENT_TYPES.register(modEventBus);
    }
}
