package me.howard.multiprotonostapi.parity;

import me.howard.multiprotonostapi.MultiprotoNoStAPI;
import me.howard.multiprotonostapi.protocol.ProtocolVersion;
import me.howard.multiprotonostapi.protocol.ProtocolVersionManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.CraftingRecipeManager;
import net.minecraft.recipe.SmeltingRecipeManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RecipeParityHelper
{
    public static List vanillaCraftingRecipes = new ArrayList();
    public static Map vanillaSmeltingRecipes = new HashMap();

    public static void applyParity()
    {
        CraftingRecipeManager.getInstance().getRecipes().clear();
        CraftingRecipeManager.getInstance().getRecipes().addAll(vanillaCraftingRecipes);
        SmeltingRecipeManager.getInstance().getRecipes().clear();
        SmeltingRecipeManager.getInstance().getRecipes().putAll(vanillaSmeltingRecipes);

        // < b1.7
        removeBefore(ProtocolVersion.BETA_14, new ItemStack(Block.PISTON), new ItemStack(Block.STICKY_PISTON), new ItemStack(Item.SHEARS));

        // < b1.6
        removeBefore(ProtocolVersion.BETA_13, new ItemStack(Block.TRAPDOOR, 2), new ItemStack(Item.MAP, 1));

        // < b1.5
        removeBefore(ProtocolVersion.BETA_11, new ItemStack(Block.POWERED_RAIL, 6), new ItemStack(Block.DETECTOR_RAIL, 6));
        replaceBefore(ProtocolVersion.BETA_11, new ItemStack(Block.LADDER, 2), new ItemStack(Block.LADDER, 1), "# #", "###", "# #", '#', Item.STICK);

        // < b1.3
        removeBefore(ProtocolVersion.BETA_9, new ItemStack(Item.BED), new ItemStack(Item.REPEATER), new ItemStack(Block.SLAB, 3, 3), new ItemStack(Block.SLAB, 3, 2), new ItemStack(Block.SLAB, 3, 1));
        replaceBefore(ProtocolVersion.BETA_9, new ItemStack(Block.SLAB, 3), "###", '#', Block.COBBLESTONE);
        replaceBefore(ProtocolVersion.BETA_9, new ItemStack(Block.STONE_PRESSURE_PLATE), "###", '#', Block.STONE);
        replaceBefore(ProtocolVersion.BETA_9, new ItemStack(Block.WOODEN_PRESSURE_PLATE), "###", '#', Block.PLANKS);

        // < b1.2
        removeBefore(ProtocolVersion.BETA_8, new ItemStack(Item.CAKE), new ItemStack(Block.DISPENSER), new ItemStack(Block.NOTE_BLOCK), new ItemStack(Block.SANDSTONE), new ItemStack(Item.SUGAR));
        for (int i = 0; i < 16; i++)
        {
            removeBefore(ProtocolVersion.BETA_8, new ItemStack(Block.WOOL, 1, ~i & 15), new ItemStack(Item.DYE, 1, i));
        }
        if (ProtocolVersionManager.isBefore(ProtocolVersion.BETA_8))
        {
            SmeltingRecipeManager.getInstance().getRecipes().remove(Block.CACTUS.id);
            SmeltingRecipeManager.getInstance().getRecipes().remove(Block.LOG.id);
        }
        MultiprotoNoStAPI.LOGGER.info("Applied version recipe parity");
    }

    private static void replaceBefore(ProtocolVersion target, ItemStack output, Object... recipe)
    {
        replaceBefore(target, output, output, recipe);
    }

    private static void replaceBefore(ProtocolVersion target, ItemStack oldOutput, ItemStack output, Object... recipe)
    {
        removeBefore(target, oldOutput);
        CraftingRecipeManager.getInstance().addShapedRecipe(output, recipe);
    }

    private static void removeBefore(ProtocolVersion target, ItemStack... outputs)
    {
        if (ProtocolVersionManager.isBefore(target))
        {
            for (ItemStack output : outputs)
            {
                CraftingRecipeManager.getInstance().getRecipes().removeIf(r -> ((CraftingRecipe) r).getOutput().equals(output));
            }
        }
    }
}
