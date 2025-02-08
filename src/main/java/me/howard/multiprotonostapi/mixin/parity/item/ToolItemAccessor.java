package me.howard.multiprotonostapi.mixin.parity.item;

import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ToolItem.class)
public interface ToolItemAccessor
{
    @Accessor void setMiningSpeed(float miningSpeed);
    @Accessor ToolMaterial getToolMaterial();
}
