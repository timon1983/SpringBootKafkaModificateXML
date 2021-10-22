package com.example.KafkaModificateXML.service;

import com.example.KafkaModificateXML.dto.DataXmlDTO;
import com.example.KafkaModificateXML.model.DataXML;
import com.example.KafkaModificateXML.model.TypeXML;
import com.example.KafkaModificateXML.repository.DataRepository;
import com.example.KafkaModificateXML.xmlmodification.ModificationXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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

    public DataXmlDTO save(DataXmlDTO dataXmlDTO) {
        TypeXML typeXML = TypeXML.valueOf(dataXmlDTO.getType());
        List<DataXML> dataXMLs = List.of(
                new DataXML(typeXML, dataXmlDTO.getIn1(), dataXmlDTO.getOut1(), dataXmlDTO.getVersion()),
                new DataXML(typeXML, dataXmlDTO.getIn2(), dataXmlDTO.getOut2(), dataXmlDTO.getVersion()),
                new DataXML(typeXML, dataXmlDTO.getIn3(), dataXmlDTO.getOut3(), dataXmlDTO.getVersion()),
                new DataXML(typeXML, dataXmlDTO.getIn4(), dataXmlDTO.getOut4(), dataXmlDTO.getVersion())
        );
        List<DataXML> dataXMLfindAll = dataRepository.findAllByTypeAndVersion(typeXML , dataXmlDTO.getVersion());
        System.out.println(dataXMLfindAll);
        if (!(dataXMLfindAll.isEmpty()) && typeXML.equals(dataXMLfindAll.get(0).getType()) &&
                dataXMLs.get(0).getVersion() == dataXMLfindAll.get(0).getVersion()) {
            modificationXML.workWithDataXML(getDataXmlDTO(dataXMLfindAll));
            return getDataXmlDTO(dataXMLfindAll);
        } else {
            dataRepository.saveAll(dataXMLs);
            modificationXML.workWithDataXML(dataXmlDTO);
            return dataXmlDTO;
        }
    }

    public DataXML getById(int id) {
        return dataRepository.getById(id);
    }

    public List<DataXML> findAll() {
        return dataRepository.findAll();
    }

    public DataXmlDTO findAllByType(TypeXML typeXML , Integer version) {
        List<DataXML> dataXMLS = dataRepository.findAllByTypeAndVersion(typeXML , version);
        if (!(dataXMLS.isEmpty())) {
            DataXmlDTO dataXmlDTO = getDataXmlDTO(dataXMLS);
            return dataXmlDTO;
        } else {
            return null;
        }
    }

    public DataXmlDTO getDataXmlDTO(List<DataXML> dataXMLS){
        DataXmlDTO dataXmlDTO = new DataXmlDTO();
        dataXmlDTO.setIn1(dataXMLS.get(0).getInValue());
        dataXmlDTO.setIn2(dataXMLS.get(1).getInValue());
        dataXmlDTO.setIn3(dataXMLS.get(2).getInValue());
        dataXmlDTO.setIn4(dataXMLS.get(3).getInValue());
        dataXmlDTO.setOut1(dataXMLS.get(0).getOutValue());
        dataXmlDTO.setOut2(dataXMLS.get(1).getOutValue());
        dataXmlDTO.setOut3(dataXMLS.get(2).getOutValue());
        dataXmlDTO.setOut4(dataXMLS.get(3).getOutValue());
        dataXmlDTO.setType(dataXMLS.get(0).getType().name());
        dataXmlDTO.setVersion(dataXMLS.get(0).getVersion());
        return dataXmlDTO;
    }

    public void getModificationFields(List<DataXML> dataXMLS) {
        List<String> fields = new ArrayList<>();
//        fields.add(dataXML.getIncomeField1());
//        fields.add(dataXML.getIncomeField2());
//        fields.add(dataXML.getIncomeField3());
//        fields.add(dataXML.getIncomeField4());
//        fields.add(dataXML.getOutgoingField1());
//        fields.add(dataXML.getOutgoingField2());
//        fields.add(dataXML.getOutgoingField3());
//        fields.add(dataXML.getOutgoingField4());
        //modificationXML.setFieldsForWork(fields);
    }
}
