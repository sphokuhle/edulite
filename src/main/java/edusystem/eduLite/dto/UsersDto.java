package edusystem.eduLite.dto;

import java.sql.Timestamp;

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
public class UsersDto {

	private Long userId;

	private String cellnumber;

	private String createdby;

	private Timestamp createddate;
	
	private String emailAddress;

	private String firstname;

	private String lastName;

	private String password;

	private String physicalAddress;

	private String secondName;
	
	private String username;
	
	private Integer gradeId;
	
	private Integer usertypeId;
	
	private String teacherGrades;
	
	public UsersDto(User user) {
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
	}

}
