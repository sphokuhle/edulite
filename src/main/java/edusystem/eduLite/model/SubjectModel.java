package edusystem.eduLite.model;

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

import edusystem.eduLite.dto.StudentDto;
import edusystem.eduLite.dto.SubjectDto;
import edusystem.eduLite.dto.UserSubjectDto;
import edusystem.eduLite.entity.Grade;
import edusystem.eduLite.entity.Subject;
import edusystem.eduLite.entity.SubjectGrade;
import edusystem.eduLite.entity.User;
import edusystem.eduLite.entity.UserSubject;

@Stateless
public class SubjectModel {
	@PersistenceContext
	EntityManager em;

	@Inject
	UsersModel userModel;

	Logger logger = Logger.getLogger(SubjectModel.class.getCanonicalName());

	public UserSubjectDto assignSubject(StudentDto studentDto, Integer subjectId) {
		UserSubject userSubject = new UserSubject();
		try {
			Subject subject = em.find(Subject.class, subjectId);
			User user = em.find(User.class, studentDto.getUserId());
			userSubject.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			userSubject.setSubject(subject);
			userSubject.setUser(user);
			em.persist(userSubject);
			logger.log(Level.INFO, "Subject Added Successfully");
			return new UserSubjectDto(userSubject);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: assignSubject()", e);
			return null;
		}
	}

	public int assignSubjectToTeacher(StudentDto studentDto, int subjectId) {
		try {

			int status = 1;
			User user = em.find(User.class, studentDto.getUserId());
			// Grade grade = em.find(Grade.class, 1);
			if (user != null) {
				if (user.getUsertype().getUsertypeId() == 2) {
					String tempGradeId = "";
					if (studentDto.getTeacherGrades() != null) {
						if (user.getTeacherGrades() == null) {
							user.setTeacherGrades(studentDto.getTeacherGrades());
						} else {
							tempGradeId = user.getTeacherGrades();
							// If that grade was never assigned to this teacher then add it.
							if (!isGradeIdAlreadyAssigned(user, studentDto))
								user.setTeacherGrades(tempGradeId + ";" + studentDto.getTeacherGrades());
						}
						em.merge(user);
						logger.log(Level.INFO, "Grades of the teacher have been updated..");
						int gradeId = Integer.valueOf(studentDto.getTeacherGrades());
						this.addToSubjectGrade(user, subjectId, gradeId);
						//Lastly add it into the UserSubject table
						this.assignSubject(studentDto, subjectId);
					} else {
						logger.log(Level.WARNING, "The grade for a subject was not selected");
						status = -2;
					}

				} else {
					logger.log(Level.WARNING, "The user is not registered as a teacher");
					status = -1;
				}
			}
			return status;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: assignSubjectToTeacher()", e);
			return 0;
		}
	}

	private void addToSubjectGrade(User user, int subjectId, int gradeId) {
		SubjectGrade subjectGrade = new SubjectGrade();

		Grade grade = em.find(Grade.class, gradeId);
		Subject subject = em.find(Subject.class, subjectId);
		
		if (grade != null && subject != null) {
			subjectGrade.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			subjectGrade.setDescription(user.getFirstname() + " " + user.getLastName() + " has been assigned to teach "
					+ subject.getName() + " in grade " + grade.getName());
			subjectGrade.setGrade(grade);
			subjectGrade.setSubject(subject);
			em.persist(subjectGrade);
			logger.log(Level.INFO, "Info added into SubjectGrade table");
		}
		else logger.log(Level.WARNING, "Could not find either the subject or grade");

	}

	private boolean isGradeIdAlreadyAssigned(User user, StudentDto studentDto) {
		String[] delimeter = user.getTeacherGrades().split(";");
		for (String string : delimeter) {
			if (string.equalsIgnoreCase(studentDto.getTeacherGrades())) {
				return true;
			}
		}
		logger.log(Level.INFO, "GradeId is not yet assigned to this user");
		return false;
	}

	public List<SubjectDto> getSubjectsByStudent(StudentDto studentDto) {
		try {
			List<SubjectDto> list = new ArrayList<>();
			String qlString = "SELECT DISTINCT s FROM Subject s JOIN UserSubject _usersubject "
					+ "JOIN _usersubject.user usrUser "
					+ "JOIN _usersubject.subject usrSubject ON (usrSubject.subjectId = s.subjectId) "
					+ "INNER JOIN User users ON (usrUser.userId = users.userId) " + "WHERE usrUser.userId = "
					+ studentDto.getUserId();
			TypedQuery<Subject> query = em.createQuery(qlString, Subject.class);
			if (query != null) {
				for (Subject subject : query.getResultList()) {
					list.add(new SubjectDto(subject));
				}
			}
			logger.log(Level.INFO, "Number of Subject(s): {0}", list.size());
			return list;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: getSubjectByStudent()", e);
			return null;
		}
	}
}
