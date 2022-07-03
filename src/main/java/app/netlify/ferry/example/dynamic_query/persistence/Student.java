package app.netlify.ferry.example.dynamic_query.persistence;

import app.netlify.ferry.example.dynamic_query.persistence.Score;
import app.netlify.ferry.example.dynamic_query.persistence.StudentBatch;
import app.netlify.ferry.example.dynamic_query.persistence.Subject;
import app.netlify.ferry.example.dynamic_query.persistence.UserLogin;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
@Entity
public class Student implements Serializable{
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NaturalId
	@Column(unique = true, length = 10, nullable = false)
	private String npm;

	@Column(unique = true, nullable = false, length = 16)
	private String nik;

	@Column(length = 25, nullable = false)
	private String studentName;

	@Column(name = "is_active", nullable = false)
	private boolean active;

	@Column(nullable = false)
	private LocalDate birthDate;

	@ManyToMany(fetch = FetchType.LAZY)
	private Collection<Subject> subjects;

	@OneToOne(fetch = FetchType.LAZY)
	private UserLogin userLogin;

	@Column(name = "user_login_id", insertable = false, updatable = false, unique = true)
	private Long userLoginId;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
	private Collection<Score> scores;

	@ManyToOne(fetch = FetchType.LAZY)
	private StudentBatch studentBatch;

	@Column(name = "student_batch_id", insertable = false, updatable = false)
	private Long studentBatchId;

	@Version
	private Integer version;

}
