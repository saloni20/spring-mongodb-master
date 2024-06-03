package com.spring.mongo.api.resource.serviceImpl;

import com.spring.mongo.api.resource.dto.AdminRegisterDto;
import com.spring.mongo.api.resource.service.ValidPassword;
import lombok.extern.slf4j.Slf4j;
import org.passay.*;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(

                new LengthRule(8, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 3, false),
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 3, false),
                new WhitespaceRule()

        ));
        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);

        String messageTemplate = messages.stream()
                .collect(Collectors.joining(","));
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }


    public boolean checkStrongPassword(AdminRegisterDto adminRegisterDto) {
        if (adminRegisterDto.getEmail().contains(adminRegisterDto.getPassword())) {
            log.info("Password Cannot be from email " + " for user : {} ", adminRegisterDto.getEmail());
            return false;
        }
        List<String> nameParts = Arrays.asList(adminRegisterDto.getFirstname().toLowerCase(), adminRegisterDto.getLastname().toLowerCase());
        for (String part : nameParts) {
            if (adminRegisterDto.getPassword().toLowerCase().contains(part)) {
                log.info("Password cannot contain the user's name ({}) for user: {}", part, adminRegisterDto.getEmail());
                return false;
            }
        }
        return isValid(adminRegisterDto.getPassword(), null);
    }





}
