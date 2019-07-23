package edusystem.eduLite.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import edusystem.eduLite.entity.UserAssignment;
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
public class MarkDto {
	
	private Long markId;
	
	private String createdUser;

	private String description;

	private Timestamp dueDate;

	private Timestamp lasttouchedDate;

	private String lasttouchedUser;

	private Integer markAllocation;

	private BigDecimal markPercentage;

	private String name;

	private Integer studentMark;
	
	private Long assignmentId;
	
	private Long userId;
	
	private StudentDto userDto;
	
	public MarkDto(UserAssignment userAssignment) {
		this.markId = userAssignment.getUserAssignmentId();
		this.createdUser = userAssignment.getCreatedUser();
		this.description = userAssignment.getDescription();
		this.dueDate = userAssignment.getDueDate();
		this.lasttouchedDate = userAssignment.getLasttouchedDate();
		this.lasttouchedUser = userAssignment.getLasttouchedUser();
		this.markAllocation = userAssignment.getMarkAllocation();
		this.markPercentage = userAssignment.getMarkPercentage();
		this.name = userAssignment.getAssessmentType();
		this.studentMark = userAssignment.getStudentMark();
		this.assignmentId = userAssignment.getAssignment().getAssignmentId();
		this.userId = userAssignment.getUser().getUserId();
		this.userDto = new StudentDto(userAssignment.getUser());
	}
}
