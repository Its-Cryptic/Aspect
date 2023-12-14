package dev.cryptic.aspects.api.util;

import dev.cryptic.aspects.Aspects;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import java.lang.reflect.Method;

public class GameruleFactory {
    public static int count = 0;
    public GameruleFactory() {
    }

    public static GameRules.Key<GameRules.BooleanValue> createBoolean(String name, GameRules.Category category, boolean defaultValue) {
        try {
            Method m = ObfuscationReflectionHelper.findMethod(GameRules.BooleanValue.class, "m_46250_", new Class[]{Boolean.TYPE});
            GameRules.Type<GameRules.BooleanValue> ruleTypeBoolean = (GameRules.Type)m.invoke((Object)null, defaultValue);
            GameRules.Key<GameRules.BooleanValue> rule = GameRules.register(name, category, ruleTypeBoolean);
            ++count;
            return rule;
        } catch (Exception var6) {
            Aspects.LOGGER.error("Gamerule Creation error", var6);
            return null;
        }
    }
}
