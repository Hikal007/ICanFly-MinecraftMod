package org.hikal007.icanfly;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.server.network.ServerPlayerEntity;
import org.hikal007.icanfly.bridge.ICanFlyPlayerBridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ICanFly implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(ICanFly.class);

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> FlyCommand.register(dispatcher));
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            boolean flying = ((ICanFlyPlayerBridge) player).iCanFly$isFlying();
            if (flying) {
                PlayerAbilities abilities = player.getAbilities();
                abilities.allowFlying = true;
                abilities.flying = true;
                player.sendAbilitiesUpdate();
            }
        });
        LOGGER.info("ICanFlyMOD initialized!");
    }
}