package com.bounswe.bounswe2017group3;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    public Event findByName(String name);
    
    //List events with respect to privacy option
    public List<Event> findByPrivacyoption(Boolean privacyoption);
    
    @Query("SELECT COUNT(e) FROM Event e where e.name = :name")
    public int numberOfEventsWithName(@Param("name") String name);


}