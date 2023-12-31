package dev.cryptic.aspects;

import dev.cryptic.aspects.api.flux.FluxUtil;
import dev.cryptic.aspects.item.ModItems;
import dev.cryptic.aspects.api.flux.AspectType;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class AspectsPlugin extends KubeJSPlugin {
    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("AspectJS", FluxUtil.class);
        event.add("AspectType", AspectType.class);
        event.add("AspectItems", ModItems.class);
    }
}

