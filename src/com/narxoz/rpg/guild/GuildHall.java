package com.narxoz.rpg.guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Topic-based mediator for the Adventurers' Guild war council.
 */
public class GuildHall implements GuildMediator {

    private final Map<String, List<GuildMember>> membersByTopic = new HashMap<>();

    private int messagesRouted = 0;
    private int membersNotified = 0;
    private int lastDispatchNotified = 0;

    @Override
    public void register(GuildMember member) {
        if (member instanceof Quartermaster) {
            addSubscriber("orders", member);
            addSubscriber("healing", member);
            addSubscriber("rewards", member);
            addSubscriber("urgent", member);
        } else if (member instanceof Scout) {
            addSubscriber("orders", member);
            addSubscriber("supplies", member);
            addSubscriber("urgent", member);
        } else if (member instanceof Healer) {
            addSubscriber("orders", member);
            addSubscriber("scouting", member);
            addSubscriber("urgent", member);
        } else if (member instanceof Captain) {
            addSubscriber("supplies", member);
            addSubscriber("scouting", member);
            addSubscriber("healing", member);
            addSubscriber("urgent", member);
        }
        System.out.println("[GuildHall] Registered " + member.getName()
                + " (" + member.getClass().getSimpleName() + ")");
    }

    @Override
    public void dispatch(String topic, GuildMember from, String payload) {
        messagesRouted++;
        lastDispatchNotified = 0;
        String senderName = from == null ? "system" : from.getName();
        System.out.println("[GuildHall] " + senderName + " -> '" + topic + "': " + payload);
        for (GuildMember subscriber : subscribersFor(topic)) {
            if (subscriber == from) {
                continue;
            }
            subscriber.receive(topic, from, payload);
            membersNotified++;
            lastDispatchNotified++;
        }
    }

    public int getMessagesRouted() {
        return messagesRouted;
    }

    public int getMembersNotified() {
        return membersNotified;
    }

    public int getLastDispatchNotified() {
        return lastDispatchNotified;
    }

    protected void addSubscriber(String topic, GuildMember member) {
        membersByTopic.computeIfAbsent(topic, key -> new ArrayList<>()).add(member);
    }

    protected List<GuildMember> subscribersFor(String topic) {
        return membersByTopic.getOrDefault(topic, List.of());
    }
}
