package com.bounswe.bounswe2017group3;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    public Event findByName(String name);

    @Query("SELECT COUNT(e) FROM Event e where e.name = :name")
    public int numberOfEventsWithName(@Param("name") String name);


}