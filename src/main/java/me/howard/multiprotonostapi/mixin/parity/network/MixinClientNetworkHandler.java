package me.howard.multiprotonostapi.mixin.parity.network;

import me.howard.multiprotonostapi.protocol.ProtocolVersion;
import me.howard.multiprotonostapi.protocol.ProtocolVersionManager;
import net.minecraft.client.network.ClientNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.play.EntityAnimationPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientNetworkHandler.class)
public abstract class MixinClientNetworkHandler
{
    @Shadow protected abstract Entity method_1645(int i);

    @Inject(method = "onEntityAnimation", at = @At("TAIL"))
    private void applySneakingParity(EntityAnimationPacket packet, CallbackInfo ci)
    {
        Entity entity = this.method_1645(packet.id);
        if (entity != null && ProtocolVersionManager.isBefore(ProtocolVersion.BETA_8))
        {
            if (packet.animationId == 104) entity.setFlag(1, true);
            if (packet.animationId == 105) entity.setFlag(1, false);
        }
    }
}
