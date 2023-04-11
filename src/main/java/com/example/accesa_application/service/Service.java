package com.example.accesa_application.service;

import java.util.Properties;

public class Service {
    private final IUserService<Integer> userService;
    private final IQuestService<Integer> questService;
    private final IResponseService<Integer> responseService;
    private final IRewardService<Integer> rewardService;
    private final IItemService<Integer> itemService;

    public Service(Properties props, IUserService<Integer> userService, IQuestService<Integer> questService, IResponseService<Integer> responseService, IRewardService<Integer> rewardService, IItemService<Integer> itemService) {
        this.userService = userService;
        this.questService = questService;
        this.responseService = responseService;
        this.rewardService = rewardService;
        this.itemService = itemService;
    }

    public IUserService<Integer> getUserService() {
        return userService;
    }

    public IQuestService<Integer> getQuestService() {
        return questService;
    }

    public IResponseService<Integer> getResponseService() {
        return responseService;
    }

    public IRewardService<Integer> getRewardService() {
        return rewardService;
    }

    public IItemService<Integer> getItemService() {
        return itemService;
    }
}
