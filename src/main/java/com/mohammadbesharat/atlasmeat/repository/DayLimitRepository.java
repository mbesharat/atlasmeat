package com.mohammadbesharat.atlasmeat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mohammadbesharat.atlasmeat.model.DayLimit;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;


public  interface DayLimitRepository extends JpaRepository<DayLimit, LocalDate>{

    //this fetches single date if applicable
    Optional<DayLimit> findByDate(LocalDate date);

    //this fetches dates between a range
    List<DayLimit> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
}
