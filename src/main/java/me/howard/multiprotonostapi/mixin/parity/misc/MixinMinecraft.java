package me.howard.multiprotonostapi.mixin.parity.misc;

import me.howard.multiprotonostapi.protocol.ProtocolVersion;
import me.howard.multiprotonostapi.protocol.ProtocolVersionManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft
{
    @Inject(method = "method_2120", at = @At("HEAD"))
    private void joinSinglePlayerWorld(CallbackInfo ci)
    {
        ProtocolVersionManager.setVersion(ProtocolVersion.BETA_14);
    }
}
