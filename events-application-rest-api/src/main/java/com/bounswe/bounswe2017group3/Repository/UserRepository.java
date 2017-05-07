package com.bounswe.bounswe2017group3.Repository;

import com.bounswe.bounswe2017group3.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);

    public User findById(long id);

    public User findByFullname(String fullname);

    @Query("SELECT COUNT(u) FROM User u where u.username = :username")
    public int numberOfUsersWithUsername(@Param("username") String username);

    @Query("SELECT COUNT(u) FROM User u where u.email = :email")
    public int numberOfUsersWithEmail(@Param("email") String email);
}
