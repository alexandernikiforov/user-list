package ch.alni.userlist.service;

import java.util.Optional;

public interface AdminService {

    Admin getInfo();

    String createUser(UserData userData);

    Optional<UserData> findUser(String userId);
}
