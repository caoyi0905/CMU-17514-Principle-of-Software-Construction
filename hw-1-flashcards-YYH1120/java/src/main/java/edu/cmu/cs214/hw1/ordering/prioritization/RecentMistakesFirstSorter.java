package edu.cmu.cs214.hw1.ordering.prioritization;

import edu.cmu.cs214.hw1.cards.CardStatus;
import edu.cmu.cs214.hw1.ordering.CardOrganizer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecentMistakesFirstSorter implements CardOrganizer {
    public List<CardStatus> reorganize(List<CardStatus> cards) {
        return cards.stream()
                .sorted(Comparator.comparingInt(this::numberOfMostRecentFailures).reversed())
                .collect(Collectors.toList());
    }

    private int numberOfMostRecentFailures(CardStatus cardStatus) {
        for (int i = cardStatus.getResults().size(); i>0; i = i - 1){
            if (cardStatus.getResults().get(i-1) == false)
                return i-1;
        }
        return -1;
    }
}
