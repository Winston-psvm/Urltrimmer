package com.testproject.urltrimmer.repository;

import com.testproject.urltrimmer.model.LinkingCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaStatsRepository extends JpaRepository<LinkingCounter, Integer> {

    Optional<LinkingCounter> getByDateAndUrlId(LocalDate date, Integer id);

    List<LinkingCounter> getAllByUrlIdOrderByDate(Integer id);

    @Modifying
    @Query("UPDATE LinkingCounter l set l.counter = l.counter + 1 where l.id = :id")
    void updateUrlCounter(Integer id);
}
