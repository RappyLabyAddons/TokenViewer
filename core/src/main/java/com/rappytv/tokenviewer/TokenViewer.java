package com.rappytv.tokenviewer;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class TokenViewer extends LabyAddon<TokenViewerConfig> {

    public static final Component prefix = Component.empty()
        .append(Component.text("TV", Style.empty().color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD)))
        .append(Component.text(" » ", NamedTextColor.DARK_GRAY));

    @Override
    protected void enable() {
        registerSettingCategory();
        registerCommand(new TokenViewerCommand());
    }

    @Override
    protected Class<? extends TokenViewerConfig> configurationClass() {
        return TokenViewerConfig.class;
    }
}
