package app.netlify.ferry.example.dynamic_query.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Getter
@Setter
public class UserLogin implements Serializable{
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String userName;

	@Column(length = 32, nullable = false)
	private String password;

	private Instant lastLogin;

	@Column(name = "is_active", nullable = false)
	private boolean active;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserType type;

	@Version
	private Integer version;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "userLogin")
	private Student student;

}
