package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s JOIN s.pet p WHERE p.id = :petId")
    List<Schedule> findScheduleForPet(@Param("petId") Long petId);

    @Query("SELECT s FROM Schedule s JOIN s.employee e WHERE e.id = :employeeId")
    List<Schedule> findScheduleForEmployee(@Param("employeeId") Long employeeId);

    @Query("SELECT s from Schedule s JOIN s.pet p JOIN p.customer c WHERE c.id=:customerId")
    List<Schedule> findScheduleForCustomer(@Param("customerId") Long customerId);
}
