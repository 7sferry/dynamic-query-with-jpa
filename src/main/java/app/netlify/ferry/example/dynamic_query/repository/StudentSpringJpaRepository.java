package app.netlify.ferry.example.dynamic_query.repository;

import app.netlify.ferry.example.dynamic_query.persistence.Student;
import app.netlify.ferry.example.dynamic_query.filter.StudentFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentSpringJpaRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student>{
	<C> List<C> findByActive(boolean active, Class<C> clazz);

	@Query("" +
			"select " +
			"s " +
			"from Student s " +
			"join fetch s.studentBatch " +
			"where " +
			"(:#{#filter?.active} is null or s.active = :#{#filter?.active}) AND " +
			"(:#{#filter?.nik} is null or s.nik = :#{#filter?.nik}) AND " +
			"(:#{#filter?.birthDateRangeStart?.toString()} is null or :#{#filter?.birthDateRangeEnd?.toString()} is null or " +
			"s.birthDate BETWEEN :#{#filter?.birthDateRangeStart} AND :#{#filter.birthDateRangeEnd}) AND " +
			"(:#{#filter?.studentName} is null or s.studentName LIKE %:#{#filter?.studentName}%) AND " +
			"(:#{#filter?.npm} is null or s.npm = :#{#filter?.npm}) " +
			"")
	List<Student> findWithManualQueryJoinBatch(@Param("filter") StudentFilter filter);

	@Query("" +
			"select " +
			"s " +
			"from Student s " +
			"where " +
			"(:#{#filter?.active} is null or s.active = :#{#filter?.active}) AND " +
			"(:#{#filter?.nik} is null or s.nik = :#{#filter?.nik}) AND " +
			"(:#{#filter?.birthDateRangeStart?.toString()} is null or :#{#filter?.birthDateRangeEnd?.toString()} is null or " +
			"s.birthDate BETWEEN :#{#filter?.birthDateRangeStart} AND :#{#filter.birthDateRangeEnd}) AND " +
			"(:#{#filter?.studentName} is null or s.studentName LIKE %:#{#filter?.studentName}%) AND " +
			"(:#{#filter?.npm} is null or s.npm = :#{#filter?.npm}) " +
			"")
	List<Student> findWithManualQuery(@Param("filter") StudentFilter filter);

	List<Student> findByActive(boolean active);

}
