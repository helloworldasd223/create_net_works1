package com.createnetworks.content.devices;

import com.createnetworks.network.NetworkNode;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NetworkTesterItem extends Item {
    public NetworkTesterItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = context.getLevel().getBlockEntity(context.getClickedPos());
        Player player = context.getPlayer();
        if (be instanceof NetworkNode node && player != null) {
            player.displayClientMessage(Component.literal(
                    String.format("[NetTester] IP: %s | Powered: %s | Links: %d",
                            node.getIpAddress(), node.isPowered(), node.getLinks().size()))
                    .withStyle(node.isPowered() ? ChatFormatting.GREEN : ChatFormatting.RED), true);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
