package dev.lumareth.moreMissiles.block.types;

import dev.lumareth.moreMissiles.block.entity.AluminumWingHardpointBlockEntity;
import dev.lumareth.moreMissiles.block.states.Missiletype;
import dev.lumareth.moreMissiles.block.states.ModBlockStates;
import dev.lumareth.moreMissiles.utils.procedures.HardpointProcedures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AluminumWingHardpointBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final VoxelShape HITBOX = Block.box(0, 0, 0, 16, 11, 16);
    public static final EnumProperty<Missiletype> MISSILE_TYPE = ModBlockStates.MISSILE_TYPE;

    public AluminumWingHardpointBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(MISSILE_TYPE, Missiletype.EMPTY));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, MISSILE_TYPE);
    }

    @Override
    public boolean canConnectRedstone(BlockState blockState, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @NotNull
    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide){
            Missiletype currentType = blockState.getValue(MISSILE_TYPE);
            Missiletype updatedType = HardpointProcedures.reload(currentType, player, interactionHand);
            if (updatedType != currentType) {
                level.setBlock(blockPos, blockState.setValue(MISSILE_TYPE, updatedType), 3);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(MISSILE_TYPE, Missiletype.EMPTY);
    }

    @Nullable
    @Override
    public <T extends BlockEntity>BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return (lvl, pos, st, blockEntity) -> {
            if (!level.isClientSide()) {
                if (blockEntity instanceof AluminumWingHardpointBlockEntity be) {
                    be.tick();
                }
            }
            else {
                return;
            }
        };
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (blockState.getBlock() != newState.getBlock()) {
            ItemStack itemStack = HardpointProcedures.returnDroppedItem(blockState.getValue(MISSILE_TYPE));
            if (!itemStack.isEmpty()) {
                Block.popResource(level, blockPos, itemStack);
            }
            super.onRemove(blockState, level, blockPos, newState, isMoving);
        }
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AluminumWingHardpointBlockEntity(pPos, pState);
    }
}
