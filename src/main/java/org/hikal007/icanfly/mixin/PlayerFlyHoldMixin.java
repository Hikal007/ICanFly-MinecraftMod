package org.hikal007.icanfly.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.hikal007.icanfly.bridge.ICanFlyPlayerBridge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class PlayerFlyHoldMixin implements ICanFlyPlayerBridge {
    @Unique
    private boolean flying;

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("ICanFlyPlayerHoldData")) {
            NbtCompound iCanFlyPlayerHoldData = nbt.getCompound("ICanFlyPlayerHoldData");
            flying = iCanFlyPlayerHoldData.getBoolean("Flying");
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        NbtCompound tag = nbt.getCompound("ICanFlyPlayerHoldData");
        tag.putBoolean("Flying", flying);
        nbt.put("ICanFlyPlayerHoldData", tag);
    }

    @Override
    public void iCanFly$setFlying(boolean flying) {
        this.flying = flying;
    }

    @Override
    public boolean iCanFly$isFlying() {
        return flying;
    }
}
