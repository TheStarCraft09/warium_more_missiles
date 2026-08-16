package dev.lumareth.moreMissiles.utils.procedures;

import dev.lumareth.moreMissiles.block.states.Missiletype;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class WingtipHardpointProcedures {
    public static Missiletype reload(Missiletype currentMissileType, LivingEntity entity, InteractionHand interactionHand) {
    if (interactionHand != InteractionHand.MAIN_HAND){
        return currentMissileType;
    }

    if (currentMissileType == Missiletype.EMPTY) {
        ItemStack itemStack = entity.getItemInHand(interactionHand);
        if (itemStack.is(CrustyChunksModItems.SEEKER_SPEAR_ROCKET.get())){
            entity.setItemInHand(interactionHand, ItemStack.EMPTY);
            return Missiletype.IR;
        } else if (itemStack.is(CrustyChunksModItems.RADAR_SPEAR_MISSILE.get())) {
            entity.setItemInHand(interactionHand, ItemStack.EMPTY);
            return Missiletype.SARH;
        } else if (itemStack.is(CrustyChunksModItems.STRIKE_SPEAR_MISSILE.get())) {
            entity.setItemInHand(interactionHand, ItemStack.EMPTY);
            return Missiletype.SACLOCS;
        }
    } else {
        if (entity.getItemInHand(interactionHand).isEmpty()) {
            if (interactionHand == InteractionHand.MAIN_HAND) {
                if (currentMissileType == Missiletype.IR) {
                    entity.setItemInHand(interactionHand, new ItemStack(CrustyChunksModItems.SEEKER_SPEAR_ROCKET.get()));
                } else if (currentMissileType == Missiletype.SACLOCS) {
                    entity.setItemInHand(interactionHand, new ItemStack(CrustyChunksModItems.STRIKE_SPEAR_MISSILE.get()));
                } else if (currentMissileType == Missiletype.SARH) {
                    entity.setItemInHand(interactionHand, new ItemStack(CrustyChunksModItems.RADAR_SPEAR_MISSILE.get()));
                }
                return Missiletype.EMPTY;
            }
        } else {
            return currentMissileType;
        }

    }

    return Missiletype.EMPTY;
    }
}
