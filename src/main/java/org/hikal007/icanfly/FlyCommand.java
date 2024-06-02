package org.hikal007.icanfly;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class FlyCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("fly")
                // 无参数时切换飞行模式
                .executes(context -> {
                    ServerCommandSource source = context.getSource();
                    ServerPlayerEntity player = source.getPlayer();

                    if (player != null) {
                        boolean currentlyFlying = player.getAbilities().allowFlying;
                        boolean newFlyState = !currentlyFlying;

                        player.getAbilities().allowFlying = newFlyState;
                        player.getAbilities().flying = newFlyState; // 同时更新飞行状态
                        player.sendAbilitiesUpdate();
                        player.sendMessage(Text.literal("Fly mode " + (newFlyState ? "enabled" : "disabled")), false);
                    }
                    return 1;
                })
                // 有参数时根据参数设置飞行模式
                .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                        .executes(context -> {
                            ServerCommandSource source = context.getSource();
                            ServerPlayerEntity player = source.getPlayer();
                            boolean enabled = BoolArgumentType.getBool(context, "enabled");

                            if (player != null) {
                                player.getAbilities().allowFlying = enabled;
                                player.getAbilities().flying = enabled; // 如果禁用飞行，同时也要关闭飞行状态
                                player.sendAbilitiesUpdate();
                                player.sendMessage(Text.literal("Fly mode " + (enabled ? "enabled" : "disabled")), false);
                            }
                            return 1;
                        })
                )
        );
    }
}
