package com.example.KafkaModificateXML.service;

import com.example.KafkaModificateXML.dto.DataXmlDTO;
import com.example.KafkaModificateXML.model.DataXMLEntity;
import com.example.KafkaModificateXML.model.TypeXML;
import com.example.KafkaModificateXML.repository.DataRepository;
import com.example.KafkaModificateXML.xmlmodification.ModificationXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {

    private DataRepository dataRepository;
    private ModificationXML modificationXML;

    @Autowired
    public DataService(DataRepository dataRepository, ModificationXML modificationXML) {
        this.dataRepository = dataRepository;
        this.modificationXML = modificationXML;
    }

    /**
     * сохранение объектов в БД , если обЪект с таким же типом и версией существует ,
     * то он возвращается обратно без сохранения в БД
     *
     * @param dataXmlDTO
     * @return
     */
    public DataXmlDTO save(DataXmlDTO dataXmlDTO) {
        TypeXML typeXML = TypeXML.valueOf(dataXmlDTO.getType());
        List<DataXMLEntity> dataXMLEntities = List.of(
                new DataXMLEntity(typeXML, dataXmlDTO.getIn1(), dataXmlDTO.getOut1(), dataXmlDTO.getVersion()),
                new DataXMLEntity(typeXML, dataXmlDTO.getIn2(), dataXmlDTO.getOut2(), dataXmlDTO.getVersion()),
                new DataXMLEntity(typeXML, dataXmlDTO.getIn3(), dataXmlDTO.getOut3(), dataXmlDTO.getVersion()),
                new DataXMLEntity(typeXML, dataXmlDTO.getIn4(), dataXmlDTO.getOut4(), dataXmlDTO.getVersion())
        );
        List<DataXMLEntity> dataXMLfindAllEntity = dataRepository.findAllByTypeAndVersion(typeXML, dataXmlDTO.getVersion());
        System.out.println(dataXMLfindAllEntity);
        if (!(dataXMLfindAllEntity.isEmpty()) && typeXML.equals(dataXMLfindAllEntity.get(0).getType()) &&
                dataXMLEntities.get(0).getVersion() == dataXMLfindAllEntity.get(0).getVersion()) {
            return dataXmlDTO;
        } else {
            dataRepository.saveAll(dataXMLEntities);
            return dataXmlDTO;
        }
    }

    public DataXMLEntity getById(int id) {
        return dataRepository.getById(id);
    }

    public List<DataXMLEntity> findAll() {
        return dataRepository.findAll();
    }

    /**
     * получение всех сущностей по типу и версии
     *
     * @param typeXML
     * @param version
     * @return
     */
    public DataXmlDTO findAllByTypeAndVersion(TypeXML typeXML, Integer version) {
        List<DataXMLEntity> dataXMLEntities = dataRepository.findAllByTypeAndVersion(typeXML, version);
        if (!(dataXMLEntities.isEmpty())) {
            DataXmlDTO dataXmlDTO = getDataXmlDTO(dataXMLEntities);
            return dataXmlDTO;
        } else {
            return null;
        }
    }

    public DataXmlDTO findAllByMaxVersion(){
        return getDataXmlDTO(dataRepository.findAllWithMaxVersion());
    }

    /**
     * преобразование объекта List<DataXML> dataXMLS в DataXmlDTO
     *
     * @param dataXMLEntities
     * @return
     */
    public DataXmlDTO getDataXmlDTO(List<DataXMLEntity> dataXMLEntities) {
        DataXmlDTO dataXmlDTO = new DataXmlDTO();
        dataXmlDTO.setIn1(dataXMLEntities.get(0).getInValue());
        dataXmlDTO.setIn2(dataXMLEntities.get(1).getInValue());
        dataXmlDTO.setIn3(dataXMLEntities.get(2).getInValue());
        dataXmlDTO.setIn4(dataXMLEntities.get(3).getInValue());
        dataXmlDTO.setOut1(dataXMLEntities.get(0).getOutValue());
        dataXmlDTO.setOut2(dataXMLEntities.get(1).getOutValue());
        dataXmlDTO.setOut3(dataXMLEntities.get(2).getOutValue());
        dataXmlDTO.setOut4(dataXMLEntities.get(3).getOutValue());
        dataXmlDTO.setType(dataXMLEntities.get(0).getType().name());
        dataXmlDTO.setVersion(dataXMLEntities.get(0).getVersion());
        return dataXmlDTO;
    }
}
