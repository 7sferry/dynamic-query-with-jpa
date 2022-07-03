package app.netlify.ferry.example.dynamic_query.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Score implements Serializable{
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	private Subject subject;

	@Column(length = 25)
	private String name;

	@Column(nullable = false)
	private double scoreValue;

	@Version
	private Integer version;
}
