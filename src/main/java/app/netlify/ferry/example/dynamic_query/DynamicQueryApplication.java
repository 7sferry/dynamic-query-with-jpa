package app.netlify.ferry.example.dynamic_query;

import app.netlify.ferry.example.dynamic_query.filter.StudentFilter;
import app.netlify.ferry.example.dynamic_query.persistence.Student;
import app.netlify.ferry.example.dynamic_query.persistence.StudentBatch;
import app.netlify.ferry.example.dynamic_query.projection.StudentDefaultProjection;
import app.netlify.ferry.example.dynamic_query.repository.StudentSpringJpaRepository;
import app.netlify.ferry.example.dynamic_query.specification.StudentSpecification;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class DynamicQueryApplication implements CommandLineRunner{
	private final StudentSpringJpaRepository studentRepository;
	private final EntityManager entityManager;

	public static void main(String[] args){
		SpringApplication.run(DynamicQueryApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args){
		StudentFilter studentFilter = StudentFilter.builder()
				.active(true)
				.studentName("s")
				.nik("1313727230300101")
				.birthDateRangeEnd(LocalDate.now())
				.birthDateRangeStart(LocalDate.of(2019, Month.JANUARY, 1))
				.npm("1133080")
				.batchYear(2013)
				.build();

		//find with specification
		StudentSpecification spec = new StudentSpecification(studentFilter);
		List<Student> students = studentRepository.findAll(spec);
		for(Student student : students){
			System.out.println("student.getStudentName() = " + student.getStudentName());
			System.out.println("student.getStudentBatch() = " + student.getStudentBatch());
		}

		//find with manual query
		List<Student> withManualQuery = studentRepository.findWithManualQuery(studentFilter);
		System.out.println("withManualQuery.size() = " + withManualQuery.size());
		StudentBatch studentBatch1 = withManualQuery.get(0).getStudentBatch();
		System.out.println("studentBatch1 = " + studentBatch1.getBatchName());

		//find with manual query join batch table
		List<Student> withManualQueryJoinBatch = studentRepository.findWithManualQueryJoinBatch(studentFilter);
		System.out.println("withManualQueryJoinBatch.size() = " + withManualQueryJoinBatch.size());
		StudentBatch studentBatch = withManualQueryJoinBatch.get(0).getStudentBatch();
		System.out.println("studentBatch = " + studentBatch.getBatchName());

		//jpql standard
		TypedQuery<Student> query = entityManager.createQuery("select s from Student s", Student.class);
		List<Student> list = query.getResultList();
		for(Student student : list){
			System.out.println("student.getStudentName() = " + student.getStudentName());
		}

		//hibernate query standard
		Session session = entityManager.unwrap(Session.class);
		Query<Student> hibernateQuery = session.createQuery("select s from Student s", Student.class);
		List<Student> resultList = hibernateQuery.getResultList();
		for(Student student : resultList){
			System.out.println("student.getStudentName() = " + student.getStudentName());
		}

		//find with spring data jpa
		List<Student> all = studentRepository.findAll();
		for(Student student : all){
			System.out.println("student.getStudentName() = " + student.getStudentName());
		}

		// find with generated filter using "ByColumnName" suffix
		List<Student> byActive = studentRepository.findByActive(true);
		for(Student studentDefaultProjection : byActive){
			System.out.println("studentDefaultProjection.getStudentName() = " + studentDefaultProjection.getStudentName());
		}

		// find with projections
		List<StudentDefaultProjection> projections = studentRepository.findByActive(true, StudentDefaultProjection.class);
		for(StudentDefaultProjection studentDefaultProjection : projections){
			System.out.println("studentDefaultProjection.getStudentName() = " + studentDefaultProjection.getStudentName());
		}

	}

}
