package me.howard.multiprotonostapi.gui;

import me.howard.multiprotonostapi.mixin.MultiprotoMixinPlugin;
import me.howard.multiprotonostapi.mixin.mojangfix.DirectConnectScreenAccessor;
import me.howard.multiprotonostapi.mixin.mojangfix.EditServerScreenAccessor;
import me.howard.multiprotonostapi.mixinterface.MultiprotoServerData;
import me.howard.multiprotonostapi.protocol.ProtocolVersion;
import me.howard.multiprotonostapi.protocol.ProtocolVersionManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import pl.js6pak.mojangfix.client.gui.multiplayer.DirectConnectScreen;
import pl.js6pak.mojangfix.client.gui.multiplayer.EditServerScreen;
import pl.js6pak.mojangfix.client.gui.multiplayer.ServerData;

import java.util.Comparator;
import java.util.List;

public final class ProtocolVersionScreen extends Screen
{
    private static ProtocolVersion lastServerVersion;
    private final Screen parent;
    private final ServerData server;
    private final List<ProtocolVersion> versions;

    public ProtocolVersionScreen(Screen parent, ServerData server)
    {
        this.parent = parent;
        this.server = server;
        this.versions = ProtocolVersion.PROTOCOL_VERSIONS.stream().sorted(Comparator.reverseOrder()).toList();
    }

    public ProtocolVersionScreen(Screen parent)
    {
        this(parent, null);
    }

    public static ProtocolVersion lastServerVersion()
    {
        return lastServerVersion != null ? lastServerVersion : ProtocolVersionManager.lastVersion();
    }

    @Override
    public void init()
    {
        for (ProtocolVersion version : versions)
        {
            int i = versions.indexOf(version);
            int size = versions.size();
            boolean side = i < size / 2;
            ButtonWidget button = new ButtonWidget(i, side ? width / 2 - 184 : width / 2 + 4,
                    height / 4 - 12 + 24 * (side ? i : i - size / 2),
                    180, 20, version.nameRange(false));
            if (i == size - 1 && i % 2 == 0) button.x = width / 2 - 90;
            buttons.add(button);
        }
        buttons.add(new ButtonWidget(100, width / 2 - 100, ((ButtonWidget) buttons.get(versions.size() - 1)).y + 36,
                I18n.getTranslation("gui.cancel")));
    }

    @Override
    protected void buttonClicked(ButtonWidget button)
    {
        if (button.id == 100)
        {
            minecraft.setScreen(parent);
        } else if (button.id <= versions.size())
        {
            ProtocolVersion version = versions.get(button.id);
            if (MultiprotoMixinPlugin.shouldApplyMojangFixServerListIntegration())
            {
                if (parent instanceof DirectConnectScreen)
                {
                    String address = ((DirectConnectScreenAccessor) parent).getAddressField().getText();
                    boolean active = ((DirectConnectScreenAccessor) parent).getConnectButton().active;
                    ProtocolVersionManager.setLastVersion(version);
                    minecraft.setScreen(parent);
                    ((DirectConnectScreenAccessor) parent).getAddressField().setText(address);
                    ((DirectConnectScreenAccessor) parent).getConnectButton().active = active;
                } else if (parent instanceof EditServerScreen)
                {
                    String name = ((EditServerScreenAccessor) parent).getNameTextField().getText();
                    String address = ((EditServerScreenAccessor) parent).getIpTextField().getText();
                    boolean active = ((EditServerScreenAccessor) parent).getButton().active;
                    lastServerVersion = version;
                    if (server != null) ((MultiprotoServerData) server).multiproto_setVersion(lastServerVersion);
                    minecraft.setScreen(parent);
                    ((EditServerScreenAccessor) parent).getNameTextField().setText(name);
                    ((EditServerScreenAccessor) parent).getIpTextField().setText(address);
                    ((EditServerScreenAccessor) parent).getButton().active = active;
                }
            } else
            {
                ProtocolVersionManager.setLastVersion(version);
                minecraft.setScreen(parent);
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta)
    {
        renderBackground();
        drawCenteredTextWithShadow(textRenderer, "Protocol version", width / 2, 20, 16777215);
        super.render(mouseX, mouseY, delta);
    }
}
