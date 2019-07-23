package edusystem.eduLite.dto;

import java.sql.Timestamp;

import edusystem.eduLite.entity.SubjectGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectGradeDto {
	private Integer subjectGradeId;
	
	private Timestamp createdDate;
	
	private String description;
	
	private Integer subjectId;

	private Integer gradeId;
	
	public SubjectGradeDto(SubjectGrade subjectGrade) {
		this.subjectGradeId = subjectGrade.getSubjectGradeId();
		this.description = subjectGrade.getDescription();
		this.subjectId = subjectGrade.getSubject().getSubjectId();
		this.gradeId = subjectGrade.getGrade().getGradeId();
	}
}
