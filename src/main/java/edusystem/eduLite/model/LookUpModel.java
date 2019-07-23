package edusystem.eduLite.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import edusystem.eduLite.dto.GradeDto;
import edusystem.eduLite.dto.StreamDto;
import edusystem.eduLite.dto.StudentDto;
import edusystem.eduLite.dto.UserTypeDto;
import edusystem.eduLite.entity.Grade;
import edusystem.eduLite.entity.Stream;
import edusystem.eduLite.entity.User;
import edusystem.eduLite.entity.Usertype;

@Stateless
public class LookUpModel {
	Logger logger = Logger.getLogger(LookUpModel.class.getCanonicalName());
	
	@PersistenceContext
	EntityManager em;
	public List<GradeDto> getClasses(){
		TypedQuery<Grade> grades = em.createNamedQuery("Grade.findAll", Grade.class);
		List<GradeDto> list = new ArrayList<>();
		if(grades != null) {
			for (Grade grade : grades.getResultList()) {
				list.add(new GradeDto(grade));
			}
			return list;
		}
		return Collections.emptyList();
		
	}
	
	public List<GradeDto> getGradesByUser(StudentDto studentDto){
		List<GradeDto> list = new ArrayList<>();
		try {
			User user = em.find(User.class, studentDto.getUserId());
			if(user != null) {
				if(user.getTeacherGrades() != null) {
					String[] ids = user.getTeacherGrades().split(";");
					for (String string : ids) {
						list.add(new GradeDto(em.find(Grade.class, Integer.valueOf(string))));
					}
				}
			}
			return list;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in method: getGradesByUser()", e);
			return Collections.emptyList();
		}
	}
	
	
	public List<StreamDto> getStreams(){
		TypedQuery<Stream> streams = em.createNamedQuery("Stream.findAll", Stream.class);
		List<StreamDto> list = new ArrayList<>();
		if(streams != null) {
			for (Stream stream : streams.getResultList()) {
				list.add(new StreamDto(stream));
			}
			return list;
		}
		return Collections.emptyList();	
	}
	
	
	
	public List<UserTypeDto> getUserTypes(){
		TypedQuery<Usertype> query = em.createNamedQuery("Usertype.findAll", Usertype.class);
		List<UserTypeDto> list = new ArrayList<>();
		if(query != null) {
			for (Usertype type : query.getResultList()) {
				list.add(new UserTypeDto(type));
			}
			return list;
		}
		return Collections.emptyList();
		
	}
	
	public List<StudentDto> getAllUsers(){
		TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);
		List<StudentDto> list = new ArrayList<>();
		if(query != null) {
			for (User type : query.getResultList()) {
				list.add(new StudentDto(type));
			}
			return list;
		}
		return Collections.emptyList();	
	}
	
}
