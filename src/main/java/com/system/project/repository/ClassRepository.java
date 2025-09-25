package com.system.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.system.project.entities.Class;


public interface ClassRepository extends JpaRepository<Class, Long> {
    @Query("SELECT c FROM Class c WHERE c.course.id = :courseId")
    List<Class> findByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT c FROM Class c JOIN FETCH c.fees WHERE c.course.id = :courseId")
    List<Class> findByCourseIdWithFees(@Param("courseId") Long courseId);

    @Modifying
    @Query("DELETE FROM Class c WHERE c.course.id = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT COUNT(c) > 0 FROM Class c WHERE c.className = :className AND c.course.id = :courseId")
	boolean existsByClassNameAndCourseId(@Param("className") String className, @Param("courseId") Long courseId);
}
//public interface ClassRepository extends JpaRepository<Class, Long> {
	//@Query("SELECT c FROM Class c JOIN FETCH c.fees WHERE c.course.id = :courseId")
	//List<Class> findByCourseIdWithClass(@Param("courseId") Long courseId);
	//
//}