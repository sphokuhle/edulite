package edusystem.eduLite.model;

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
import javax.persistence.TypedQuery;

import edusystem.eduLite.dto.NotificationDto;
import edusystem.eduLite.dto.StudentDto;
import edusystem.eduLite.entity.Notification;
import edusystem.eduLite.entity.User;

@Stateless
public class NotificationModel {
	@PersistenceContext
	EntityManager em;
	@Inject
	LookUpModel lookUpModel;

	@Inject
	UsersModel userModel;

	Logger logger = Logger.getLogger(NotificationModel.class.getCanonicalName());

	public NotificationDto writeNotification(Principal p, NotificationDto notificationDto) {
		try {
			boolean gradeAndSubjectSelected = notificationDto.getGradeId() != null
					&& notificationDto.getSubjectId() != null;
			boolean onlyGradeSelected = (notificationDto.getGradeId() != null) && (notificationDto.getSubjectId() == null);
			boolean onlySubjectSelected = (notificationDto.getGradeId() == null)
					&& (notificationDto.getSubjectId() != null);
			NotificationDto ntfcation = null;
			Timestamp createdDate = new Timestamp(System.currentTimeMillis());
			int i = 0;
			if (gradeAndSubjectSelected) {
				List<StudentDto> users = userModel.getStudentBySubject(notificationDto.getSubjectId(),
						notificationDto.getGradeId());
				logger.log(Level.INFO, "Subject and Grade oriented notification has been selected");
				for (StudentDto studentDto : users) {
					Notification notification = new Notification();
					notification.setCreatedDate(createdDate);
					notification.setCreatedUser(p.getName());
					notification.setDescription(notificationDto.getDescription());
					notification.setGradeId(notificationDto.getGradeId());
					notification.setLasttouchedDate(createdDate);
					notification.setLasttouchedUser(p.getName());
					notification.setSubjectId(notificationDto.getSubjectId());
					notification.setUserId(studentDto.getUserId());
					em.persist(notification);
					em.flush();
					i++;
					if (users.size() == i) {
						ntfcation = new NotificationDto(notification);
					}
				}
				logger.log(Level.INFO, "***Notification will posted on dashboards of {0} users", users.size());
				return ntfcation;
			} else if (onlyGradeSelected) {
				List<StudentDto> users = userModel.getStudentByGrade(notificationDto.getGradeId());
				logger.log(Level.INFO, "Grade oriented notification has been selected");
				for (StudentDto studentDto : users) {
					Notification notification = new Notification();
					notification.setCreatedDate(createdDate);
					notification.setCreatedUser(p.getName());
					notification.setDescription(notificationDto.getDescription());
					notification.setGradeId(notificationDto.getGradeId());
					notification.setLasttouchedDate(createdDate);
					notification.setLasttouchedUser(p.getName());
					notification.setSubjectId(null);
					notification.setUserId(studentDto.getUserId());
					em.persist(notification);
					em.flush();
					i++;
					if (users.size() == i) {
						ntfcation = new NotificationDto(notification);
					}
				}
				logger.log(Level.INFO, "***Notification will posted on dashboards of {0} users", users.size());
				return ntfcation;
			} else if (onlySubjectSelected) {
				List<StudentDto> users = new ArrayList<>();
				String strQuery = "SELECT DISTINCT  _users FROM User _users JOIN _users.usertype usrType JOIN UserSubject _usersubject "
						+ "JOIN _usersubject.subject usrSubject "
						+ "JOIN _usersubject.user usrUser ON (usrUser.userId = _users.userId) " + 
						"INNER JOIN Subject _subject ON (usrSubject.subjectId = _subject.subjectId) " + 
						"WHERE (usrSubject.subjectId = "+notificationDto.getSubjectId()+" AND usrType.usertypeId = 1";
				TypedQuery<User> query = em.createQuery(strQuery, User.class);
				if(query != null) {
					for(User user: query.getResultList()) {
						users.add(new StudentDto(user));
					}
				}
				logger.log(Level.INFO, "Subject oriented notification has been selected");
				for (StudentDto studentDto : users) {
					Notification notification = new Notification();
					notification.setCreatedDate(createdDate);
					notification.setCreatedUser(p.getName());
					notification.setDescription(notificationDto.getDescription());
					notification.setGradeId(null);
					notification.setLasttouchedDate(createdDate);
					notification.setLasttouchedUser(p.getName());
					notification.setSubjectId(notificationDto.getSubjectId());
					notification.setUserId(studentDto.getUserId());
					em.persist(notification);
					em.flush();
					i++;
					if (users.size() == i) {
						ntfcation = new NotificationDto(notification);
					}
				}
				logger.log(Level.INFO, "***Notification will posted on dashboards of {0} users", users.size());
				return ntfcation;
			} else {
				// List<StudentDto> users = userModel.get
				List<StudentDto> users = null;
				String notificationType = notificationDto.getNotificationCategory();
				if (notificationType != null && notificationType != "") {
					logger.log(Level.INFO, "User oriented notification has been selected");
					if (notificationType.equalsIgnoreCase("Public")) {
						logger.log(Level.INFO, "Public notification has been selected");
						users = lookUpModel.getAllUsers();
					} else if (notificationType.equalsIgnoreCase("student")) {
						logger.log(Level.INFO, "Notification to all students has been selected");
						users = userModel.getUsersByType(1);
					} else if (notificationType.equalsIgnoreCase("teacher")) {
						logger.log(Level.INFO, "Notification to all teachers has been selected");
						users = userModel.getUsersByType(2);
					}
					for (StudentDto studentDto : users) {
						Notification notification = new Notification();
						notification.setCreatedDate(createdDate);
						notification.setCreatedUser(p.getName());
						notification.setDescription(notificationDto.getDescription());
						notification.setGradeId(null);
						notification.setLasttouchedDate(createdDate);
						notification.setLasttouchedUser(p.getName());
						notification.setSubjectId(notificationDto.getSubjectId());
						notification.setUserId(studentDto.getUserId());
						em.persist(notification);
						em.flush();
						i++;
						if (users.size() == i) {
							ntfcation = new NotificationDto(notification);
						}
					}
					logger.log(Level.INFO, "***Notification will posted on dashboards of {0} users", users.size());
					return ntfcation;
				}
				return null;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: writeNotification()", e);
			return null;
		}

	}
}
