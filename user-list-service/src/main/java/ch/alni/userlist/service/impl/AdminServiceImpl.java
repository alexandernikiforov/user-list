package ch.alni.userlist.service.impl;

import ch.alni.userlist.service.Admin;
import ch.alni.userlist.service.AdminProperties;
import ch.alni.userlist.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class AdminServiceImpl implements AdminService {

    private final AdminProperties adminProperties;

    @Autowired
    public AdminServiceImpl(AdminProperties adminProperties) {
        this.adminProperties = adminProperties;
    }

    @Override
    public Admin getInfo() {
        return Admin.create(adminProperties.getName(), adminProperties.getEmail());
    }
}
