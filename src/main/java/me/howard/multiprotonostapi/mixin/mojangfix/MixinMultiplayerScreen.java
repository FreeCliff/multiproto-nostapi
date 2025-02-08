package me.howard.multiprotonostapi.mixin.mojangfix;

import me.howard.multiprotonostapi.mixinterface.MultiprotoServerData;
import me.howard.multiprotonostapi.protocol.ProtocolVersionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.js6pak.mojangfix.client.gui.multiplayer.MultiplayerScreen;
import pl.js6pak.mojangfix.client.gui.multiplayer.ServerData;

@Mixin(MultiplayerScreen.class)
public class MixinMultiplayerScreen
{
    @Inject(method = "joinServer", at = @At(value = "INVOKE", target = "Lpl/js6pak/mojangfix/client/gui/multiplayer/DirectConnectScreen;connect(Lnet/minecraft/client/Minecraft;Ljava/lang/String;)V"), remap = false)
    private void setVersionOnConnect(ServerData server, CallbackInfo ci)
    {
        ProtocolVersionManager.setVersion(((MultiprotoServerData) server).multiproto_version());
    }
}
