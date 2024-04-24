package com.kick.it.kickit.services.impl;

import com.kick.it.kickit.entities.ContactUs;
import com.kick.it.kickit.repository.ContactUsRepository;
import com.kick.it.kickit.services.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class ContactUsServiceImpl implements ContactUsService {

    @Autowired
    ContactUsRepository contactUsRepository;

    @Override
    public String saveQuery(ContactUs contactUs) {
        contactUs.setRefNo("SR-"+ UUID.randomUUID());
        ContactUs local=contactUsRepository.save(contactUs);

        return local.getRefNo();
    }
}
