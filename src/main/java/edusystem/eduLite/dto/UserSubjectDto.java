package edusystem.eduLite.dto;

import java.sql.Timestamp;

import edusystem.eduLite.entity.UserSubject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserSubjectDto {
	private Integer userSubjectId;
	
	private Timestamp createdDate;

	private String description;

	private Long userId;

	private Integer subjectId;
	
	public UserSubjectDto(UserSubject userSubject) {
		this.subjectId = userSubject.getUserSubjectId();
		this.createdDate = userSubject.getCreatedDate();
		this.description = userSubject.getDescription();
		this.userId = userSubject.getUser().getUserId();
		this.subjectId = userSubject.getSubject().getSubjectId();
	}
}
