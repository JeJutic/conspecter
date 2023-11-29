package pan.artem.conspecterrepo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pan.artem.conspecterrepo.entity.User;
import pan.artem.conspecterrepo.repository.UserRepository;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public User findOrCreate(String username) {
        User user = userRepository.findByName(username);
        if (user == null) {
            user = new User();
            user.setName(username);
            user = userRepository.save(user);
        }
        return user;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
