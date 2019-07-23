package edusystem.eduLite.dto;

import edusystem.eduLite.entity.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StreamDto {
	private Integer facultyId;
	private String name;
	
	public StreamDto(Stream stream) {
		this.facultyId = stream.getFacultyId();
		this.name = stream.getName();
	}
	
}
