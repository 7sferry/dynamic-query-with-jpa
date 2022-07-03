/************************
 * Made by [MR Ferry™]  *
 * on July 2022         *
 ************************/

package app.netlify.ferry.example.dynamic_query.specification;

import app.netlify.ferry.example.dynamic_query.persistence.Student;
import app.netlify.ferry.example.dynamic_query.filter.StudentFilter;
import app.netlify.ferry.example.dynamic_query.persistence.StudentBatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static app.netlify.ferry.example.dynamic_query.persistence.StudentBatch_.batchYear;
import static app.netlify.ferry.example.dynamic_query.persistence.Student_.*;

@RequiredArgsConstructor
public class StudentSpecification implements Specification<Student>{
	private final StudentFilter studentFilter;

	@Override
	public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder){
		List<Predicate> predicates = new ArrayList<>();
		if(studentFilter.getActive() != null){
			predicates.add(criteriaBuilder.equal(root.get(active), studentFilter.getActive()));
		}
		if(studentFilter.getStudentName() != null){
			predicates.add(criteriaBuilder.like(root.get(studentName),
					'%' + studentFilter.getStudentName() + '%'));
		}
		if(studentFilter.getBirthDateRangeEnd() != null && studentFilter.getBirthDateRangeStart() != null){
			predicates.add(criteriaBuilder.between(root.get(birthDate),
					studentFilter.getBirthDateRangeStart(), studentFilter.getBirthDateRangeEnd()));
		}
		if(studentFilter.getNik() != null){
			predicates.add(criteriaBuilder.equal(root.get(nik), studentFilter.getNik()));
		}
		if(studentFilter.getNpm() != null){
			predicates.add(criteriaBuilder.equal(root.get(npm), studentFilter.getNpm()));
		}
		if(studentFilter.getBatchYear() != null){
			Join<Student, StudentBatch> join = root.join(studentBatch);
			predicates.add(criteriaBuilder.equal(join.get(batchYear),
					studentFilter.getBatchYear()));
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

}
