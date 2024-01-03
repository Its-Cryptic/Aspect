package dev.cryptic.aspects.aspect.abilities;

public class ScanAbility {

    public ScanAbilityType() {
        super(10);
    }

    @Override
    public void onEnable(Ability ability, Level level, Player player) {
        super.onEnable(ability, level, player);
        if(!level.isClientSide) return;
        ScannerRenderer.getInstance().setup(player, 200);
    }

    @Override
    public void tick(Ability ability, Level level, Player player) {
        super.tick(ability, level, player);
        if(ability.isEnabled()) {
            ability.disable(player);
        }
    }
}
}
