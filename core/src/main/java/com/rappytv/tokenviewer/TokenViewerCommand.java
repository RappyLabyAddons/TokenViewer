package com.rappytv.tokenviewer;

import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.session.Session;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.TokenStorage.Purpose;
import net.labymod.api.util.I18n;

public class TokenViewerCommand extends Command {

    protected TokenViewerCommand() {
        super("token", "tv");
    }

    @Override
    public boolean execute(String prefix, String[] args) {
        String accessToken = null;
        String labyConnectClient = null;
        String labyConnectJWT = null;

        Session session = labyAPI.minecraft().sessionAccessor().getSession();
        if(session != null && session.getAccessToken() != null && !session.getAccessToken().equals("0")) accessToken = session.getAccessToken();

        LabyConnectSession labyConnectSession = labyAPI.labyConnect().getSession();
        if(labyConnectSession != null) {
            if(labyConnectSession.tokenStorage().getToken(Purpose.CLIENT, labyAPI.getUniqueId()) != null)
                labyConnectClient = labyConnectSession.tokenStorage()
                    .getToken(Purpose.CLIENT, labyAPI.getUniqueId())
                    .getToken();
            if(labyConnectSession.tokenStorage().getToken(Purpose.JWT, labyAPI.getUniqueId()) != null)
                labyConnectJWT = labyConnectSession.tokenStorage()
                    .getToken(Purpose.JWT, labyAPI.getUniqueId())
                    .getToken();
        }

        Component message = Component.empty()
            .append(TokenViewer.prefix)
            .append(Component.text("Minecraft Access Token: ", NamedTextColor.WHITE))
            .append(Component.text(
                String.format("<%s>\n", I18n.translate("tokenviewer.component." + (accessToken != null ? "copy" : "null"))),
                getStyle(accessToken, NamedTextColor.DARK_GREEN)
            ))
            .append(TokenViewer.prefix)
            .append(Component.text("LabyConnect Client Token: ", NamedTextColor.WHITE))
            .append(Component.text(
                String.format("<%s>\n", I18n.translate("tokenviewer.component." + (labyConnectClient != null ? "copy" : "null"))),
                getStyle(labyConnectClient, NamedTextColor.BLUE)
            ))
            .append(TokenViewer.prefix)
            .append(Component.text("LabyConnect JWT Token: ", NamedTextColor.WHITE))
            .append(Component.text(
                String.format("<%s>", I18n.translate("tokenviewer.component." + (labyConnectJWT != null ? "copy" : "null"))),
                getStyle(labyConnectJWT, NamedTextColor.AQUA)
            ));

        displayMessage(message);
        return true;
    }

    private Style getStyle(String token, TextColor color) {
        return Style.empty()
            .color(token != null ? color : NamedTextColor.DARK_GRAY)
            .decorate(TextDecoration.BOLD)
            .hoverEvent(token != null ? HoverEvent.showText(Component.translatable("tokenviewer.component.hover")) : null)
            .clickEvent(token != null ? ClickEvent.copyToClipboard(token) : null);
    }
}
