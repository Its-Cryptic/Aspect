package dev.cryptic.aspect.api.aspect.abilities;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public interface CommonAbilityEvents {

    void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty event);
    void onRightClickBlock(PlayerInteractEvent.RightClickBlock event);
    void onRightClickItem(PlayerInteractEvent.RightClickItem event);
    void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event);
    void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event);
    void onEntityInteract(PlayerInteractEvent.EntityInteract event);
    void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event);
    void onAttackEntity(AttackEntityEvent event);
    void onTakeDamage(LivingHurtEvent event);
}
