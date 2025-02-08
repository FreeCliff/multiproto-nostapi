package me.howard.multiprotonostapi.mixin.network.packet.play;

import me.howard.multiprotonostapi.protocol.ProtocolVersion;
import me.howard.multiprotonostapi.protocol.ProtocolVersionManager;
import net.minecraft.network.packet.play.PlayerRespawnPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.DataInputStream;
import java.io.DataOutputStream;

@Mixin(PlayerRespawnPacket.class)
public class MixinPlayerRespawnPacket
{
    @Inject(method = "read", at = @At("HEAD"), cancellable = true)
    private void cancelRead(DataInputStream stream, CallbackInfo ci)
    {
        if (ProtocolVersionManager.isBefore(ProtocolVersion.BETA_13)) ci.cancel();
    }

    @Inject(method = "write", at = @At("HEAD"), cancellable = true)
    private void cancelWrite(DataOutputStream stream, CallbackInfo ci)
    {
        if (ProtocolVersionManager.isBefore(ProtocolVersion.BETA_13)) ci.cancel();
    }

    @Inject(method = "size", at = @At("HEAD"), cancellable = true)
    private void size(CallbackInfoReturnable<Integer> cir)
    {
        if (ProtocolVersionManager.isBefore(ProtocolVersion.BETA_13)) cir.setReturnValue(0);
    }
}
