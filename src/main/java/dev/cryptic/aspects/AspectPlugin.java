package dev.cryptic.aspects;

import dev.cryptic.aspects.compat.kubejs.AspectJS;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class AspectPlugin extends KubeJSPlugin {
    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("AspectJS", AspectJS.class);
    }
}

