package com.bounswe.bounswe2017group3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    

    public User findByUsername(String username);

    public User findById(String id);
}
