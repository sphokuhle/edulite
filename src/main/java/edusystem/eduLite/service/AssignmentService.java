package edusystem.eduLite.service;

import java.security.Principal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edusystem.eduLite.dto.AssignmentDto;
import edusystem.eduLite.dto.MarkDto;
import edusystem.eduLite.dto.StudentDto;
import edusystem.eduLite.dto.SubjectDto;
import edusystem.eduLite.dto.UserAssignmentDto;
import edusystem.eduLite.model.AssignmentModel;
import edusystem.eduLite.util.FailureResponse;
import edusystem.eduLite.util.ResponseBuilder;

@Stateless
public class AssignmentService {
	@Inject 
	AssignmentModel assignmentModel;
	
	@Inject ResponseBuilder responseBuilder;
	
	public Response createAssignment(Principal p, AssignmentDto as) {
		AssignmentDto assignmetDto = assignmentModel.createAssignment(p, as);
		if(assignmetDto == null) {
			responseBuilder.buildResponse(Status.INTERNAL_SERVER_ERROR, new FailureResponse("Failed to add an assessment"));
		}
		return responseBuilder.buildResponse(Status.OK, assignmetDto);
	}
	
	public Response addMarks(Principal p, UserAssignmentDto dto) {
		UserAssignmentDto userAssignmentDto = assignmentModel.addMarks(p, dto);
		if(userAssignmentDto == null) {
			responseBuilder.buildResponse(Status.INTERNAL_SERVER_ERROR, new FailureResponse("Failed to add marks of a student for an assessment"));
		}
		return responseBuilder.buildResponse(Status.OK, userAssignmentDto);
	}
	
	public Response viewAssignmentBySubject(SubjectDto subjectDto) {
		List<AssignmentDto> list = assignmentModel.viewAssignmentBySubject(subjectDto);
		if(list == null) {
			responseBuilder.buildResponse(Status.INTERNAL_SERVER_ERROR, new FailureResponse("Internal Server Error"));
		}
		return responseBuilder.buildResponse(Status.OK, list);
	}
	
	public Response getAssignmentById(AssignmentDto assignmentDto) {
		AssignmentDto _assignmentDto = assignmentModel.getAssignmentById(assignmentDto);
		if(_assignmentDto == null) {
			responseBuilder.buildResponse(Status.NOT_FOUND, new FailureResponse("Assessment does not exist"));
		}
		return responseBuilder.buildResponse(Status.OK, _assignmentDto);
	}
	
	public Response deleteAssignment(AssignmentDto assignmentDto) {
		int deletionStatus = assignmentModel.deleteAssignment(assignmentDto.getAssignmentId());
		if(deletionStatus > 0) {
			return responseBuilder.buildResponse(Status.OK, new FailureResponse("Assessment successfully deleted"));
		}
		else {
			if(deletionStatus == 0) {
				return responseBuilder.buildResponse(Status.NOT_FOUND, new FailureResponse("Assignment you are attempting to delete does not exist"));
			}
			else if(deletionStatus == -1) {
				return responseBuilder.buildResponse(Status.NOT_ACCEPTABLE, new FailureResponse("Cannot delete an assessment that already have student marks."));
			}
			else {
				return responseBuilder.buildResponse(Status.INTERNAL_SERVER_ERROR, new FailureResponse("Failed to delete an assessment"));
			}
		}
	}
	
	public Response viewMarks(StudentDto studentDto) {
		List<MarkDto> marks = assignmentModel.viewMarks(studentDto);
		if(marks != null) {
			return responseBuilder.buildResponse(Status.OK,marks);
		}
		return responseBuilder.buildResponse(Status.INTERNAL_SERVER_ERROR, new FailureResponse("Failed to read marks"));
	}
	
	public Response updateMarks(Principal p, UserAssignmentDto userAssignment) {
		UserAssignmentDto userMark = assignmentModel.updateMarks(p, userAssignment);
		if(userMark != null) {
			return responseBuilder.buildResponse(Status.OK,userMark);
		}
		return responseBuilder.buildResponse(Status.INTERNAL_SERVER_ERROR, new FailureResponse("Failed to update marks"));
	}
}
