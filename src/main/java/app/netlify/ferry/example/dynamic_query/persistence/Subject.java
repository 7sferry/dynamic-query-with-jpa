package app.netlify.ferry.example.dynamic_query.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code", "grade"}))
public class Subject implements Serializable{
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 10)
	@Enumerated(EnumType.STRING)
	private SubjectCode code;

	@Column(nullable = false)
	private String grade;

	@Column(length = 25, nullable = false)
	private String name;

	private double minimumCriteria;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "subjects")
	private Collection<Student> students;

	@Version
	private Integer version;
}
