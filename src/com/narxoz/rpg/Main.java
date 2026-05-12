package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.Captain;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.Healer;
import com.narxoz.rpg.guild.Quartermaster;
import com.narxoz.rpg.guild.Scout;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;
import java.util.List;

/**
 * Entry point for Homework 10 — The Adventurers' Guild: Iterator + Mediator.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===");

        Hero arin = new Hero("Arin", 80, 12, 4);
        Hero brina = new Hero("Brina", 60, 18, 2);
        List<Hero> party = List.of(arin, brina);

        QuestLog log = new QuestLog();
        log.add(new Quest("Gather Herbs", QuestPriority.LOW, 20, false));
        log.add(new Quest("Clear Goblin Camp", QuestPriority.NORMAL, 50, false));
        log.add(new Quest("Rescue Merchant", QuestPriority.HIGH, 120, false));
        log.add(new Quest("Slay Forest Troll", QuestPriority.HIGH, 200, false));
        log.add(new Quest("Investigate Cult Ritual", QuestPriority.URGENT, 250, true));
        log.add(new Quest("Stop Hex Storm", QuestPriority.URGENT, 300, true));

        GuildHall hall = new GuildHall();
        new Quartermaster("Borin", hall);
        new Scout("Lirien", hall);
        new Healer("Maelis", hall);
        new Captain("Thane", hall);

        System.out.println();
        System.out.println("Reverse roll call (newest quest first):");
        QuestIterator rev = log.reverse();
        while (rev.hasNext()) {
            Quest quest = rev.next();
            System.out.println("  - " + quest.getTitle() + " [" + quest.getPriority() + "]");
        }

        System.out.println();
        System.out.println("Pre-council briefing:");
        hall.dispatch("orders", null, "All officers report status");

        CouncilEngine engine = new CouncilEngine();
        CouncilRunResult result = engine.runCouncil(party, log, hall);

        System.out.println();
        System.out.println("Final " + result);
    }
}
