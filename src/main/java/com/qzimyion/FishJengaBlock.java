package com.qzimyion;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

@SuppressWarnings("deprecation")
public class FishJengaBlock extends Block implements Waterloggable {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final IntProperty FISH = IntProperty.of("fish", 1, 8);
    private static final VoxelShape ONE_FISH;
    private static final VoxelShape TWO_FISH;
    private static final VoxelShape THREE_FISH;
    private static final VoxelShape FOUR_FISH;
    private static final VoxelShape FIVE_FISH;
    private static final VoxelShape SIX_FISH;
    private static final VoxelShape SEVEN_FISH;

    public FishJengaBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(FISH, 1).with(WATERLOGGED, false).with(FACING, Direction.NORTH));
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(FISH) < 8) {
            return true;
        }
        return super.canReplace(state, context);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FISH)) {
            default -> ONE_FISH;
            case 2 -> TWO_FISH;
            case 3 -> THREE_FISH;
            case 4 -> FOUR_FISH;
            case 5 -> FIVE_FISH;
            case 6 -> SIX_FISH;
            case 7 -> SEVEN_FISH;
            case 8 -> VoxelShapes.fullCube();
        };
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return !floor.getCollisionShape(world, pos).getFace(Direction.UP).isEmpty() || floor.isSideSolidFullSquare(world, pos, Direction.UP);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.with(FISH, Math.min(8, blockState.get(FISH) + 1));
        }
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, FISH, WATERLOGGED);
    }

    static {
        ONE_FISH = Block.createCuboidShape(5, 0, 0, 11, 4, 16);
        TWO_FISH = Block.createCuboidShape(0, 0, 0, 16, 4, 16);
        THREE_FISH = VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 4, 16), Block.createCuboidShape(4, 4, 0, 12, 8, 16));
        FOUR_FISH = Block.createCuboidShape(0, 0, 0, 16, 8, 16);
        FIVE_FISH = VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 8, 16), Block.createCuboidShape(4, 8, 0, 12, 12, 16));
        SIX_FISH = Block.createCuboidShape(0, 0, 0, 16, 12, 16);
        SEVEN_FISH = VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 12, 16), Block.createCuboidShape(4, 12, 0, 12, 16, 16));

    }
}
