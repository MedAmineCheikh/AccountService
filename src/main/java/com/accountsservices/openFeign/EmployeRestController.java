package com.accountsservices.openFeign;


import com.accountsservices.Entity.Employe;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "EMPLOYE-SERVICE")
public interface EmployeRestController {

    @GetMapping(path = "/employe/{id}")
    Employe getEmploye(@PathVariable String id);

    @GetMapping(path = "/employes")
    List<Employe> allEmployes();

}
