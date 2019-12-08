package ch.alni.userlist.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ch.alni.userlist.core.CoreConfig;
import ch.alni.userlist.core.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        ServiceConfig.class, AdminServiceTestConfig.class, CoreConfig.class
})
@TestPropertySource("/test.properties")
@Transactional
@Commit
public class AdminServiceTest {

    public static final String EMAIL = "email@nowhere.org";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void getInfo() {
        // show case that the properties can be taken from the a custom property file on a single test-basis
        final var info = adminService.getInfo();
        assertThat(info.getName()).isEqualTo("test");
        assertThat(info.getEmail()).isEqualTo("test@nowhere.org");
    }

    @Test
    public void testCreateUser() {
        final var userData = UserData.builder()
                .setEmail(EMAIL)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .build();

        final var userId = adminService.createUser(userData);

        // try to find it
        assertThat(adminService.findUser(userId)).hasValue(userData);
    }
}
