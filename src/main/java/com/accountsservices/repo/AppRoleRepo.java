package com.accountsservices.repo;

import com.accountsservices.Entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRoleRepo extends JpaRepository<AppRole,Long> {
    AppRole findByRoleName(String rolename);
}
