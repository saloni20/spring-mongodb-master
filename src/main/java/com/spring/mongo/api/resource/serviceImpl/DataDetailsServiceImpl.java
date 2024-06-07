package com.spring.mongo.api.resource.serviceImpl;

import com.spring.mongo.api.entity.DataDetails;
import com.spring.mongo.api.repository.DataDetailRepository;
import com.spring.mongo.api.resource.dto.DataDetailsDto;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.DataDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class DataDetailsServiceImpl implements DataDetailsService {

    private final DataDetailRepository dataDetailRepository;

    @Override
    public Response addDataByType(List<DataDetailsDto> dataDetailsDtoList) {
        if (!CollectionUtils.isEmpty(dataDetailsDtoList)) {
            for (DataDetailsDto dataDetailsDto : dataDetailsDtoList) {
                DataDetails dataDetails = new DataDetails();
                dataDetails.setType(dataDetailsDto.getType());
                dataDetails.setValue(dataDetailsDto.getValue());
                dataDetails.setIsRequired(dataDetailsDto.getIsRequired());
                dataDetails.setLabel(dataDetailsDto.getLabel());
                dataDetails.setMaxLength(dataDetailsDto.getMaxLength());
                dataDetails.setOptions(dataDetailsDto.getOptions());
                dataDetailRepository.save(dataDetails);
            }
            return new Response("Transaction completed successfully.", HttpStatus.OK);
        }
        return null;
    }

    @Override
    public Response filterDataByType(String type) {
        List<DataDetailsDto> detailsDtoList = new ArrayList<>();
        List<DataDetails> dataDetailsList = dataDetailRepository.findByType(type);
        for (DataDetails dataDetails : dataDetailsList) {
            DataDetailsDto dataDetailsDto = new DataDetailsDto();
            dataDetailsDto.setId(dataDetails.getId().toHexString());
            dataDetailsDto.setType(dataDetails.getType());
            dataDetailsDto.setValue(dataDetails.getValue());
            dataDetailsDto.setIsRequired(dataDetails.getIsRequired());
            dataDetailsDto.setLabel(dataDetails.getLabel());
            dataDetailsDto.setMaxLength(dataDetails.getMaxLength());
            dataDetailsDto.setOptions(dataDetails.getOptions());
            detailsDtoList.add(dataDetailsDto);
        }
        return new Response("Transaction completed successfully.", detailsDtoList, HttpStatus.OK);
    }
}