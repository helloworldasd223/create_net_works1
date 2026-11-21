package com.createnetworks.content.devices;

import com.createnetworks.network.NetworkManager;
import com.createnetworks.network.RadioPropagation;
import com.createnetworks.registry.CNDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SmartphoneItem extends Item {
    public SmartphoneItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            int battery = stack.getOrDefault(CNDataComponents.BATTERY_LEVEL.get(), 100);
            if (battery <= 0) {
                player.displayClientMessage(Component.literal("Smartphone battery is empty! Place on Charging Dock.")
                        .withStyle(ChatFormatting.RED), true);
                return InteractionResultHolder.fail(stack);
            }

            // Simulate cellular/Wi-Fi scan
            BlockPos playerPos = player.blockPosition();
            double rssi = RadioPropagation.calculateRssiDbm(level, playerPos.offset(12, 20, -8), playerPos, 43.0, 3.5);
            int bars = RadioPropagation.rssiToBars(rssi);
            String ip = stack.getOrDefault(CNDataComponents.IP_ADDRESS.get(), "10.0.0.42");
            String sim = stack.getOrDefault(CNDataComponents.SIM_NUMBER.get(), "NO SIM");

            // Drain 1% battery per active scan
            stack.set(CNDataComponents.BATTERY_LEVEL.get(), Math.max(0, battery - 1));

            player.displayClientMessage(
                    Component.literal(String.format("5G [%d/4 bars | %.1f dBm] | IP: %s | SIM: %s | Bat: %d%%",
                            bars, rssi, ip, sim, battery - 1))
                            .withStyle(bars > 0 ? ChatFormatting.GREEN : ChatFormatting.YELLOW),
                    true
            );
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        int battery = stack.getOrDefault(CNDataComponents.BATTERY_LEVEL.get(), 100);
        String ip = stack.getOrDefault(CNDataComponents.IP_ADDRESS.get(), "Unassigned");
        tooltip.add(Component.literal("Battery: " + battery + "%").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("IP: " + ip).withStyle(ChatFormatting.DARK_AQUA));
    }
}
