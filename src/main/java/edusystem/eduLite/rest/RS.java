package edusystem.eduLite.rest;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import edusystem.eduLite.App;
import edusystem.eduLite.dto.AssignmentDto;
import edusystem.eduLite.dto.GradeDto;
import edusystem.eduLite.dto.NotificationDto;
import edusystem.eduLite.dto.StreamDto;
import edusystem.eduLite.dto.StudentDto;
import edusystem.eduLite.dto.SubjectDto;
import edusystem.eduLite.dto.UserAssignmentDto;
import edusystem.eduLite.dto.UserSubjectDto;
import edusystem.eduLite.dto.UsersDto;
import edusystem.eduLite.model.LookUpModel;
import edusystem.eduLite.service.AssignmentService;
import edusystem.eduLite.service.NotificationService;
import edusystem.eduLite.service.SubjectService;
import edusystem.eduLite.service.UsersService;
import edusystem.eduLite.util.ResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Stateless
@Path("/edulite/")
@Api( value = "Edulite:  This is an Educational system application." )
public class RS {
	Logger logger = Logger.getLogger(RS.class.getCanonicalName());
	
	@Inject App app;
	
	@Inject ResponseBuilder responseBuilder;
	
	@Inject LookUpModel lookUp;
	
	@Inject UsersService userService;
	
	@Inject SubjectService subjectService;
	
	@Inject AssignmentService assignmentService;
	
	@Inject NotificationService notificationService;
	
	@GET
    @Path("echo")
    @Produces("text/plain")
    public Response echo() {
        return Response.ok(app.echo()).build();
    }
	
	@POST
    @Path("grades")
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to get a list of all grades." )
	public Response getClasses() {
		return responseBuilder.buildResponse(Status.OK, lookUp.getClasses());
	}
	
	@POST
    @Path("getGradesByTeacherId")
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to get a list of grades assigned to the teacher." )
	public Response getGradesByTeacherId(StudentDto studentDto) {
		return responseBuilder.buildResponse(Status.OK, lookUp.getGradesByUser(studentDto));
	}
	
	@POST
    @Path("streams")
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to get a list of all streams/faculties." )
	public Response getStreams() {
		return responseBuilder.buildResponse(Status.OK, lookUp.getStreams());
	}
	
	@POST
	@Path("users")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to get a list of all users." )
	public Response getAllUsers() {
		return responseBuilder.buildResponse(Status.OK, lookUp.getAllUsers());
	}
	
	@POST
    @Path("usertypes")
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to get all types of users." )
	public Response getUserTypes() {
		return responseBuilder.buildResponse(Status.OK, lookUp.getUserTypes());
	}
	
	@POST
    @Path("getLoggedUser")
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to get a logged in user." )
	public Response getLoggedInUserByUserName(@Context SecurityContext security) {
		return userService.getLoggedUser(security.getUserPrincipal());
	}
	
	@POST
    @Path("addStream")
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to add a stream into the database." )
	public Response addStream(StreamDto streamDto) {
		return userService.addStream(streamDto);
	}
	
	@POST
    @Path("getStudentByGrade")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to get a list of all students by grade." )
	public Response getStudentByGrade(GradeDto gradeDto) {
		return userService.getStudentByGrade(gradeDto);
	}
	
	@POST
    @Path("getStudentBySubject/{gradeId}")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to get a list of all students by subject and grade." )
	public Response getStudentBySubject(UserSubjectDto userSubjectDto, @PathParam("gradeId") int gradeId) {
		return userService.getStudentsBySubject(userSubjectDto, gradeId);
	}
	
	@POST
    @Path("getUserById")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to get a user by id." )
	public Response getUserById(StudentDto studentDto) {
		return userService.getUserById(studentDto);
	}
	
	@POST
    @Path("registerStudent")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to add a user (specifically a student)." )
	public Response registerStudent(@Context SecurityContext security, UsersDto userDto) {
		userDto.setUsertypeId(1);
		return userService.addUser(security.getUserPrincipal(), userDto);
	}
	
	@POST
    @Path("registerTeacher")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to add a user (specifically a teacher)." )
	public Response registerTeacher(@Context SecurityContext security, UsersDto userDto) {
		userDto.setUsertypeId(2);
		return userService.addUser(security.getUserPrincipal(), userDto);
	}
	
	@DELETE
    @Path("deregisterUser")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to get delete a user/student." )
	public Response registerStudent(StudentDto userDto) {
		return userService.deregisterStudent(userDto);
	}
	
	@POST
    @Path("assignSubjectToStudent/{subjectId}")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to assign a subject to a student." )
	public Response assignSubjectToStudent(StudentDto studentDto,  @PathParam("subjectId") int subjectId) {
		return subjectService.assignSubject(studentDto, subjectId);
	}
	
	@POST
    @Path("assignSubjectToTeacher/{subjectId}")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to assign a subject to a teacher." )
	public Response assignSubjectToTeacher(StudentDto studentDto,  @PathParam("subjectId") int subjectId) {
		return subjectService.assignSubjectToTeacher(studentDto, subjectId);
	}
	
	@POST
    @Path("getSubjectsByStudent")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to retrieve a list of subjects assigned to a student/user." )
	public Response assignSubjectToStudent(StudentDto studentDto) {
		return subjectService.getSubjectsByStudent(studentDto);
	}
	
	@POST
    @Path("getAssessmentById")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to get an assessment by Id." )
	public Response getAssessmentById(AssignmentDto assignmentDto) {
		return assignmentService.getAssignmentById(assignmentDto);
	}
	
	@POST
    @Path("viewAssessmentBySubject")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to get a list of assessments assigned for a subject." )
	public Response getAssessmentById(SubjectDto subjectDto) {
		return assignmentService.viewAssignmentBySubject(subjectDto);
	}
	
	@POST
    @Path("createAssessment")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to create/post an assessment." )
	public Response createAssessment(@Context SecurityContext security, AssignmentDto as) {
		return assignmentService.createAssignment(security.getUserPrincipal(), as);
	}
	
	@POST
    @Path("addMarks")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to add marks of a student to the specific assessment." )
	public Response addMarks(@Context SecurityContext security, UserAssignmentDto dto) {
		return assignmentService.addMarks(security.getUserPrincipal(), dto);
	}
	
	@DELETE
    @Path("deleteAssessment")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to remove an assessment if no marks allocated to students for it." )
	public Response deleteAssessment(AssignmentDto dto) {
		return assignmentService.deleteAssignment(dto);
	}
	
	
	@POST
	@Path("viewMarks")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to view assessment result by user/student." )
	public Response viewMarks(StudentDto studentDto) {
		return assignmentService.viewMarks(studentDto);
	}
	
	@PUT
	@Path("updateMarks")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to update marks of a student to the specific assessment." )
	public Response updateMarks(@Context SecurityContext security, UserAssignmentDto dto) {
		return assignmentService.updateMarks(security.getUserPrincipal(), dto);
	}
	
	@POST
	@Path("writeNotification")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( value = "This operation is used to add notifications." )
	public Response writeNotification(@Context SecurityContext security, NotificationDto notificationDto) {
		return notificationService.writeNotification(security.getUserPrincipal(), notificationDto);
	}
	
}
