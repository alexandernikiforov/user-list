package ch.alni.userlist.rest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.alni.userlist.service.Admin;
import ch.alni.userlist.service.AdminService;

import static org.slf4j.LoggerFactory.getLogger;

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

    @RequestMapping(method = RequestMethod.GET, path = "/admin")
    public Admin info() {
        LOG.info("requesting admin object");
        return adminService.getInfo();
    }
}
