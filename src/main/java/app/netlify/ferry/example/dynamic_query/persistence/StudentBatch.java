package app.netlify.ferry.example.dynamic_query.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "unique_batches", columnNames = {"batchYear", "batchMajor"}))
public class StudentBatch implements Serializable{
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private int batchYear;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private StudentMajor batchMajor;

	@Column(length = 15, nullable = false)
	private String batchName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "studentBatch")
	private Collection<Student> students;

	@Version
	private Integer version;

}
