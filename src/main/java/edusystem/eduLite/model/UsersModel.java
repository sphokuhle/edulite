package edusystem.eduLite.model;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import edusystem.eduLite.dto.StreamDto;
import edusystem.eduLite.dto.StudentDto;
import edusystem.eduLite.dto.UsersDto;
import edusystem.eduLite.entity.Grade;
import edusystem.eduLite.entity.Stream;
import edusystem.eduLite.entity.User;
import edusystem.eduLite.entity.UserAssignment;
import edusystem.eduLite.entity.UserSubject;
import edusystem.eduLite.entity.Usertype;

@Stateless
public class UsersModel {
	@PersistenceContext
	EntityManager em;

	Logger logger = Logger.getLogger(UsersModel.class.getCanonicalName());

	public StudentDto findUserById(Long userId) {
		try {
			User user = em.find(User.class, userId);
			if(user != null) {
				return new StudentDto(user);
			}
			logger.log(Level.WARNING, "User Id: {0} not found; method: findUserById()", userId);
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "Error in method: findUserById()", e);
			return null;
		}
	}
	
	public List<StudentDto> getUsersByType(Integer usertypeId){
		TypedQuery<User> query = em.createNamedQuery("User.findAllByUserType", User.class)
				.setParameter("usertypeId", usertypeId);
		List<StudentDto> list = new ArrayList<>();
		if(query != null) {
			for (User type : query.getResultList()) {
				list.add(new StudentDto(type));
			}
			return list;
		}
		return Collections.emptyList();	
	}

	public List<StudentDto> getStudentByGrade(Integer gradeId) {
		try {
			TypedQuery<User> users = em.createNamedQuery(User.FINDBYGRADE, User.class)
					.setParameter("gradeId", gradeId)
					.setParameter("usertypeId", 1);
			List<StudentDto> list = new ArrayList<StudentDto>();
			if (users != null) {
				for (User user : users.getResultList()) {
					list.add(new StudentDto(user));
				}
			}
			logger.log(Level.INFO, "Number of Users: {0}", list.size());
			return list;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: getStudentByGrade()", e);
			return null;
		}
	}

	public List<StudentDto> getStudentBySubject(int subjectId, int gradeId){
		try {			
			List<StudentDto> list = new ArrayList<StudentDto>();
			
			String strQuery = "SELECT DISTINCT  _users FROM User _users JOIN _users.usertype usrType JOIN UserSubject _usersubject "
					+ "JOIN _usersubject.subject usrSubject "
					+ "JOIN _usersubject.user usrUser ON (usrUser.userId = _users.userId) " + 
					"INNER JOIN Subject _subject ON (usrSubject.subjectId = _subject.subjectId) " + 
					"WHERE (usrSubject.subjectId = "+subjectId+" AND usrType.usertypeId = 1 AND _users.gradeId = "+gradeId+")";
			
			TypedQuery<User> query = em.createQuery(strQuery, User.class);
			if(query != null) {
				for(User user: query.getResultList()) {
					list.add(new StudentDto(user));
				}
			}
			
			logger.log(Level.INFO, "Number of Users: {0}",list.size());
			return list;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: getStudentBySubject()", e);
			return null;
		}
	}
	
	/*public User findUserByName(Principal p) {
		TypedQuery<User> user = em.createNamedQuery(User.FINDBYNAME, User.class).setParameter("firstname", p.getName());
		try {
			if (user != null) {
				return user.getSingleResult();
			}
			return null;
		} catch (NonUniqueResultException e) {
			logger.log(Level.INFO, "Found more than one username match");
			return user.getResultList().get(0);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: findUserByName()", e);
			return null;
		}
	}*/

	/*private UserSubject getUserSubject(UserSubjectDto userSubjectDto) {
		try {
			TypedQuery<UserSubject> query = em.createNamedQuery(UserSubject.FINDBYUSERANDSUBJECT, UserSubject.class)
					.setParameter("userId", userSubjectDto.getUserId())
					.setParameter("subjectId", userSubjectDto.getSubjectId());
			if (query != null) {
				if (!query.getResultList().isEmpty()) {
					return query.getResultList().get(0);
				}
			}
			logger.log(Level.SEVERE, "No User = {0} && Subject = " + userSubjectDto.getSubjectId()
					+ " combination was found in method: getUserSubject()", userSubjectDto.getUserId());
			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: getUserSubject()", e);
			return null;
		}
	}*/

	/*public Grade getGradeByUser(Long userId) {
		try {
			TypedQuery<Grade> query = em.createNamedQuery("Grade.findByUser", Grade.class).setParameter("userId",
					userId);
			if (query != null) {
				return query.getSingleResult();
			}
			logger.log(Level.WARNING, "Could not find Grade by userId '{0}'; method: getGradeByUser()", userId);
			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: getGradeByUser()", e);
			return null;
		}
	}*/

	public List<StudentDto> getStudentsByGrade(StudentDto studentDto) {
		try {
			List<StudentDto> list = new ArrayList<StudentDto>();// usertypeId
			TypedQuery<User> query = em.createNamedQuery(User.FINDBYGRADE, User.class)
					.setParameter("gradeId", studentDto.getGradeId())
					.setParameter("usertypeId", 1);
			if (query != null) {
				for (User student : query.getResultList()) {
					list.add(new StudentDto(student));
				}
			}
			return list;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: getStudentsByGrade()", e);
			return Collections.emptyList();
		}
	}
	
	public StreamDto addStreams(String name) {
		try {
			Stream stream = new Stream();
			stream.setName(name);
			em.persist(stream);
			logger.log(Level.INFO, "Stream Added successfully");
			return new StreamDto(stream);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: addStreams()", e);
			return null;
		}
	}

	public StudentDto addUser(Principal p, UsersDto student) {
		User user = new User();
		Usertype userType = em.find(Usertype.class, student.getUsertypeId());
		logger.log(Level.INFO, "User Type: " + userType.toString());

		try {
			if (validateGrade(student.getGradeId())) {
				user.setCellnumber(student.getCellnumber());
				user.setCreatedby(p.getName());
				user.setCreateddate(new Timestamp(System.currentTimeMillis()));
				user.setEmailAddress(student.getEmailAddress());
				user.setFirstname(student.getFirstname());
				user.setSecondName(student.getSecondName());
				user.setLastName(student.getLastName());
				user.setPhysicalAddress(student.getPhysicalAddress());
				user.setUsertype(userType);
				user.setUsername(student.getUsername());
				user.setPassword(student.getPassword());
				user.setGradeId(student.getGradeId());
				em.persist(user);
				em.flush();
				logger.log(Level.INFO, "Student added successfully");
				return new StudentDto(user);
			}
			logger.log(Level.WARNING, "Could not find the Grade for the student.");
			return null;

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: addStudent()", e);
			return null;
		}
	}

	public StudentDto getLoggedInUserByUsername(Principal p) {
		try {
			TypedQuery<User> query = em.createNamedQuery(User.FINDBYUSERNAME, User.class)
					.setParameter("username", p.getName());
			if (query != null) {
				return new StudentDto(query.getSingleResult());
			}
			logger.log(Level.WARNING, "Could not find user '{0}'", p.getName());
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "Error in method: getLoggedInUserByUsername()", e);
			return null;
		}
	}

	private boolean validateGrade(Integer gradeId) {
		try {
			Grade grade = em.find(Grade.class, gradeId);
			if (grade != null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: validateGrade()", e);
			return false;
		}
	}

	public String removeStudent(StudentDto studentDto) {
		
		try {
			
			User user = em.find(User.class, studentDto.getUserId());
			if (user != null) {
				
				String strQuery = "DELETE FROM EDUCATION.USERS WHERE (USERTYPE_ID = 1) AND USER_ID = "+user.getUserId();
				String strUserAsgnmntQuery = "DELETE FROM EDUCATION.USER_ASSIGNMENT WHERE USER_ID = "+user.getUserId();
				String strUserSubjectQuery = "DELETE FROM EDUCATION.USER_SUBJECT WHERE USER_ID = "+user.getUserId();
				
				TypedQuery<UserAssignment> userAssignmentsQuery = em.createNamedQuery("UserAssignment.findByUser", UserAssignment.class)
						.setParameter("userId", user.getUserId());
				TypedQuery<UserSubject> userSubjectQuery = em.createNamedQuery(UserSubject.FINDBYUSER, UserSubject.class)
						.setParameter("userId", user.getUserId());
				if(userAssignmentsQuery != null) {
					 int deletedUsrAssignment = em.createQuery(strUserAsgnmntQuery).executeUpdate();
					 logger.log(Level.INFO, "{0} user's assignments deleted for userId: "+user.getUserId(), deletedUsrAssignment);
				}
				
				if(userSubjectQuery != null) {
					 int deletedUsrSubject = em.createQuery(strUserSubjectQuery).executeUpdate();
					 logger.log(Level.INFO, "{0} user's subjects deleted for userId: "+user.getUserId(), deletedUsrSubject);
				}
				
				Query _query = em.createQuery(strQuery);
				int result = _query.executeUpdate();
				logger.log(Level.INFO, "{0} record(s) deleted", result);
				logger.log(Level.INFO, "Successfully deregistered");
				return "Successful";
			} else {
				logger.log(Level.WARNING, "User {0} does not exist to be deleted", studentDto.getUserId());
				return null;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: removeStudent()", e);
			return null;
		}
	}

}
