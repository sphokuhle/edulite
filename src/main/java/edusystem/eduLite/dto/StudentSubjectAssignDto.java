package edusystem.eduLite.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edusystem.eduLite.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentSubjectAssignDto {
	private Long userId;

	private String cellnumber;

	private String createdby;

	private Timestamp createddate;
	
	private String emailAddress;

	private String firstname;

	private String lastName;

	private String physicalAddress;

	private String secondName;
	
	private Integer gradeId;
	
	private Integer usertypeId;
	
	private String teacherGrades;
	
	private List<SubjectDto> subjectIDs;
	
	public StudentSubjectAssignDto(User user) {
		this.userId = user.getUserId();
		this.cellnumber = user.getCellnumber();
		this.createdby = user.getCreatedby();
		this.createddate = user.getCreateddate();
		this.emailAddress = user.getEmailAddress();
		this.firstname = user.getFirstname();
		this.lastName = user.getLastName();
		this.physicalAddress = user.getPhysicalAddress();
		this.secondName = user.getSecondName();
		this.gradeId = user.getGradeId();
		this.usertypeId = user.getUsertype().getUsertypeId();
		this.teacherGrades = user.getTeacherGrades();
		this.subjectIDs = new ArrayList<>();
	}
}
