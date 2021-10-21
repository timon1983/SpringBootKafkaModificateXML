package com.example.KafkaModificateXML.repsitory;

import com.example.KafkaModificateXML.model.DataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<DataDTO, Integer> {
    
}
