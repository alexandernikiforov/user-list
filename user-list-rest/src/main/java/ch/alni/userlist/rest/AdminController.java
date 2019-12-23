package ch.alni.userlist.rest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import ch.alni.userlist.service.Admin;
import ch.alni.userlist.service.AdminService;
import ch.alni.userlist.service.UserData;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

/**
 * The REST interface for admin calls.
 */
@RestController
public class AdminController {
    private static final Logger LOG = getLogger(AdminController.class);

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public Admin info() {
        LOG.info("requesting admin object");
        return adminService.getInfo();
    }

    @PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createUser(@RequestBody UserData userData) {
        LOG.info("creating a new user");
        final var userId = adminService.createUser(userData);

        final var uriComponents = MvcUriComponentsBuilder
                .fromMethodCall(on(AdminController.class).getUser(userId))
                .build();

        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping(path = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserData> getUser(@PathVariable("userId") final String userId) {
        return adminService.findUser(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
