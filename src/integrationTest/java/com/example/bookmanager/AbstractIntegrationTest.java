package com.example.bookmanager;

import com.example.bookmanager.entity.Role;
import com.example.bookmanager.repository.RoleRepository;
import com.example.bookmanager.repository.TokenRepository;
import com.example.bookmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    protected static  final String USER_ROLE = "USER";

    @Autowired
    protected RestTestClient client;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected TokenRepository tokenRepository;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @MockitoBean
    protected JavaMailSender mailSender;

    @BeforeEach
    void prepareState() {
        when(mailSender.createMimeMessage())
                .thenReturn(new JavaMailSenderImpl().createMimeMessage());
        tokenRepository.deleteAll();
        userRepository.deleteAll();

        roleRepository.findByName(USER_ROLE)
                .orElseGet(() -> roleRepository.save(Role.builder().name(USER_ROLE).build()));
    }
}
