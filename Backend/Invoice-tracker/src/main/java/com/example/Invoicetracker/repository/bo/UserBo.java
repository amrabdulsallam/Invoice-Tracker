package com.example.Invoicetracker.repository.bo;

import com.example.Invoicetracker.service.dto.RoleDTO;

import java.util.Date;
import java.util.List;

public interface UserBo {

    public long getId();

    public String getEmail();

    public String getPhone();

    public List<RoleDTO> getRole();

    public Date getSignupDate();

}
