package com.narxoz.rpg.guild;

/**
 * Guild officer responsible for wounds, potions, and recovery plans.
 */
public class Healer extends GuildMember {

    public Healer(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void prepareAid(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = from == null ? "system" : from.getName();
        switch (topic) {
            case "orders":
                System.out.println("    " + getName() + " (Healer): readying triage kit for order from "
                        + sender + " - \"" + payload + "\"");
                break;
            case "scouting":
                System.out.println("    " + getName() + " (Healer): pre-mixing antidotes for hazards spotted by "
                        + sender + " - \"" + payload + "\"");
                break;
            case "urgent":
                System.out.println("    " + getName() + " (Healer): RUSHING field aid [" + sender
                        + "] - \"" + payload + "\"");
                break;
            default:
                System.out.println("    " + getName() + " (Healer): noted '" + topic + "' from "
                        + sender);
                break;
        }
    }
}
