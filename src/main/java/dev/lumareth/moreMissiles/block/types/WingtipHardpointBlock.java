package dev.lumareth.moreMissiles.block.types;

import dev.lumareth.moreMissiles.block.states.HardpointType;
import dev.lumareth.moreMissiles.block.states.ModBlockStates;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class WingtipHardpointBlock extends HorizontalDirectionalBlock {
    public static final VoxelShape BOTTOM = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    public static final VoxelShape TOP = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    public static final EnumProperty<HardpointType> TYPE = ModBlockStates.HARDPOINT_TYPE;
    public WingtipHardpointBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, HardpointType.BOTTOM));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE);
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos pos, CollisionContext context){
        HardpointType hardpointType = blockState.getValue(TYPE);
        switch (hardpointType) {
            case BOTTOM:
                return BOTTOM;
            case TOP:
                return TOP;
            default:
                return BOTTOM;
        }
    }
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState1 = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(TYPE, HardpointType.BOTTOM);
        Direction direction = context.getClickedFace();
        return direction != Direction.DOWN && (direction == Direction.UP || !(context.getClickLocation().y - (double)blockPos.getY() > 0.5D)) ? blockState1: blockState1.setValue(TYPE,  HardpointType.TOP);
    }
}
