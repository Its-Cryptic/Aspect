package dev.cryptic.aspect.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;

public interface IAspectCommand {
    void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext);
}
