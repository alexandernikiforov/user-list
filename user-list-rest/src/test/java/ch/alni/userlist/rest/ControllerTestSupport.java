package ch.alni.userlist.rest;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ContextConfiguration(classes = {
        RestConfig.class, RestTestConfig.class
})
class ControllerTestSupport {

}
