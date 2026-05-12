package com.narxoz.rpg.quest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Traverses quests by reward gold (highest reward first).
 *
 * <p>Open/closed proof: adding this iterator does not modify
 * {@link QuestLog}, {@link OrderedQuestIterator}, {@link ReverseQuestIterator},
 * or {@link PriorityQuestIterator}.
 */
public class RewardSortedQuestIterator implements QuestIterator {

    private final List<Quest> snapshot;
    private int cursor;

    public RewardSortedQuestIterator(QuestLog questLog) {
        this.snapshot = new ArrayList<>(questLog.snapshot());
        this.snapshot.sort(Comparator.comparingInt(Quest::getRewardGold).reversed());
        this.cursor = 0;
    }

    @Override
    public boolean hasNext() {
        return cursor < snapshot.size();
    }

    @Override
    public Quest next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException("No more quests to sort by reward");
        }
        return snapshot.get(cursor++);
    }
}
