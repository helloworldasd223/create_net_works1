package com.createnetworks.content.router;

import com.createnetworks.content.devices.PcCaseBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class NetworkDeviceBlock extends Block implements EntityBlock {
    private final BiFunction<BlockPos, BlockState, BlockEntity> beFactory;

    public NetworkDeviceBlock(Properties properties, BiFunction<BlockPos, BlockState, BlockEntity> beFactory) {
        super(properties);
        this.beFactory = beFactory;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return beFactory.apply(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) return null;
        return (lvl, pos, st, be) -> {
            if (be instanceof AbstractNetworkBlockEntity netBe) {
                netBe.serverTick();
            }
        };
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return ItemInteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof PcCaseBlockEntity pc) {
            if (!stack.isEmpty() && pc.tryInstallComponent(stack)) {
                if (!player.isCreative()) stack.shrink(1);
                player.displayClientMessage(Component.literal("Installed component! " + pc.getAssemblyStatus())
                        .withStyle(ChatFormatting.GREEN), true);
                return ItemInteractionResult.CONSUME;
            }
            player.displayClientMessage(Component.literal("PC Status: " + pc.getAssemblyStatus()
                    + (pc.isFullyAssembled() ? " [READY]" : " [INCOMPLETE]"))
                    .withStyle(pc.isFullyAssembled() ? ChatFormatting.AQUA : ChatFormatting.YELLOW), true);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
