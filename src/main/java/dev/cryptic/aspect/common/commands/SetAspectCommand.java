package dev.cryptic.aspect.common.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.aspect.AspectType;
import dev.cryptic.aspect.api.util.AspectUtil;
import dev.cryptic.aspect.common.commands.arguments.AspectTypeArgument;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetAspectCommand implements IAspectCommand {

    @Override
    public void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(Commands.literal(Aspect.MODID)
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("set")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.argument("aspect", AspectTypeArgument.aspect())
                                        .executes(ctx -> {
                                            AspectType aspect = AspectTypeArgument.getAspect(ctx, "aspect");
                                            ServerPlayer player = EntityArgument.getPlayer(ctx, "targets");
                                            AspectUtil.setAspect(player, aspect);
                                            sendSuccess(ctx, player, aspect);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                )
        );
    }

    private static void sendSuccess(CommandContext<CommandSourceStack> context, ServerPlayer player, AspectType type) {
        context.getSource().sendSuccess(() ->
                Component.translatable("aspect.commands.setaspect.success", player.getDisplayName(), type.getName()),
                true
        );
    }
}
