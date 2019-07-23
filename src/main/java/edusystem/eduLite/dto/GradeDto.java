package edusystem.eduLite.dto;

import edusystem.eduLite.entity.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GradeDto {
	private Integer gradeId;
	private String gradeCode;
	private String location;
	private String name;
	private Long userId;
	
	public GradeDto(Grade grade) {
		this.gradeId = grade.getGradeId();
		this.gradeCode = grade.getGradeCode();
		this.location = grade.getLocation();
		this.name = grade.getName();
		this.userId = grade.getUserId();
	}
}
