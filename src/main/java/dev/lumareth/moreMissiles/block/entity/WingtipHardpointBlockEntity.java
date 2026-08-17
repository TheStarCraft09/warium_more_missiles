package dev.lumareth.moreMissiles.block.entity;

import dev.lumareth.moreMissiles.block.ModBlockEntities;
import dev.lumareth.moreMissiles.block.records.ControllerTargetData;
import dev.lumareth.moreMissiles.utils.procedures.WingtipHardpointProcedures;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WingtipHardpointBlockEntity extends BlockEntity {
    private BlockPos ControlPos;
    private Vec3 TargetPos;
    private Vec3 TargetVelocity;
    private boolean Trigger = false;
    private double currentTarget = 0D;
    private double channel = 0D;



    public WingtipHardpointBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WINGTIP_HARDPOINT.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        if (compoundTag.contains("ControlPos")) {
            this.ControlPos = NbtUtils.readBlockPos(compoundTag.getCompound("ControlPos"));
        }
        if (compoundTag.contains("Channel")) {
            this.channel = compoundTag.getDouble("Channel");
        }
    }

    @Override
    public void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if (this.ControlPos != null) {
            compoundTag.put("ControlPos", NbtUtils.writeBlockPos(this.ControlPos));
        }
        if (this.channel != 0D) {
            compoundTag.putDouble("Channel", this.channel);
        }
    }

    public void tick() {
        CompoundTag data = this.getPersistentData();
        if (this.ControlPos == null || this.ControlPos.equals(BlockPos.ZERO)) {
            return;
        }
        double controlX = data.getDouble("ControlX");
        double controlY = data.getDouble("ControlY");
        double controlZ = data.getDouble("ControlZ");
        this.channel = data.getDouble("Channel");
        ControllerTargetData targetData;
        ControlPos = BlockPos.containing(controlX, controlY, controlZ);
        BlockEntity controlBlockEntity = level.getBlockEntity(ControlPos);
        if (controlBlockEntity != null) {
            currentTarget = controlBlockEntity.getPersistentData().getDouble("SelectedTarget");
        } else {
            currentTarget = 0D;
        }
        targetData = WingtipHardpointProcedures.getTargetDataFromControlNode(controlBlockEntity, level, currentTarget);
        Trigger = WingtipHardpointProcedures.getTriggerFromControlNode(controlBlockEntity, level, channel);
        TargetPos = targetData.position();
        TargetVelocity = targetData.velocity();
        setChanged();


    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(){
        return this.saveWithFullMetadata();
    }

}
