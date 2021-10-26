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
            return DataXmlDTO.builder().build();
        }
    }

    /**
     * получение List<DataXMLEntity> по последней версии
     *
     * @return
     */
    public DataXmlDTO findAllByMaxVersion() {
        List<DataXMLEntity> dataXMLEntities = dataRepository.findAllWithMaxVersion();
        if (!(dataXMLEntities.isEmpty())) {
            return getDataXmlDTO(dataXMLEntities);
        } else {
            return DataXmlDTO.builder().build();
        }
    }

    /**
     * преобразование объекта List<DataXML> dataXMLS в DataXmlDTO
     *
     * @param dataXMLEntities
     * @return
     */
    public DataXmlDTO getDataXmlDTO(List<DataXMLEntity> dataXMLEntities) {

        return DataXmlDTO
                .builder()
                .type(dataXMLEntities.get(0).getType().name())
                .in1(dataXMLEntities.get(0).getInValue())
                .in2(dataXMLEntities.get(1).getInValue())
                .in3(dataXMLEntities.get(2).getInValue())
                .in4(dataXMLEntities.get(3).getInValue())
                .out1(dataXMLEntities.get(0).getOutValue())
                .out2(dataXMLEntities.get(1).getOutValue())
                .out3(dataXMLEntities.get(2).getOutValue())
                .out4(dataXMLEntities.get(3).getOutValue())
                .version(dataXMLEntities.get(0).getVersion())
                .build();
    }
}
