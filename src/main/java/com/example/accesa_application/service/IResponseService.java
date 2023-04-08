package com.example.accesa_application.service;

import com.example.accesa_application.domain.Response;

import java.util.ArrayList;

public interface IResponseService<ID> extends IService<ID, Response<ID>>{
    ArrayList<Response<Integer>> filterResponsesByQuest(Integer id);

    void markAsBestResponse(Response<Integer> selectedResponse);
}
