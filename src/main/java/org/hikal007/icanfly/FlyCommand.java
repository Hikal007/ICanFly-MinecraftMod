package org.hikal007.icanfly;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class FlyCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("fly")
                .executes(context -> {
                    ServerCommandSource source = context.getSource();
                    ServerPlayerEntity player = source.getPlayer();

                    if (player != null) {
                        boolean canFly = player.getAbilities().allowFlying;
                        player.getAbilities().allowFlying = !canFly;
                        if (canFly) {
                            player.getAbilities().flying = false;  // 禁用飞行状态
                            player.sendAbilitiesUpdate();  // 更新能力
                            player.setVelocity(player.getVelocity().x, 0, player.getVelocity().z);  // 重置垂直速度
                        } else {
                            player.sendAbilitiesUpdate();  // 更新能力
                        }
                        player.sendMessage(Text.literal("Fly mode " + (!canFly ? "enabled" : "disabled")), false);
                    }
                    return 1;
                }));
    }
}