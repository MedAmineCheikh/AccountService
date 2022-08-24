package com.accountsservices.dto;

import com.accountsservices.Entity.AppRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

 @Data @AllArgsConstructor @NoArgsConstructor
public class AppUserRequestDTO  {


    private String username;
    private String password;
    private String confirmedpassword;
    private String email;
    private String nomprenom;
    private  String employeId;
    private boolean active;


}
