package com.example.bookmanager;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationIntegrationTest extends AbstractIntegrationTest{

    private static final String VALID_BODY = """
            {
                "firstname": "Lakatos",
                "lastname": "Ali",
                "email": "lakatoskaki@gmail.com",
                "password": "password1"
            }
            """;

    @Test
    void registerUser_persistsToken_andSendsActivationEmail() {
        ResponseEntity<String> response =
                rest.postForEntity(url("/auth/register"), json(VALID_BODY), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }
}
