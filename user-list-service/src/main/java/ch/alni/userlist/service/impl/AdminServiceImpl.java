package ch.alni.userlist.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import ch.alni.userlist.core.User;
import ch.alni.userlist.core.UserRepository;
import ch.alni.userlist.service.Admin;
import ch.alni.userlist.service.AdminProperties;
import ch.alni.userlist.service.AdminService;
import ch.alni.userlist.service.UserData;

import static org.slf4j.LoggerFactory.getLogger;

@Service
class AdminServiceImpl implements AdminService {
    private static final Logger LOG = getLogger(AdminServiceImpl.class);

    private final AdminProperties adminProperties;
    private final UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(AdminProperties adminProperties, UserRepository userRepository) {
        this.adminProperties = adminProperties;
        this.userRepository = userRepository;
    }

    @Override
    public Admin getInfo() {
        return Admin.create(adminProperties.getName(), adminProperties.getEmail());
    }

    private static UserData toUserData(User user) {
        return UserData.builder()
                .setEmail(user.getEmail())
                .setLastName(user.getLastName())
                .setFirstName(user.getFirstName())
                .build();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String createUser(UserData userData) {
        LOG.info("creating new user with data {}", userData);

        final User user = new User();

        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setEmail(userData.getEmail());
        user.setActive(true);

        userRepository.save(user);

        return user.getId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<UserData> findUser(String userId) {
        LOG.info("searching for user with ID {}", userId);
        return userRepository.findById(userId).map(AdminServiceImpl::toUserData);
    }
}
