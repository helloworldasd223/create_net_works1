package com.createnetworks.content.cable;

import com.createnetworks.network.NetworkLink;
import com.createnetworks.network.NetworkManager;
import com.createnetworks.network.NetworkNode;
import com.createnetworks.registry.CNDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CableItem extends Item {
    private final NetworkLink.Medium medium;

    public CableItem(Properties properties, NetworkLink.Medium medium) {
        super(properties);
        this.medium = medium;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockPos clickedPos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        BlockEntity be = level.getBlockEntity(clickedPos);

        if (!(be instanceof NetworkNode targetNode)) {
            if (player != null) {
                player.displayClientMessage(Component.literal("Not a network device!").withStyle(ChatFormatting.RED), true);
            }
            return InteractionResult.FAIL;
        }

        BlockPos firstPos = stack.get(CNDataComponents.LINK_TARGET_POS.get());
        if (firstPos == null) {
            stack.set(CNDataComponents.LINK_TARGET_POS.get(), clickedPos);
            if (player != null) {
                player.displayClientMessage(Component.literal("Cable start bound at " + clickedPos.toShortString())
                        .withStyle(ChatFormatting.AQUA), true);
            }
            return InteractionResult.SUCCESS;
        }

        if (firstPos.equals(clickedPos)) {
            stack.remove(CNDataComponents.LINK_TARGET_POS.get());
            if (player != null) {
                player.displayClientMessage(Component.literal("Cable binding cleared.").withStyle(ChatFormatting.GRAY), true);
            }
            return InteractionResult.SUCCESS;
        }

        double dist = Math.sqrt(firstPos.distSqr(clickedPos));
        if (dist > medium.maxRangeBlocks) {
            if (player != null) {
                player.displayClientMessage(Component.literal(
                        String.format("Too far! %.1f blocks (max %d for %s)", dist, medium.maxRangeBlocks, medium.name()))
                        .withStyle(ChatFormatting.RED), true);
            }
            return InteractionResult.FAIL;
        }

        BlockEntity firstBe = level.getBlockEntity(firstPos);
        if (firstBe instanceof NetworkNode firstNode) {
            NetworkLink link = new NetworkLink(firstPos, clickedPos, medium);
            firstNode.getLinks().add(link);
            targetNode.getLinks().add(link);
            stack.remove(CNDataComponents.LINK_TARGET_POS.get());
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            if (player != null) {
                player.displayClientMessage(Component.literal(
                        String.format("Connected %s <-> %s (%.1fm, %d Mbps)",
                                firstPos.toShortString(), clickedPos.toShortString(), dist, medium.maxBandwidthMbps))
                        .withStyle(ChatFormatting.GREEN), true);
            }
            return InteractionResult.SUCCESS;
        }

        stack.remove(CNDataComponents.LINK_TARGET_POS.get());
        return InteractionResult.FAIL;
    }
}
