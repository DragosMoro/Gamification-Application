package com.example.accesa_application.service;

import com.example.accesa_application.domain.Quest;
import com.example.accesa_application.repository.IQuestRepository;

import java.util.ArrayList;
import java.util.Properties;

public class QuestService implements IQuestService<Integer>{
    Properties props ;
    private final IQuestRepository<Integer> questRepository;

    public QuestService(Properties props,IQuestRepository<Integer> questRepository) {
        this.props = props;
        this.questRepository = questRepository;
    }

    @Override
    public void add(Quest<Integer> elem) {
        questRepository.add(elem);
    }

    @Override
    public void update(Integer id, Quest<Integer> elem) {
        questRepository.update(id,elem);
    }

    @Override
    public void delete(Integer id) {
        questRepository.delete(id);
    }

    @Override
    public Quest<Integer> findAfterId(Integer id) {
        return questRepository.findAfterId(id);
    }

    @Override
    public Iterable<Quest<Integer>> getAll() {
        return questRepository.getAll();
    }

    @Override
    public ArrayList<Quest<Integer>> filterQuestsByUser(Integer id) {
        Iterable<Quest<Integer>> quests = getAll();
        ArrayList<Quest<Integer>> filtredQuests = new ArrayList<>();
        for(Quest<Integer> quest:quests)
            if(quest.getUserId().equals(id))
                filtredQuests.add(quest);
        return filtredQuests;
    }

    @Override
    public void markAsCompleted(Quest<Integer> selectedQuest) {
        selectedQuest.setCompleted(true);
        update(selectedQuest.getId(),selectedQuest);
    }

    @Override
    public ArrayList<Quest<Integer>> filterQuestsByAvailability(Integer id) {
        Iterable<Quest<Integer>> quests = getAll();
        ArrayList<Quest<Integer>> filtredQuests = new ArrayList<>();
        for(Quest<Integer> quest:quests)
            if(!quest.isCompleted() && !quest.getUserId().equals(id))
                filtredQuests.add(quest);
        return filtredQuests;
    }

}

