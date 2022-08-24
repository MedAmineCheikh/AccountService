package com.accountsservices.dto;

import com.accountsservices.Entity.AppRole;
import com.accountsservices.Entity.Employe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class AppUserResponseDTO {


   private String username;
   private String email;
   private String nomprenom;
   private boolean active;
   private Employe employe;
   private Collection<AppRole> appRoles =new ArrayList<>();


}
