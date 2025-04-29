package edu.icet.repository;

import edu.icet.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao extends JpaRepository< EmployeeEntity , Long> {
    @Query("SELECT e FROM EmployeeEntity e WHERE e.id = :id AND e.isActive = true")
    Optional<EmployeeEntity> findByIdActive(@Param("id") Long id);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.isActive = true")
    List<EmployeeEntity> findAllActive();

    Optional<EmployeeEntity> findByEmail(String email);
}
