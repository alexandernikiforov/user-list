package ch.alni.userlist.rest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ch.alni.userlist.service.Admin;
import ch.alni.userlist.service.AdminService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class AdminControllerTest extends ControllerTestSupport {

    private static final String USER = "user";
    private static final String EMAIL = "email";

    @MockBean
    private AdminService adminService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void info() throws Exception {
        when(adminService.getInfo()).thenReturn(Admin.create(USER, EMAIL));

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().json(String.format("{\"name\": \"%s\", \"email\": \"%s\"}", USER, EMAIL)));
    }
}