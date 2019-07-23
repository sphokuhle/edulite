package edusystem.eduLite.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import edusystem.eduLite.entity.UserAssignment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAssignmentDto {
	
	private Long userAssignmentId;
	
	private String assessmentType;

	private Timestamp createdDate;

	private String createdUser;

	private String description;

	private Timestamp dueDate;

	private Timestamp lasttouchedDate;

	private String lasttouchedUser;

	private Integer markAllocation;

	private BigDecimal markPercentage;

	private Integer studentMark;
	
	private Long userId;

	private Long assignmentId;
	
	public UserAssignmentDto(UserAssignment userAssignment) {
		this.userAssignmentId = userAssignment.getUserAssignmentId();
		this.assessmentType = userAssignment.getAssessmentType();
		this.createdDate = userAssignment.getCreatedDate();
		this.description = userAssignment.getDescription();
		this.dueDate = userAssignment.getDueDate();
		this.lasttouchedDate = userAssignment.getLasttouchedDate();
		this.lasttouchedUser = userAssignment.getLasttouchedUser();
		this.markAllocation = userAssignment.getMarkAllocation();
		this.markPercentage = userAssignment.getMarkPercentage();
		this.studentMark = userAssignment.getStudentMark();
		this.userId = userAssignment.getUser().getUserId();
		this.assignmentId = userAssignment.getAssignment().getAssignmentId();
	}
}
