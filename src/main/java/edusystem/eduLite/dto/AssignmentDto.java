package edusystem.eduLite.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import edusystem.eduLite.entity.Assignment;
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
public class AssignmentDto {

	private Long assignmentId;

	private Timestamp createdDate;

	private String createdUser;

	private String description;

	private Timestamp dueDate;

	private Timestamp lasttouchedDate;

	private String lasttouchedUser;

	private Integer markAllocation;

	private BigDecimal markPercentage;

	private String name;

	private Integer studentMark;

	private Integer subjectId;
	
	public AssignmentDto(Assignment assignment) {
		this.assignmentId = assignment.getAssignmentId();
		this.createdDate = assignment.getCreatedDate();
		this.createdUser = assignment.getCreatedUser();
		this.description = assignment.getDescription();
		this.dueDate = assignment.getDueDate();
		this.lasttouchedDate = assignment.getLasttouchedDate();
		this.lasttouchedUser = assignment.getLasttouchedUser();
		this.markAllocation = assignment.getMarkAllocation();
		this.markPercentage = assignment.getMarkPercentage();
		this.name = assignment.getName();
		this.studentMark = assignment.getStudentMark();
		this.subjectId = assignment.getSubject().getSubjectId();
	}
}
