package edusystem.eduLite.service;

import java.security.Principal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edusystem.eduLite.dto.GradeDto;
import edusystem.eduLite.dto.StreamDto;
import edusystem.eduLite.dto.StudentDto;
import edusystem.eduLite.dto.UserSubjectDto;
import edusystem.eduLite.dto.UsersDto;
import edusystem.eduLite.model.UsersModel;
import edusystem.eduLite.util.FailureResponse;
import edusystem.eduLite.util.ResponseBuilder;

@Stateless
public class UsersService {
	@Inject
	UsersModel userModel;
	
	@Inject 
	ResponseBuilder responseBuilder;
	
	public Response getLoggedUser(Principal p) {
		StudentDto user = userModel.getLoggedInUserByUsername(p);
		if(user == null) {
			return responseBuilder.buildResponse(Status.NOT_FOUND, new FailureResponse("User was not found"));
		}
		return responseBuilder.buildResponse(Status.OK, user);
	}
	
	public Response getUserById(StudentDto studentDto) {
		StudentDto user = userModel.findUserById(studentDto.getUserId());
		if(user == null) {
			return responseBuilder.buildResponse(Status.NOT_FOUND, new FailureResponse("User was not found"));
		}
		return responseBuilder.buildResponse(Status.OK, user);
	}
	
	public Response getStudentByGrade(GradeDto gradeDto) {
		List<StudentDto> list = userModel.getStudentByGrade(gradeDto.getGradeId());
		if(list == null) {
			return responseBuilder.buildResponse(Status.SEE_OTHER, new FailureResponse("Failed to return a list of students"));
		}
		return responseBuilder.buildResponse(Status.OK, list);
	}
	
	public Response getStudentsBySubject(UserSubjectDto userSubjectDto, int gradeId) {
		List<StudentDto> list = userModel.getStudentBySubject(userSubjectDto.getSubjectId(), gradeId);
		if(list == null) {
			return responseBuilder.buildResponse(Status.SEE_OTHER, new FailureResponse("Failed to return a list of students"));
		}
		return responseBuilder.buildResponse(Status.OK, list);
	}
	
	public Response addStream(StreamDto streamDto) {
		StreamDto stream = userModel.addStreams(streamDto.getName());
		if(stream == null) {
			return responseBuilder.buildResponse(Status.INTERNAL_SERVER_ERROR, new FailureResponse("Failed to add a stream"));
		}
		return responseBuilder.buildResponse(Status.OK, stream);
	}
	
	public Response addUser(Principal p, UsersDto student) {
		StudentDto studentDto = userModel.addUser(p, student);
		if(studentDto == null) {
			return responseBuilder.buildResponse(Status.INTERNAL_SERVER_ERROR, new FailureResponse("Failed to add a student"));
		}
		return responseBuilder.buildResponse(Status.OK, studentDto);
	}
	
	public Response deregisterStudent(StudentDto studentDto) {
		String deregStatus = userModel.removeStudent(studentDto);
		if(deregStatus == null) {
			return responseBuilder.buildResponse(Status.INTERNAL_SERVER_ERROR, new FailureResponse("Failed to deregister a student"));
		}
		return responseBuilder.buildResponse(Status.OK, new FailureResponse("Student deregistered successfully"));
	}
}
