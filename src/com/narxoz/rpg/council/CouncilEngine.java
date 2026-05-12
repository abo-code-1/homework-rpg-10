package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.GuildMediator;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;
import java.util.List;

/**
 * Orchestrates a planning session that uses both Iterator and Mediator.
 */
public class CouncilEngine {

    public CouncilRunResult runCouncil(List<Hero> party, QuestLog questLog, GuildMediator hall) {
        System.out.println();
        System.out.println("=== Council session begins ===");
        for (Hero hero : party) {
            System.out.println("  Party member: " + hero.getName()
                    + " (HP " + hero.getHp() + ", ATK " + hero.getAttackPower() + ")");
        }

        int messagesBefore = messagesRouted(hall);
        int notifiedBefore = membersNotified(hall);
        int questsTraversed = 0;

        System.out.println();
        System.out.println("-- Pass 1: ordered survey --");
        QuestIterator ordered = questLog.ordered();
        while (ordered.hasNext()) {
            Quest quest = ordered.next();
            questsTraversed++;
            hall.dispatch("supplies", null,
                    "Survey: " + quest.getTitle() + " (priority " + quest.getPriority() + ")");
        }

        System.out.println();
        System.out.println("-- Pass 2: high-priority briefing --");
        QuestIterator priority = questLog.priorityAtLeast(QuestPriority.HIGH);
        while (priority.hasNext()) {
            Quest quest = priority.next();
            questsTraversed++;
            String topic = quest.isUrgent() ? "urgent" : "orders";
            hall.dispatch(topic, null,
                    "Plan strike: " + quest.getTitle() + " (" + quest.getRewardGold() + "g)");
        }

        int messagesAfter = messagesRouted(hall);
        int notifiedAfter = membersNotified(hall);

        System.out.println();
        System.out.println("=== Council session ends ===");

        return new CouncilRunResult(
                questsTraversed,
                messagesAfter - messagesBefore,
                notifiedAfter - notifiedBefore);
    }

    private int messagesRouted(GuildMediator hall) {
        return hall instanceof GuildHall ? ((GuildHall) hall).getMessagesRouted() : 0;
    }

    private int membersNotified(GuildMediator hall) {
        return hall instanceof GuildHall ? ((GuildHall) hall).getMembersNotified() : 0;
    }
}
