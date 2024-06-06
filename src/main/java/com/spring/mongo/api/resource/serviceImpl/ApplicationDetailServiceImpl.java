package com.spring.mongo.api.resource.serviceImpl;

import com.spring.mongo.api.entity.ApplicationDetail;
import com.spring.mongo.api.entity.ClientDetails;
import com.spring.mongo.api.repository.ApplicationDetailRepository;
import com.spring.mongo.api.repository.ClientDetailRepository;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.ApplicationDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplicationDetailServiceImpl implements ApplicationDetailService {

    private final SequenceGeneratorService seqGeneratorService;
    private final ApplicationDetailRepository applicationDetailRepository;
    private final ClientDetailRepository clientDetailRepository;

    @Override
    public Response saveNewClient(ClientDetails clientDetails) {
        ClientDetails client = new ClientDetails();
        client.setClientId(seqGeneratorService.generateSequence(ClientDetails.SEQUENCE_NAME));
        client.setAge(clientDetails.getAge());
        client.setFirstName(clientDetails.getFirstName());
        client.setLastName(clientDetails.getLastName());
        client.setMobileNumber(clientDetails.getMobileNumber());
        client.setGender(clientDetails.getGender());
        clientDetailRepository.save(client);
        return new Response("Transaction completed successfully.", client.getClientId(), HttpStatus.OK);
    }

    @Override
    public Response saveApplication(ApplicationDetail applicationRequest) {
        applicationRequest.setApplicationId(seqGeneratorService.generateSequence(ApplicationDetail.SEQUENCE_NAME));
        ApplicationDetail applicationDetail = applicationDetailRepository.save(applicationRequest);
        return new Response("Transaction completed successfully.", applicationDetail.getApplicationId(), HttpStatus.OK);
    }

    @Override
    public Response getClientById(Long clientId) {
        Optional<ClientDetails> clientDetailsOptional = clientDetailRepository.findById(clientId);
        return clientDetailsOptional.map(clientDetails -> new Response("Transaction completed successfully.", clientDetails, HttpStatus.OK)).orElseGet(() -> new Response("No client Found. ", HttpStatus.BAD_REQUEST));
    }

    @Override
    public Response getApplicationId(Long applicationId) {
        Optional<ApplicationDetail> applicationDetailOptional = applicationDetailRepository.findById(applicationId);
        return applicationDetailOptional.map(applicationDetail -> new Response("Transaction completed successfully.", applicationDetail, HttpStatus.OK)).orElseGet(() -> new Response("No application Found with this Id. ", HttpStatus.BAD_REQUEST));
    }
}