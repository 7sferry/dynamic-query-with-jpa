/************************
 * Made by [MR Ferry™]  *
 * on July 2022         *
 ************************/

package app.netlify.ferry.example.dynamic_query.filter;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class StudentFilter{
	String npm;
	String nik;
	String studentName;
	Boolean active;
	LocalDate birthDateRangeStart;
	LocalDate birthDateRangeEnd;
	Integer batchYear;
}
