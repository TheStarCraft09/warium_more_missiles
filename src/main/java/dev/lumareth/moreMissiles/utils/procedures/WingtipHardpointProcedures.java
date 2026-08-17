package dev.lumareth.moreMissiles.utils.procedures;

import dev.lumareth.moreMissiles.block.records.ControllerTargetData;
import dev.lumareth.moreMissiles.block.states.Missiletype;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

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

    public static ItemStack returnDroppedItem(Missiletype missiletype) {
        if (missiletype == Missiletype.IR) {
            return new ItemStack(CrustyChunksModItems.SEEKER_SPEAR_ROCKET.get());
        } else if (missiletype == Missiletype.SARH) {
            return new ItemStack(CrustyChunksModItems.RADAR_SPEAR_MISSILE.get());
        } else if (missiletype == Missiletype.SACLOCS) {
            return new ItemStack(CrustyChunksModItems.STRIKE_SPEAR_MISSILE.get());
        } else {
            return ItemStack.EMPTY;
        }
    }

    public static ControllerTargetData getTargetDataFromControlNode(BlockEntity controleNodeBlockEntity, Level level, double target) {
        if (!level.isClientSide) {
            Vec3 targetPos = Vec3.ZERO;
            Vec3 targetVelocity = Vec3.ZERO;
            BlockEntity bE = controleNodeBlockEntity;
            double TargetX = bE.getPersistentData().getDouble("TargetX" + target);
            double TargetY = bE.getPersistentData().getDouble("TargetY" + target);
            double TargetZ = bE.getPersistentData().getDouble("TargetZ" + target);
            targetPos = new Vec3(TargetX, TargetY, TargetZ);
            double VelocityX = bE.getPersistentData().getDouble("TMX" + target);
            double VelocityY = bE.getPersistentData().getDouble("TMY" + target);
            double VelocityZ = bE.getPersistentData().getDouble("TMZ" + target);
            targetVelocity = new Vec3(VelocityX, VelocityY, VelocityZ);
            return new ControllerTargetData(targetPos, targetVelocity);
        } else {
            return new ControllerTargetData(Vec3.ZERO, Vec3.ZERO);
        }
    }

    public static boolean getTriggerFromControlNode(BlockEntity controlNode, Level level, double Channel) {
        if (!level.isClientSide()) {
            String triggerKey = "Trigger" + Channel;
            double triggerValue = controlNode.getPersistentData().getDouble(triggerKey);
            if (triggerValue > 0.0D) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    public void fireSeekerSpear() {
        // Implementation for firing seeker spear
    }
}
