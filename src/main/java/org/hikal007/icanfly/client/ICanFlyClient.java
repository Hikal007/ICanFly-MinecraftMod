package org.hikal007.icanfly.client;

import net.fabricmc.api.ClientModInitializer;

public class ICanFlyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("ICanFlyClient initialized.");
    }
}