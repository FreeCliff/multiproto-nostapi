package me.howard.multiprotonostapi.mixin.mojangfix;

import me.howard.multiprotonostapi.mixinterface.MultiprotoServerData;
import me.howard.multiprotonostapi.protocol.ProtocolVersion;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import pl.js6pak.mojangfix.client.gui.multiplayer.ServerData;

@Mixin(ServerData.class)
public class MixinServerData implements MultiprotoServerData
{
    @Unique private ProtocolVersion multiproto_version;

    @Redirect(method = "load", at = @At(value = "NEW", target = "Lpl/js6pak/mojangfix/client/gui/multiplayer/ServerData;", remap = false), remap = false)
    private static ServerData load(NbtCompound nbt)
    {
        return MultiprotoServerData.create(nbt);
    }

    @Inject(method = "save()Lnet/minecraft/nbt/NbtCompound;", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void save(CallbackInfoReturnable<NbtCompound> cir, NbtCompound nbt)
    {
        nbt.putString("version", multiproto_version.toString());
    }

    @Override
    public ProtocolVersion multiproto_version()
    {
        return multiproto_version;
    }

    @Override
    public void multiproto_setVersion(ProtocolVersion version)
    {
        this.multiproto_version = version;
    }
}
