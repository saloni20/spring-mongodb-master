package com.spring.mongo.api.resource.serviceImpl;

import com.spring.mongo.api.entity.ApplicationDetail;
import com.spring.mongo.api.entity.ClientDetails;
import com.spring.mongo.api.repository.ApplicationDetailRepository;
import com.spring.mongo.api.repository.ClientDetailRepository;
import com.spring.mongo.api.resource.request.ApplicationRequest;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.ApplicationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationDetailServiceImpl implements ApplicationDetailService {

    @Autowired
    SequenceGeneratorService seqGeneratorService;
    private final ApplicationDetailRepository applicationDetailRepository;
    private  final ClientDetailRepository clientDetailRepository;

    public ApplicationDetailServiceImpl(ApplicationDetailRepository applicationDetailRepository, ClientDetailRepository clientDetailRepository) {
        this.applicationDetailRepository = applicationDetailRepository;
        this.clientDetailRepository = clientDetailRepository;
    }

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
        return new Response("Client Saved Successfully",client.getClientId(), HttpStatus.OK);
    }

    @Override
    public Response saveApplication(ApplicationDetail applicationRequest) {
        applicationRequest.setApplicationId(seqGeneratorService.generateSequence(ApplicationDetail.SEQUENCE_NAME));
        ApplicationDetail applicationDetail = applicationDetailRepository.save(applicationRequest);
        return new Response("Client Saved Successfully",applicationDetail.getApplicationId(), HttpStatus.OK);
    }

    @Override
    public Response getClientById(Long clientId) {
          Optional<ClientDetails> clientDetailsOptional = clientDetailRepository.findById(clientId);
          if(clientDetailsOptional.isPresent())
            return new Response("Transaction Successful",clientDetailsOptional.get(),HttpStatus.OK);
          else
        return new Response("No client Found. ",HttpStatus.BAD_REQUEST);
    }

    @Override
    public Response getApplicationId(Long applicationId) {

        Optional<ApplicationDetail> applicationDetailOptional = applicationDetailRepository.findById(applicationId);
        if(applicationDetailOptional.isPresent())
            return new Response("Transaction Successful",applicationDetailOptional.get(),HttpStatus.OK);
        else
            return new Response("No application Found with this Id. ",HttpStatus.BAD_REQUEST);
    }
}
