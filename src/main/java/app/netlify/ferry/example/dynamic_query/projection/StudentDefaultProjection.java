package app.netlify.ferry.example.dynamic_query.projection;

import javax.persistence.Column;
import java.time.LocalDate;

/************************
 * Made by [MR Ferry™]  *
 * on July 2022         *
 ************************/

public interface StudentDefaultProjection{
	String getNpm();
	String getStudentName();
	boolean isActive();
	LocalDate getBirthDate();
}
