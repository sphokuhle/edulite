package edusystem.eduLite.model;

import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import edusystem.eduLite.dto.AssignmentDto;
import edusystem.eduLite.dto.MarkDto;
import edusystem.eduLite.dto.NotificationDto;
import edusystem.eduLite.dto.StudentDto;
import edusystem.eduLite.dto.SubjectDto;
import edusystem.eduLite.dto.UserAssignmentDto;
import edusystem.eduLite.entity.Assignment;
import edusystem.eduLite.entity.Subject;
import edusystem.eduLite.entity.User;
import edusystem.eduLite.entity.UserAssignment;

@Stateless
public class AssignmentModel {
	@PersistenceContext
	EntityManager em;
	
	@Inject NotificationModel notfcationModel;

	Logger logger = Logger.getLogger(AssignmentModel.class.getCanonicalName());

	public AssignmentDto createAssignment(Principal p, AssignmentDto as) {

		Assignment assignment = new Assignment();
		Subject subject = null;
		try {
			subject = em.find(Subject.class, as.getSubjectId());
			if(subject != null) {
				assignment.setCreatedDate(as.getCreatedDate());
				assignment.setCreatedUser(p.getName());
				assignment.setDescription(as.getDescription());
				assignment.setDueDate(as.getDueDate());
				assignment.setLasttouchedDate(new Timestamp(System.currentTimeMillis()));
				assignment.setLasttouchedUser(p.getName());
				assignment.setMarkAllocation(as.getMarkAllocation());
				assignment.setMarkPercentage(as.getMarkPercentage());
				assignment.setName(as.getName());
				assignment.setStudentMark(as.getStudentMark());
				assignment.setSubject(subject);
				em.persist(assignment);
				em.flush();
				NotificationDto notificationDto = new NotificationDto(assignment);
				if(notificationDto.getGradeId() != null) {
					notificationDto.setUserId(null);
					notfcationModel.writeNotification(p, notificationDto);
				}
				else {
					logger.log(Level.WARNING, "Could not post the notification about the assignment"
							+ " because the grade was not found; method: addAssignment()");
				}
				
			}else {
				logger.log(Level.WARNING, "Could not find Subject; method: addAssignment()");
			}
			return new AssignmentDto(assignment);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "Error in method: addAssignment()", e);
			return null;
		}
	}
	
	public UserAssignmentDto addMarks(Principal p, UserAssignmentDto dto) {
		UserAssignment userAssignment = new UserAssignment();
		Assignment assignment = em.find(Assignment.class, dto.getAssignmentId());
		User user = em.find(User.class, dto.getUserId());
		if((user != null) && (assignment != null)) {
			userAssignment.setAssessmentType(dto.getAssessmentType());
			userAssignment.setAssignment(assignment);
			userAssignment.setCreatedDate(assignment.getCreatedDate());
			userAssignment.setCreatedUser(assignment.getCreatedUser());
			userAssignment.setDescription(assignment.getDescription());
			userAssignment.setDueDate(assignment.getDueDate());
			userAssignment.setLasttouchedDate(new Timestamp(System.currentTimeMillis()));
			userAssignment.setLasttouchedUser(p.getName());
			userAssignment.setMarkAllocation(assignment.getMarkAllocation());
			userAssignment.setStudentMark(dto.getStudentMark());
			if((assignment.getMarkAllocation() != null & assignment.getMarkAllocation() > 0) 
					&& (dto.getStudentMark() != null & dto.getStudentMark() > 0)) {
				
				float markAllocation = assignment.getMarkAllocation() * 1.0f;
				Double d = Double.valueOf( (dto.getStudentMark()/markAllocation) * 100 );
				userAssignment.setMarkPercentage(new BigDecimal(d));
				
				if(user.getUsertype().getUsertypeId() == 1) {
					userAssignment.setUser(user);
					em.persist(userAssignment);
					return new UserAssignmentDto(userAssignment);
				}
				else {
					logger.log(Level.WARNING, "The user you are attempting to enter marks for is not a student; method: addMarks()");
					return null;
				}
			}
			
		}
		logger.log(Level.WARNING, "Either assignmentId or userId was not found; method: addMarks()");
		return null;
		
	}
	
	public UserAssignmentDto updateMarks(Principal p, UserAssignmentDto dto) {
		UserAssignment userAssignment = em.find(UserAssignment.class, dto.getUserAssignmentId());
		
		Assignment assignment = em.find(Assignment.class, dto.getAssignmentId());
		User user = em.find(User.class, dto.getUserId());
		if (userAssignment != null) {
			if ((user != null) && (assignment != null)) {
				userAssignment.setAssessmentType(dto.getAssessmentType());
				userAssignment.setAssignment(assignment);
				userAssignment.setDescription(assignment.getDescription());
				userAssignment.setDueDate(assignment.getDueDate());
				userAssignment.setLasttouchedDate(new Timestamp(System.currentTimeMillis()));
				userAssignment.setLasttouchedUser(p.getName());
				userAssignment.setMarkAllocation(assignment.getMarkAllocation());
				userAssignment.setStudentMark(dto.getStudentMark());
				if ((assignment.getMarkAllocation() != null & assignment.getMarkAllocation() > 0)
						&& (dto.getStudentMark() != null & dto.getStudentMark() > 0)) {

					float markAllocation = assignment.getMarkAllocation() * 1.0f;
					Double d = Double.valueOf((dto.getStudentMark() / markAllocation) * 100);
					userAssignment.setMarkPercentage(new BigDecimal(d));

					if (user.getUsertype().getUsertypeId() == 1) {
						userAssignment.setUser(user);
						em.persist(userAssignment);
						return new UserAssignmentDto(userAssignment);
					} else {
						logger.log(Level.WARNING,
								"The user you are attempting to enter marks for is not a student; method: addMarks()");
						return null;
					}
				}

			} 
		}
		logger.log(Level.WARNING, "Either assignmentId or userId was not found; method: updateMarks()");
		return null;
		
	}

	public AssignmentDto updateAssignment(Principal p, Assignment assignment) {
		Assignment _assignment = em.find(Assignment.class, assignment.getAssignmentId());
		try {
			if (_assignment != null) {
				assignment.setLasttouchedDate(new Timestamp(System.currentTimeMillis()));
				assignment.setLasttouchedUser(p.getName());
				em.merge(assignment);
				return new AssignmentDto(assignment);
			} else {
				logger.log(Level.WARNING, "Could not find the Passed Assignment Object; method: updateAssignment()");
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "Error in method: updateAssignment()", e);
			return null;
		}

	}

	public int deleteAssignment(Long assignmentId) {
		try {
			Query query = null;
			int value = 0;
			TypedQuery<UserAssignment> queryMarks = em.createNamedQuery("UserAssignment.findByAssignment",UserAssignment.class)
					.setParameter("assignmentId", assignmentId);
			if(queryMarks != null) {
				if(queryMarks.getResultList().size() > 0) {
					logger.log(Level.WARNING,"Cannot delete the assignment that already have student marks.");
					return -1;
				}
			}
			if(em.find(Assignment.class, assignmentId) != null) {
				query = em
					.createQuery("DELETE FROM EDUCATION.ASSIGNMENT WHERE ASSIGNMENT_ID = "+assignmentId);
				value = query.executeUpdate();
				logger.log(Level.INFO, "Assignment deleted: {0}", value);
			}
			else {
				logger.log(Level.WARNING, "Assignment {0} does not exist", assignmentId);
				return 0;
			}
			
			Assignment assignment = em.find(Assignment.class, assignmentId);
			if (assignment == null) {
				logger.log(Level.INFO, "Assignment REMOVED successfully");
				return value;
			} else {
				logger.log(Level.WARNING, "Failed to remove an assignment");
				return -2;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: removeAssignment()", e);
			return -3;
		}
	}
	
	public AssignmentDto getAssignmentById(AssignmentDto assignmentDto) {
		try {
			Assignment assignment = em.find(Assignment.class, assignmentDto.getAssignmentId());
			if(assignment != null) {
				return new AssignmentDto(assignment);
			}
			logger.log(Level.WARNING, "Could not find Assignment with ID {0}",assignmentDto.getAssignmentId());
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "Error in method: getAssignmentById()", e);
			return null;
		}	
	}
	
	public List<AssignmentDto> viewAssignmentBySubject(SubjectDto subjectDto){
		List<AssignmentDto> list = new ArrayList<AssignmentDto>();
		try {
			TypedQuery<Assignment> query = em.createNamedQuery(Assignment.FINDBYSUBJECT, Assignment.class).
					setParameter("subjectId", subjectDto.getSubjectId());
			if(query != null) {
				for (Assignment assignment : query.getResultList()) {
					list.add(new AssignmentDto(assignment));
				}
			}
			return list;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: viewAssignmentBySubject()", e);
			return null;
		}
		
	}
	
	public List<MarkDto> viewMarks(StudentDto studentDto) {
		try {
			List<MarkDto> marks = new ArrayList<>();
			TypedQuery<UserAssignment> query = em.createNamedQuery("UserAssignment.findByUser", UserAssignment.class)
					.setParameter("userId", studentDto.getUserId()); 
			if(query != null) {
				for (UserAssignment userAssignment : query.getResultList()) {
					marks.add(new MarkDto(userAssignment));
				}
			}
			logger.log(Level.INFO, "{0} Assessment Results", marks.size());
			return marks;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: viewMarks()",e);
			return null;
		}
	}
	
}
