package pan.artem.conspecterweb.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pan.artem.conspecterweb.entity.User;
import pan.artem.conspecterweb.repository.RoleRepository;
import pan.artem.conspecterweb.repository.UserRepository;

@AllArgsConstructor
@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean register(String username, String password) {
        if (userRepository.findByName(username) != null
                || username.isEmpty() || password.isEmpty()) {
            return false;
        }

        User user = new User();
        user.setName(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(roleRepository.findByName("USER"));
        userRepository.save(user);

        return true;
    }
}
