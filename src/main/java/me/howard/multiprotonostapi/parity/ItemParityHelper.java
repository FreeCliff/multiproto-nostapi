package me.howard.multiprotonostapi.parity;

import me.howard.multiprotonostapi.MultiprotoNoStAPI;
import me.howard.multiprotonostapi.mixinterface.SwordItemAccessor;
import me.howard.multiprotonostapi.mixin.parity.item.ToolItemAccessor;
import me.howard.multiprotonostapi.protocol.ProtocolVersion;
import me.howard.multiprotonostapi.protocol.ProtocolVersionManager;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;

public final class ItemParityHelper
{
    public static void applyParity()
    {
        for (Item item : Item.ITEMS)
        {
            if (item instanceof ToolItem tool)
            {
                ToolMaterial material = ((ToolItemAccessor) tool).getToolMaterial();
                tool.setMaxDamage((ProtocolVersionManager.isBefore(ProtocolVersion.BETA_8) ?
                        (32 << material.getMiningLevel()) * (material.getMiningLevel() == 3 ? 4 : 1) : material.getDurability()));
                ((ToolItemAccessor) tool).setMiningSpeed(ProtocolVersionManager.isBefore(ProtocolVersion.BETA_8) ?
                        (material.getMiningLevel() + 1) * 2 : material.getMiningSpeedMultiplier());
            } else if (item instanceof SwordItem sword)
            {
                ToolMaterial material = ((SwordItemAccessor) sword).multiproto_toolMaterial();
                sword.setMaxDamage((ProtocolVersionManager.isBefore(ProtocolVersion.BETA_8) ?
                        (32 << material.getMiningLevel()) * (material.getMiningLevel() == 3 ? 4 : 1) : material.getDurability()));
            }
        }
        MultiprotoNoStAPI.LOGGER.info("Applied version item parity");
    }
}
