package com.narxoz.rpg.guild;

/**
 * Guild officer responsible for arcane lore, curses, and guild history.
 *
 * <p>Open/closed proof: existing colleagues hold no direct reference
 * to Loremaster — communication still flows through {@link GuildHall}.
 */
public class Loremaster extends GuildMember {

    public Loremaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void shareLore(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = from == null ? "system" : from.getName();
        switch (topic) {
            case "lore":
                System.out.println("    " + getName() + " (Loremaster): cross-referencing tomes for "
                        + sender + " - \"" + payload + "\"");
                break;
            case "curse":
                System.out.println("    " + getName() + " (Loremaster): preparing dispel ritual for "
                        + sender + " - \"" + payload + "\"");
                break;
            case "history":
                System.out.println("    " + getName() + " (Loremaster): recalling guild chronicle for "
                        + sender + " - \"" + payload + "\"");
                break;
            case "urgent":
                System.out.println("    " + getName() + " (Loremaster): scanning forbidden archives ["
                        + sender + "] - \"" + payload + "\"");
                break;
            default:
                System.out.println("    " + getName() + " (Loremaster): noted '" + topic + "' from "
                        + sender);
                break;
        }
    }
}
