package pan.artem.conspecterweb.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pan.artem.conspecterweb.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterServiceImplTest {

    @Autowired
    RegisterServiceImpl registerService;
    @Autowired
    UserRepository userRepository;

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    void register() {
        boolean success = registerService.register("user", "password");
        var user = userRepository.findByName("user");

        assertTrue(success);
        assertEquals("user", user.getName());
        assertEquals("USER", user.getRole().getName());
    }

    @Test
    void registerTwice() {
        registerService.register("user", "password");
        boolean success = registerService.register("user", "password");
        var user = userRepository.findByName("user");

        assertFalse(success);
        assertEquals("user", user.getName());
    }
}