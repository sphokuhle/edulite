package edusystem.eduLite.dto;

import java.sql.Timestamp;

import edusystem.eduLite.entity.Assignment;
import edusystem.eduLite.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationDto {
	
	private Integer notificationId;

	private Timestamp createdDate;

	private String createdUser;

	private String description;

	private Integer gradeId;

	private Timestamp lasttouchedDate;

	private String lasttouchedUser;

	private Integer subjectId;

	private Long userId;
	
	private String notificationCategory;
	
	public NotificationDto(Notification notification) {
		this.notificationId = notification.getNotificationId();
		this.createdDate = notification.getCreatedDate();
		this.createdUser = notification.getCreatedUser();
		this.description = notification.getDescription();
		this.gradeId = notification.getGradeId();
		this.lasttouchedDate = notification.getLasttouchedDate();
		this.lasttouchedUser = notification.getLasttouchedUser();
		this.subjectId = notification.getSubjectId();
		this.userId = notification.getUserId();
	}
	
	public NotificationDto(Assignment assignment) {
		this.createdDate = assignment.getCreatedDate();
		this.createdUser = assignment.getCreatedUser();
		this.description = assignment.getDescription();
		this.lasttouchedDate = assignment.getLasttouchedDate();
		this.lasttouchedUser = assignment.getLasttouchedUser();
		this.subjectId = assignment.getSubject().getSubjectId();
		if(assignment.getUserAssignments() != null) {
			if(assignment.getUserAssignments().get(0) != null) {
				this.gradeId = assignment.getUserAssignments().get(0).getUser().getGradeId();
			}	
		}
	}
}
