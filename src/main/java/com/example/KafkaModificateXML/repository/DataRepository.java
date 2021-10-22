package com.example.KafkaModificateXML.repository;

import com.example.KafkaModificateXML.model.DataXMLEntity;
import com.example.KafkaModificateXML.model.TypeXML;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<DataXMLEntity, Integer> {
    List<DataXMLEntity> findAllByTypeAndVersion(
            @Param("type") TypeXML type,
            @Param("version") Integer version
    );
}
