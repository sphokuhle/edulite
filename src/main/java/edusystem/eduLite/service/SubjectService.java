package edusystem.eduLite.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edusystem.eduLite.dto.StudentDto;
import edusystem.eduLite.dto.StudentSubjectAssignDto;
import edusystem.eduLite.dto.SubjectDto;
import edusystem.eduLite.dto.UserSubjectDto;
import edusystem.eduLite.model.SubjectModel;
import edusystem.eduLite.util.FailureResponse;
import edusystem.eduLite.util.ResponseBuilder;

@Stateless
public class SubjectService {
	@Inject 
	SubjectModel subjectModel;
	
	@Inject ResponseBuilder response;
	
	public Response assignSubject(StudentDto studentDto, Integer subjectId) {
		UserSubjectDto userSubjectDto = subjectModel.assignSubject(studentDto, subjectId);
		if(userSubjectDto == null) {
			return response.buildResponse(Status.INTERNAL_SERVER_ERROR, new FailureResponse("Failed to assign subject to the student"));
		}
		return response.buildResponse(Status.OK, userSubjectDto);
	}
	
	public Response _assignSubject(StudentSubjectAssignDto studentDto) {
		List<UserSubjectDto> userSubjectDto = subjectModel._assignSubjectToStudent(studentDto);
		if(userSubjectDto == null) {
			return response.buildResponse(Status.INTERNAL_SERVER_ERROR, new FailureResponse("Failed to assign subject to the student"));
		}
		return response.buildResponse(Status.OK, userSubjectDto);
	}
	
	public Response getSubjectsByStudent(StudentDto studentDto) {
		List<SubjectDto> subjects = subjectModel.getSubjectsByStudent(studentDto);
		if(subjects == null) {
			return response.buildResponse(Status.NOT_FOUND, new FailureResponse("Failed to retrieve subjects assigned to the student"));
		}
		return response.buildResponse(Status.OK, subjects);
	}
	
	public Response getSubjects() {
		List<SubjectDto> subjects = subjectModel.getSubjects();
		if(subjects == null) {
			return response.buildResponse(Status.NOT_FOUND, new FailureResponse("Failed to retrieve all subjects"));
		}
		return response.buildResponse(Status.OK, subjects);
	}
	
	public Response assignSubjectToTeacher(StudentDto studentDto, int subjectId) {
		int status = subjectModel.assignSubjectToTeacher(studentDto, subjectId);
		if(status == 0) {
			return response.buildResponse(Status.INTERNAL_SERVER_ERROR, new FailureResponse("Failed to assign subject to the teacher"));
		}
		else if(status == -1) {
			return response.buildResponse(Status.UNAUTHORIZED, new FailureResponse("This user is not registered as a teacher"));
		}
		else if(status == -2) {
			return response.buildResponse(Status.NOT_ACCEPTABLE, new FailureResponse("The grade for a subject was not selected"));
		}
		return response.buildResponse(Status.OK, new FailureResponse("Successfully assigned a subject to a teacher"));
	}
}
