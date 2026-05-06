package com.narxoz.rpg.guild;

/**
 * Guild officer responsible for gear, supplies, and rewards.
 */
public class Quartermaster extends GuildMember {

    public Quartermaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void requestSupplies(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        String sender = from == null ? "system" : from.getName();
        switch (topic) {
            case "orders":
                System.out.println("    " + getName() + " (Quartermaster): packing gear for order from "
                        + sender + " - \"" + payload + "\"");
                break;
            case "healing":
                System.out.println("    " + getName() + " (Quartermaster): pulling potions and bandages for "
                        + sender + " - \"" + payload + "\"");
                break;
            case "rewards":
                System.out.println("    " + getName() + " (Quartermaster): logging reward bundle from "
                        + sender + " - \"" + payload + "\"");
                break;
            case "urgent":
                System.out.println("    " + getName() + " (Quartermaster): EMERGENCY supplies queued ["
                        + sender + "] - \"" + payload + "\"");
                break;
            default:
                System.out.println("    " + getName() + " (Quartermaster): noted '" + topic + "' from "
                        + sender);
                break;
        }
    }
}
