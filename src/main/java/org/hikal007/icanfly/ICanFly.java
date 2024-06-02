package org.hikal007.icanfly;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ICanFly implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> FlyCommand.register(dispatcher));
        System.out.println("ICanFly initialized!");
    }
}