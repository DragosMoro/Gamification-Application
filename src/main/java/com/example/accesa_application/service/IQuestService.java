package com.example.accesa_application.service;

import com.example.accesa_application.domain.Quest;

import java.util.ArrayList;

public interface IQuestService<ID> extends IService<ID, Quest<ID>> {
    ArrayList<Quest<Integer>> filterQuestsByUser(ID id);

    void markAsCompleted(Quest<Integer> selectedQuest);

    ArrayList<Quest<Integer>> filterQuestsByAvailability(ID id);
}
