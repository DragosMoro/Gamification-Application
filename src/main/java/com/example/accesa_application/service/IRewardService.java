package com.example.accesa_application.service;

import com.example.accesa_application.domain.Reward;

import java.util.ArrayList;

public interface IRewardService<ID> extends IService<ID, Reward<ID>> {

    ArrayList<Reward<Integer>> filterRewardsByUser(Integer id);

    int getNumberOfRewardsByUser(Integer id);
}
