package net.redesky.skins.bungee.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class JsonMessage {
   public static void send(ProxiedPlayer target, String rawmsg) {
      TextComponent component = new TextComponent("");
      String msg = rawmsg.replace("&", "§");
      String[] rawtext;
      int var6;
      int var13;
      if (!msg.contains("/-/")) {
         TextComponent action;
         if (msg.contains(": ttp>")) {
            rawtext = msg.split(" : ");
            action = new TextComponent(rawtext[0]);
            if (msg.contains(": exe>")) {
               action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
               action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, rawtext[2].replace("exe>", "")));
               component.addExtra(action);
            } else if (msg.contains(": sgt>")) {
               action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
               action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, rawtext[2].replace("sgt>", "")));
               component.addExtra(action);
            } else if (msg.contains(": url>")) {
               action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
               action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, rawtext[2].replace("url>", "")));
               component.addExtra(action);
            } else {
               action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
               component.addExtra(action);
            }
         } else if (msg.contains(": exe>")) {
            rawtext = msg.split(" : ");
            action = new TextComponent(rawtext[0]);
            action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, rawtext[1].replace("exe>", "")));
            component.addExtra(action);
         } else if (msg.contains(": sgt>")) {
            rawtext = msg.split(" : ");
            action = new TextComponent(rawtext[0]);
            action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, rawtext[1].replace("sgt>", "")));
            component.addExtra(action);
         } else if (msg.contains(": url>")) {
            rawtext = msg.split(" : ");
            action = new TextComponent(rawtext[0]);
            action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, rawtext[1].replace("url>", "")));
            component.addExtra(action);
         } else {
            BaseComponent[] var12 = TextComponent.fromLegacyText(msg);
            var13 = var12.length;

            for(var6 = 0; var6 < var13; ++var6) {
               BaseComponent components = var12[var6];
               component.addExtra(components);
            }
         }
      } else {
         rawtext = msg.split("/-/");
         var13 = rawtext.length;

         for(var6 = 0; var6 < var13; ++var6) {
            String message = rawtext[var6];
            String[] rawtext;
            TextComponent action;
            if (message.contains(": ttp>")) {
               rawtext = message.split(" : ");
               action = new TextComponent(rawtext[0]);
               if (message.contains(": exe>")) {
                  action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                  action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, rawtext[2].replace("exe>", "")));
                  component.addExtra(action);
               } else if (message.contains(": sgt>")) {
                  action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                  action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, rawtext[2].replace("sgt>", "")));
                  component.addExtra(action);
               } else if (message.contains(": url>")) {
                  action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                  action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, rawtext[2].replace("url>", "")));
                  component.addExtra(action);
               } else {
                  action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
                  component.addExtra(action);
               }
            } else if (message.contains(": exe>")) {
               rawtext = message.split(" : ");
               action = new TextComponent(rawtext[0]);
               action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, rawtext[1].replace("exe>", "")));
               component.addExtra(action);
            } else if (message.contains(": sgt>")) {
               rawtext = message.split(" : ");
               action = new TextComponent(rawtext[0]);
               action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, rawtext[1].replace("sgt>", "")));
               component.addExtra(action);
            } else if (message.contains(": url>")) {
               rawtext = message.split(" : ");
               action = new TextComponent(rawtext[0]);
               action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, rawtext[1].replace("url>", "")));
               component.addExtra(action);
            } else {
               BaseComponent[] var8 = TextComponent.fromLegacyText(message);
               int var9 = var8.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  BaseComponent components = var8[var10];
                  component.addExtra(components);
               }
            }
         }
      }

      target.sendMessage(component);
   }
}
