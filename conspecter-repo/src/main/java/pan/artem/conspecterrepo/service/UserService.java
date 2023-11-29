package pan.artem.conspecterrepo.service;

import pan.artem.conspecterrepo.entity.User;

public interface UserService {

    User findOrCreate(String username);

    User save(User user);
}
