package dev.cryptic.aspect.registry.common;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.common.commands.GetAspectCommand;
import dev.cryptic.aspect.common.commands.IAspectCommand;
import dev.cryptic.aspect.common.commands.SetAspectCommand;
import dev.cryptic.aspect.common.commands.WorldEventCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Aspect.MODID)
public class AspectCommands {
    private static final List<IAspectCommand> COMMANDS = new ArrayList<>();

    public static final IAspectCommand SET_ASPECT = register(SetAspectCommand::new);
    public static final IAspectCommand GET_ASPECT = register(GetAspectCommand::new);
    public static final IAspectCommand WORLD_EVENT = register(WorldEventCommand::new);

    private static IAspectCommand register(Supplier<IAspectCommand> command) {
        IAspectCommand cmd = command.get();
        COMMANDS.add(cmd);
        return cmd;
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        COMMANDS.forEach(command -> command.register(event.getDispatcher(), event.getBuildContext()));
    }
}
