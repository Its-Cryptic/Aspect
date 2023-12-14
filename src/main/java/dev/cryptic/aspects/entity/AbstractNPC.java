//package dev.cryptic.aspects.entity;
//
//import net.minecraft.world.entity.AgeableMob;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.npc.AbstractVillager;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.pathfinder.BlockPathTypes;
//
//public abstract class AbstractNPC extends AgeableMob {
//    protected String name;
//    protected int health;
//    protected Position position; // Position is a hypothetical class for NPC's location
//
//    public AbstractNPC(EntityType<? extends AbstractNPC> type, Level level) {
//        super(type, level);
//    }
//
//    public AbstractVillager(EntityType<? extends AbstractVillager> p_35267_, Level p_35268_) {
//        super(p_35267_, p_35268_);
//        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
//        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
//    }
//
//    // Abstract methods
//    public abstract void move();
//    public abstract void speak();
//    public abstract void interact();
//
//    // Concrete method
//    public void setHealth(int health) {
//        this.health = health;
//    }
//
//    // Other common methods...
//}
//
//// Example of a subclass
//public class Merchant extends AbstractNPC {
//
//    public Merchant(String name, int health, Position position) {
//        super(name, health, position);
//    }
//
//    @Override
//    public void move() {
//        // Implementation for move
//    }
//
//    @Override
//    public void speak() {
//        // Implementation for speak
//    }
//
//    @Override
//    public void interact() {
//        // Implementation for interact
//    }
//
//    // Additional methods specific to Merchant...
//}
//
//// Position class (as an example)
//public class Position {
//    private int x;
//    private int y;
//
//    // Constructor, getters, setters...
//}