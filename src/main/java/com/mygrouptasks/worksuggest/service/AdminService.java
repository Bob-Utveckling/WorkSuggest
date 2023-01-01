package com.mygrouptasks.worksuggest.service;

import com.mygrouptasks.worksuggest.model.Admin;
import com.mygrouptasks.worksuggest.model.Person;
import com.mygrouptasks.worksuggest.repository.AdminRepository;
import com.mygrouptasks.worksuggest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//import com.mygrouptasks.worksuggest.repository.PersonRepository;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        adminRepository.findAll().forEach(admins::add);
        return admins;
    }

    public void addAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public Admin getAdmin(int id) {
        return adminRepository.findById(id);
    }
}
