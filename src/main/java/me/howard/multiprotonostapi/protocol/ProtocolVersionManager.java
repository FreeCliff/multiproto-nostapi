package me.howard.multiprotonostapi.protocol;

import me.howard.multiprotonostapi.MultiprotoNoStAPI;
import me.howard.multiprotonostapi.parity.BlockParityHelper;
import me.howard.multiprotonostapi.parity.ItemParityHelper;
import me.howard.multiprotonostapi.parity.RecipeParityHelper;
import net.minecraft.client.Minecraft;

import java.io.*;

public final class ProtocolVersionManager
{
    private static ProtocolVersion version = ProtocolVersion.BETA_14;
    private static ProtocolVersion lastVersion;
    private static final File lastVersionFile = new File(Minecraft.getRunDirectory(), "multiproto-lastversion.txt");

    public static ProtocolVersion version()
    {
        return version;
    }

    public static void setVersion(ProtocolVersion newVersion)
    {
        if (version != newVersion)
        {
            version = newVersion;
            BlockParityHelper.applyParity();
            ItemParityHelper.applyParity();
            RecipeParityHelper.applyParity();
        }
    }

    public static boolean isBefore(ProtocolVersion target)
    {
        return version.isBefore(target);
    }

    public static ProtocolVersion lastVersion()
    {
        if (lastVersion == null)
        {
            lastVersion = ProtocolVersion.BETA_14;

            if (lastVersionFile.exists())
            {
                try
                {
                    BufferedReader br = new BufferedReader(new FileReader(lastVersionFile));
                    String s = br.readLine();
                    br.close();
                    lastVersion = ProtocolVersion.fromString(s);
                } catch (Exception e)
                {
                    MultiprotoNoStAPI.LOGGER.error("Unknown error loading last protocol version", e);
                }
            }
        }
        return lastVersion;
    }

    public static void setLastVersion(ProtocolVersion newLastVersion)
    {
        if (lastVersion != newLastVersion)
        {
            try
            {
                if (!lastVersionFile.exists()) lastVersionFile.createNewFile();
                PrintWriter pw = new PrintWriter(new FileWriter(lastVersionFile));
                pw.print(lastVersion = newLastVersion);
                pw.close();
            } catch (Exception e)
            {
                MultiprotoNoStAPI.LOGGER.error("Error writing last protocol version to text file", e);
            }
        }
    }
}
