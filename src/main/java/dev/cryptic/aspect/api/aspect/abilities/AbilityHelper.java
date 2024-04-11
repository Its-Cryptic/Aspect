package dev.cryptic.aspect.api.aspect.abilities;

import dev.cryptic.aspect.api.util.ClassUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class AbilityHelper {

    public static ArrayList<Method> getOverwrittenCastMethods(AbstractAbility ability) {
        ArrayList<Method> overwrittenMethods = new ArrayList<>();

        Arrays.stream(IAbilityInteractEvents.class.getMethods()).forEach(iMethod -> {
            if (ClassUtil.isMethodOverridden(ability.getClass(), iMethod.toString(), ClassUtil.getMethodParameterTypes(iMethod))) {
                overwrittenMethods.add(iMethod);
            }
        });
        return overwrittenMethods;
    }
}
