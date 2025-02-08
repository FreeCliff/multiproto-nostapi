package me.howard.multiprotonostapi.mixin.parity.item;

import me.howard.multiprotonostapi.mixinterface.SwordItemAccessor;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SwordItem.class)
public class MixinSwordItem implements SwordItemAccessor
{
    @Unique private ToolMaterial toolMaterial;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void saveToolMaterial(int id, ToolMaterial toolMaterial, CallbackInfo ci)
    {
        this.toolMaterial = toolMaterial;
    }

    @Override
    public ToolMaterial multiproto_toolMaterial()
    {
        return this.toolMaterial;
    }
}
