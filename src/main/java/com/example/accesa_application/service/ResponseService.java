package com.example.accesa_application.service;

import com.example.accesa_application.domain.Response;
import com.example.accesa_application.repository.IResponseRepository;

import java.util.ArrayList;
import java.util.Properties;

public class ResponseService implements IResponseService<Integer>{

        static Properties props = new Properties();
        private final IResponseRepository<Integer> responseRepository;

        public ResponseService(Properties props, IResponseRepository<Integer> responseRepository) {
            this.props = props;
            this.responseRepository = responseRepository;
        }
        @Override
        public void add(Response<Integer> elem) {
            responseRepository.add(elem);
        }

        @Override
        public void update(Integer id, Response<Integer> elem) {
            responseRepository.update(id,elem);
        }

        @Override
        public void delete(Integer id) {
            responseRepository.delete(id);
        }

        @Override
        public Response<Integer> findAfterId(Integer id) {
            return responseRepository.findAfterId(id);
        }

        @Override
        public Iterable<Response<Integer>> getAll() {
            return responseRepository.getAll();
        }

    @Override
    public ArrayList<Response<Integer>> filterResponsesByQuest(Integer id) {
        Iterable<Response<Integer>> responses = getAll();
        ArrayList<Response<Integer>> filtredResponses = new ArrayList<>();
        for(Response<Integer> response:responses)
            if(response.getIdQuest().equals(id))
                filtredResponses.add(response);
        return filtredResponses;

    }

    @Override
    public void markAsBestResponse(Response<Integer> selectedResponse) {
        selectedResponse.setStatus("Best Response");
        update(selectedResponse.getId(),selectedResponse);
    }
}
