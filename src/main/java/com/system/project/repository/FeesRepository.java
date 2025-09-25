package com.system.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.system.project.entities.Fees;

public interface FeesRepository extends JpaRepository<Fees, Integer> {
    @Query("SELECT f FROM Fees f WHERE f.classEntity.course.id = :courseId AND f.classEntity.id = :classId AND f.caste.id = :casteId")
    List<Fees> findByCourseIdAndClassIdAndCasteId(
            @Param("courseId") Long courseId,
            @Param("classId") Long classId,
            @Param("casteId") Long casteId);

	Optional<Fees> findByCourse_IdAndClassEntity_IdAndCaste_Id(Long courseId, Long classId, Long casteId);

	boolean existsById(Long id);

	boolean existsByCourseId(Long courseId);

	@Query("SELECT f FROM Fees f JOIN FETCH f.course JOIN FETCH f.classEntity JOIN FETCH f.caste")
    List<Fees> findAllWithRelations();

	boolean existsByCourseIdAndClassEntityIdAndCasteId(Long courseId, Long classId, Long casteId);

	void deleteById(Long id);
}