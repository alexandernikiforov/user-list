package ch.alni.userlist.service.impl;

import org.springframework.stereotype.Service;

import ch.alni.userlist.service.Admin;
import ch.alni.userlist.service.AdminService;

@Service
class AdminServiceImpl implements AdminService {

    @Override
    public Admin getInfo() {
        return Admin.create("Some User", "email@nowhere.org");
    }
}
