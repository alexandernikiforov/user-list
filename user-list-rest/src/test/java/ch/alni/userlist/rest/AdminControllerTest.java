package ch.alni.userlist.rest;

import org.hamcrest.core.StringContains;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import ch.alni.userlist.service.Admin;
import ch.alni.userlist.service.AdminService;
import ch.alni.userlist.service.UserData;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class AdminControllerTest extends ControllerTestSupport {

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String USER = "user";
    private static final String EMAIL = "email";
    @MockBean
    private AdminService adminService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testInfo() throws Exception {
        when(adminService.getInfo()).thenReturn(Admin.create(USER, EMAIL));

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().json(format("{\"name\": \"%s\", \"email\": \"%s\"}", USER, EMAIL)));

        verify(adminService, times(1)).getInfo();
        verifyNoMoreInteractions(adminService);
    }

    @Test
    public void testCreateUser() throws Exception {
        when(adminService.createUser(any(UserData.class))).thenReturn(USER_ID);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(format("{\"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\"}", FIRST_NAME, LAST_NAME, EMAIL)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", StringContains.containsString("/users/" + USER_ID)));

        verify(adminService, times(1)).createUser(UserData.builder()
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setEmail(EMAIL)
                .build()
        );

        verifyNoMoreInteractions(adminService);
    }

    @Test
    public void testFindUser() throws Exception {
        final UserData userData = UserData.builder()
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setEmail(EMAIL)
                .build();

        when(adminService.findUser(USER_ID)).thenReturn(Optional.of(userData));

        mockMvc.perform(get("/users/{userId}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(format("{\"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\"}", FIRST_NAME, LAST_NAME, EMAIL)));
    }

    @Test
    public void testFindUserNotFound() throws Exception {
        when(adminService.findUser(USER_ID)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/{userId}", USER_ID))
                .andExpect(status().isNotFound());
    }
}
