package com.example.KafkaModificateXML.repsitory;

import com.example.KafkaModificateXML.model.DataXML;
import com.example.KafkaModificateXML.model.TypeXML;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataRepository extends JpaRepository<DataXML, Integer> {

    List<DataXML> findAllByType(TypeXML type);
}
