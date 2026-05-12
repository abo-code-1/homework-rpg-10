package com.narxoz.rpg.guild;

/**
 * Guild officer responsible for route reports and reconnaissance.
 */
public class Scout extends GuildMember {

    public Scout(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void reportRoute(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = from == null ? "system" : from.getName();
        switch (topic) {
            case "orders":
                System.out.println("    " + getName() + " (Scout): plotting route for order from "
                        + sender + " - \"" + payload + "\"");
                break;
            case "supplies":
                System.out.println("    " + getName() + " (Scout): marking safe path for supply run from "
                        + sender + " - \"" + payload + "\"");
                break;
            case "urgent":
                System.out.println("    " + getName() + " (Scout): SPRINTING ahead to scout danger ["
                        + sender + "] - \"" + payload + "\"");
                break;
            default:
                System.out.println("    " + getName() + " (Scout): noted '" + topic + "' from "
                        + sender);
                break;
        }
    }
}
