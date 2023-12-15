package dev.cryptic.aspects;

import dev.cryptic.aspects.api.attribute.AttributeRegistry;
import dev.cryptic.aspects.kubejs.bindings.AspectJSWrapper;
import dev.cryptic.aspects.kubejs.bindings.AspectType;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class AspectsPlugin extends KubeJSPlugin {
    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("AspectJS", AspectJSWrapper.class);
        event.add("AspectType", AspectType.class);
    }
}

