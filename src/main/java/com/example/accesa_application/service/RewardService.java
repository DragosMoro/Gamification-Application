package com.example.accesa_application.service;

import com.example.accesa_application.domain.Reward;
import com.example.accesa_application.repository.IResponseRepository;
import com.example.accesa_application.repository.IRewardRepository;

import java.util.ArrayList;
import java.util.Properties;

public class RewardService implements IRewardService<Integer>{
    static Properties props = new Properties();
    private final IRewardRepository<Integer> rewardRepository;

    public RewardService(Properties props, IRewardRepository<Integer> rewardRepository) {
        this.props = props;
        this.rewardRepository = rewardRepository;
    }
    @Override
    public void add(Reward<Integer> elem) {
        rewardRepository.add(elem);
    }

    @Override
    public void update(Integer id, Reward<Integer> elem) {
        rewardRepository.update(id,elem);
    }

    @Override
    public void delete(Integer id) {
        rewardRepository.delete(id);
    }

    @Override
    public Reward<Integer> findAfterId(Integer id) {
        return rewardRepository.findAfterId(id);
    }

    @Override
    public Iterable<Reward<Integer>> getAll() {
        return rewardRepository.getAll();
    }

    @Override
    public ArrayList<Reward<Integer>> filterRewardsByUser(Integer id) {
        Iterable<Reward<Integer>> rewards = getAll();
        ArrayList<Reward<Integer>> filtredRewards = new ArrayList<>();
        for(Reward<Integer> reward:rewards)
            if(reward.getIdUser().equals(id))
                filtredRewards.add(reward);
        return filtredRewards;
    }

    @Override
    public int getNumberOfRewardsByUser(Integer id) {
        return filterRewardsByUser(id).size();
    }
}
