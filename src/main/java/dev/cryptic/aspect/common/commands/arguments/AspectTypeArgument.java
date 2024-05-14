package dev.cryptic.aspect.common.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.aspect.AspectType;
import dev.cryptic.aspect.api.registry.AspectRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class AspectTypeArgument implements ArgumentType<AspectType> {
    private static final Collection<String> EXAMPLES = Arrays.asList("flame", "lightning");
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_ASPECT = new DynamicCommandExceptionType((object) ->
            Component.translatable("argument.aspect.id.invalid", object.toString()));


    public static AspectTypeArgument aspect() {
        return new AspectTypeArgument();
    }

    public static AspectType getAspect(CommandContext<CommandSourceStack> context, String name) {
        return context.getArgument(name, AspectType.class);
    }

    @Override
    public AspectType parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation resourceLocation = ResourceLocation.read(reader);
        if (resourceLocation.getNamespace().equals("minecraft")) resourceLocation = Aspect.id(resourceLocation.getPath());
        AspectType aspectType = AspectRegistry.REGISTRY.get().getValue(resourceLocation);
        if (aspectType != null) {
            return aspectType;
        } else {
            throw ERROR_UNKNOWN_ASPECT.create(resourceLocation);
        }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (RegistryObject<AspectType> aspect : AspectRegistry.ASPECTS.getEntries()) {
            ResourceLocation key = aspect.getId();
            if (key != null) {
                builder.suggest(key.toString());
            }
        }
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
