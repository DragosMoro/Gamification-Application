package com.example.accesa_application.service;

import java.util.Properties;

public class Service {
    private final IUserService<Integer> userService;
    private final IQuestService<Integer> questService;
    private final IResponseService<Integer> responseService;

    public Service(Properties props, IUserService<Integer> userService, IQuestService<Integer> questService, IResponseService<Integer> responseService) {
        this.userService = userService;
        this.questService = questService;
        this.responseService = responseService;
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
}
