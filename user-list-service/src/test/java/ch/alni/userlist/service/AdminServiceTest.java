package ch.alni.userlist.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        AdminServiceConfig.class, AdminServiceTestConfig.class
})
@TestPropertySource("/test.properties")
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void getInfo() {
        // show case that the properties can be taken from the a custom property file on a single test-basis
        final var info = adminService.getInfo();
        assertThat(info.getName()).isEqualTo("test");
        assertThat(info.getEmail()).isEqualTo("test@nowhere.org");
    }
}