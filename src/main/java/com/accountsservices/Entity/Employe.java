package com.accountsservices.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Employe {
    private String matricule;
    private Boolean chef_Projet;
    private Date charge_Salariale;
    private String n_Permis;
    private String n_Cin;
    private Statut statut;
}
