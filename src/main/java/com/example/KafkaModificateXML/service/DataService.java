package com.example.KafkaModificateXML.service;

import com.example.KafkaModificateXML.model.DataDTO;
import com.example.KafkaModificateXML.repsitory.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {

    private DataRepository dataRepository;

    @Autowired

    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void save(DataDTO dataDTO){
        int id = dataRepository.findAll().get(0).getId();
        dataRepository.deleteById(id);
        dataRepository.save(dataDTO);
    }

    public DataDTO getById(int id){
       return dataRepository.getById(id);
    }

    public List<DataDTO> findAll(){
        return dataRepository.findAll();
    }
}
