package com.example.KafkaModificateXML.repository;

import com.example.KafkaModificateXML.model.DataXML;
import com.example.KafkaModificateXML.model.TypeXML;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<DataXML, Integer> {

    @Query("SELECT d FROM DataXML d WHERE d.type = ?1 AND d.version = ?2")
    List<DataXML> findAllByTypeAndVersion(TypeXML type , Integer version);
}
