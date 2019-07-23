package edusystem.eduLite.dto;

import edusystem.eduLite.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectDto {
	
	private Integer subjectId;

	private String name;

	private String stream;

	private String subjectCode;
	
	//List<AssignmentDto> assignments;
	
	public SubjectDto(Subject subject) {
		this.subjectId = subject.getSubjectId();
		this.name = subject.getName();
		this.stream = subject.getStream();
		this.subjectCode = subject.getSubjectCode();
		/*this.assignments = new ArrayList<AssignmentDto>();
		for(Assignment assignment: subject.getAssignments()) {
			this.assignments.add(new AssignmentDto(assignment));
		}*/
	}
}
