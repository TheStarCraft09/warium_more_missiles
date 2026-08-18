package dev.lumareth.moreMissiles.block.entity;

import dev.lumareth.moreMissiles.block.ModBlockEntities;
import dev.lumareth.moreMissiles.block.records.ControllerTargetData;
import dev.lumareth.moreMissiles.block.states.HardpointType;
import dev.lumareth.moreMissiles.block.states.Missiletype;
import dev.lumareth.moreMissiles.block.states.ModBlockStates;
import dev.lumareth.moreMissiles.block.types.WingtipHardpointBlock;
import dev.lumareth.moreMissiles.utils.procedures.WingtipHardpointProcedures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static dev.lumareth.moreMissiles.block.states.ModBlockStates.MISSILE_TYPE;


public class WingtipHardpointBlockEntity extends BlockEntity {
    private BlockPos ControlPos;
    private Vec3 TargetPos;
    private Vec3 TargetVelocity;
    private boolean Trigger = false;
    private double currentTarget = 0D;
    private double channel = 0D;
    private boolean was_powered = false;



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
        assert this.level != null;
        boolean powered = this.level.hasNeighborSignal(this.worldPosition);
        if (powered && !was_powered) {
            if (this.getBlockState().getValue(MISSILE_TYPE) != Missiletype.EMPTY) {
                fireMissile();
            } else {
                return;
            }
        }
        was_powered = powered;
        if (!data.contains("ControlX") || !data.contains("ControlY") || !data.contains("ControlZ")) {
            return;
        }
        double controlX = data.getDouble("ControlX");
        double controlY = data.getDouble("ControlY");
        double controlZ = data.getDouble("ControlZ");
        this.channel = data.getDouble("Channel");
        BlockState blockState = this.getBlockState();
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
        Vec3 launchDirection = getLaunchDirection();

        if (Trigger) {
            if (this.getBlockState().getValue(MISSILE_TYPE) != Missiletype.EMPTY) {
                fireMissile();
            } else {
                return;
            }
        }

    }

    public void fireMissile() {
        BlockState blockState = this.getBlockState();
        Vec3 launchDirection = getLaunchDirection();
        switch (blockState.getValue(MISSILE_TYPE)) {
            case IR -> {
                HardpointType hardpointType = blockState.getValue(ModBlockStates.HARDPOINT_TYPE);
                WingtipHardpointProcedures.fireSeekerSpear(level, this.worldPosition, hardpointType, launchDirection);
                level.setBlock(this.worldPosition, blockState.setValue(MISSILE_TYPE, Missiletype.EMPTY), Block.UPDATE_CLIENTS);
            }
        }
    }

    public Vec3 getLaunchDirection() {
        Vec3 launchDirection = Vec3.ZERO;
        Direction direction =
                this.getBlockState().getValue(WingtipHardpointBlock.FACING);

        return switch (direction) {
            case NORTH, SOUTH -> new Vec3(1D, 0D, 0D);
            case EAST, WEST -> new Vec3(0D, 0D, -1D);
            default -> Vec3.ZERO;
        };
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
