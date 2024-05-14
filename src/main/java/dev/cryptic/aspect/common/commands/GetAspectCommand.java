package dev.cryptic.aspect.common.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
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

public class GetAspectCommand implements IAspectCommand {

    @Override
    public void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        LiteralCommandNode<CommandSourceStack> getCommandBase = dispatcher.register(Commands.literal(Aspect.MODID)
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("get")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(ctx -> {
                                    ServerPlayer player = EntityArgument.getPlayer(ctx, "targets");
                                    AspectType aspect = AspectUtil.getAspect(player);
                                    sendSuccess(ctx, player, aspect);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
        );
    }

    private static void sendSuccess(CommandContext<CommandSourceStack> context, ServerPlayer player, AspectType type) {
        context.getSource().sendSuccess(() ->
                        Component.translatable("aspect.commands.getaspect.success", player.getDisplayName(), type.getName()),
                true
        );
    }
}
