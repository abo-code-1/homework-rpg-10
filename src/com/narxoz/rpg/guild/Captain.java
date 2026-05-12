package com.narxoz.rpg.guild;

/**
 * Guild officer responsible for orders and mission coordination.
 */
public class Captain extends GuildMember {

    public Captain(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void issueOrder(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = from == null ? "system" : from.getName();
        switch (topic) {
            case "supplies":
                System.out.println("    " + getName() + " (Captain): approving supply request from "
                        + sender + " - \"" + payload + "\"");
                break;
            case "scouting":
                System.out.println("    " + getName() + " (Captain): adjusting battle plan on intel from "
                        + sender + " - \"" + payload + "\"");
                break;
            case "healing":
                System.out.println("    " + getName() + " (Captain): pulling unit back for aid from "
                        + sender + " - \"" + payload + "\"");
                break;
            case "urgent":
                System.out.println("    " + getName() + " (Captain): SOUNDING general alarm [" + sender
                        + "] - \"" + payload + "\"");
                break;
            default:
                System.out.println("    " + getName() + " (Captain): noted '" + topic + "' from "
                        + sender);
                break;
        }
    }
}
