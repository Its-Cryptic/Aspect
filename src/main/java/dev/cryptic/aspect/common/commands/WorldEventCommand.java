package dev.cryptic.aspect.common.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.common.worldevent.LavaWorldEvent;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.handlers.WorldEventHandler;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class WorldEventCommand implements IAspectCommand {

    @Override
    public void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(Commands.literal(Aspect.MODID)
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("worldevent")
                        .then(Commands.literal("spawn")
                                .then(registerLavaCommand())
                                .then(registerStormCommand())
                        )
                )
        );
    }

    private LiteralArgumentBuilder<CommandSourceStack> registerLavaCommand() {
        return Commands.literal("lava")
                .then(Commands.argument("position", Vec3Argument.vec3())
                        .then(Commands.argument("radius", IntegerArgumentType.integer(1))
                                .then(Commands.argument("duration", IntegerArgumentType.integer(1))
                                        .executes(ctx -> {
                                            Vec3 position = Vec3Argument.getVec3(ctx, "position");
                                            int radius = IntegerArgumentType.getInteger(ctx, "radius");
                                            int duration = IntegerArgumentType.getInteger(ctx, "duration");

                                            LavaWorldEvent event = new LavaWorldEvent(position, radius, duration);
                                            WorldEventHandler.addWorldEvent(ctx.getSource().getLevel(), event);
                                            sendSuccess(ctx, event, position);;

                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                );
    }

    private LiteralArgumentBuilder<CommandSourceStack> registerStormCommand() {
        return Commands.literal("storm")
                .then(Commands.argument("position", Vec3Argument.vec3())
                        .then(Commands.argument("intensity", IntegerArgumentType.integer(1, 10)) // scale of 1 to 10
                                .then(Commands.argument("duration", IntegerArgumentType.integer(1))
                                        .executes(ctx -> {
                                            Vec3 position = Vec3Argument.getVec3(ctx, "position");
                                            int intensity = IntegerArgumentType.getInteger(ctx, "intensity");
                                            int duration = IntegerArgumentType.getInteger(ctx, "duration");

                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                );
    }


    private static String formatDouble(double num) {
        return String.format(Locale.ROOT, "%f", num);
    }

    private static void sendSuccess(CommandContext<CommandSourceStack> context, WorldEventInstance event, Vec3 position) {
        context.getSource().sendSuccess(() -> {
            return Component.translatable("aspect.commands.worldevent.success", event.type.id, formatDouble(position.x), formatDouble(position.y), formatDouble(position.z));
        }, true);
    }

    private static List<ArgumentType<?>> getEventParameters(WorldEventInstance event) {
        List<ArgumentType<?>> parameters = new ArrayList<>();
        Arrays.stream(event.getClass().getConstructors())
                .filter(constructor -> constructor.getParameterCount() > 0)
                .forEach(constructor -> Arrays.stream(constructor.getParameters())
                        .forEach(p -> {
                            //Aspect.LOGGER.info("Parameter: {}, Type: {}", p.getName(), p.getType().getSimpleName());
                            String name = p.getType().getSimpleName();
                            switch (name) {
                                case "boolean":
                                    parameters.add(BoolArgumentType.bool());
                                    break;
                                case "int":
                                    parameters.add(IntegerArgumentType.integer());
                                    break;
                                case "long":
                                    parameters.add(LongArgumentType.longArg());
                                    break;
                                case "float":
                                    parameters.add(FloatArgumentType.floatArg());
                                    break;
                                case "double":
                                    parameters.add(DoubleArgumentType.doubleArg());
                                    break;
                                case "Vec2":
                                    parameters.add(Vec2Argument.vec2());
                                    break;
                                case "Vec3":
                                    parameters.add(Vec3Argument.vec3());
                                    break;
                            }
                        })
                );
        return parameters;
    }
}
