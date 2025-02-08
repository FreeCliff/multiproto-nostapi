package me.howard.multiprotonostapi.mixin.network.packet;

import me.howard.multiprotonostapi.protocol.ProtocolVersion;
import me.howard.multiprotonostapi.protocol.ProtocolVersionManager;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Mixin(Packet.class)
public class MixinPacket
{
    @Inject(method = "readString", at = @At("HEAD"), cancellable = true)
    private static void readString$Head(DataInputStream stream, int maxLength, CallbackInfoReturnable<String> cir) throws IOException
    {
        if (ProtocolVersionManager.isBefore(ProtocolVersion.BETA_11))
        {
            String s = stream.readUTF();
            if (s.length() > maxLength)
                throw new IOException("Received string length longer than maximum allowed (" + s.length() + " > " + maxLength + ")");
            cir.setReturnValue(s);
        }
    }

    @Inject(method = "writeString", at = @At(value = "INVOKE", target = "Ljava/io/DataOutputStream;writeShort(I)V"), cancellable = true)
    private static void writeString$writeShort(String string, DataOutputStream stream, CallbackInfo ci) throws IOException
    {
        if (ProtocolVersionManager.isBefore(ProtocolVersion.BETA_11))
        {
            stream.writeUTF(string);
            ci.cancel();
        }
    }
}
