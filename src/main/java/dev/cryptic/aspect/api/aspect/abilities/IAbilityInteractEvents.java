package dev.cryptic.aspect.api.aspect.abilities;

import net.minecraftforge.client.event.InputEvent;

public interface IAbilityInteractEvents {

    default void onConsumeClickActivationKey(InputEvent.Key event) {
    }

    default void onDoubleClickActivationKey() {
    }

    default void onHeldActivationKey() {
    }

    default void onReleaseHeldActivationKey() {
    }
}
