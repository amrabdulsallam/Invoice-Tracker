package com.example.Invoicetracker.repository.bo;

import com.example.Invoicetracker.model.enums.Role;

import java.util.Date;

public interface UserBo {

    public long getId();

    public String getEmail();

    public String getPhone();

    public Role getRole();

    public Date getSignupDate();

}
