package edusystem.eduLite.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;


/**
 * The persistent class for the USER_SUBJECT database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="USER_SUBJECT", schema = "EDUCATION")
@NamedQueries({
	@NamedQuery(name="UserSubject.findAll", query="SELECT u FROM UserSubject u"),
	@NamedQuery(name= UserSubject.FINDBYSUBJECT, query="SELECT u FROM UserSubject u JOIN u.subject sbj WHERE sbj.subjectId = :subjectId"),
	@NamedQuery(name= UserSubject.FINDBYUSER, query="SELECT u FROM UserSubject u JOIN u.user usr WHERE usr.userId = :userId"),
	@NamedQuery(name= UserSubject.FINDBYUSERANDSUBJECT, query="SELECT u FROM UserSubject u JOIN u.user usr JOIN u.subject sbj "
			+ "WHERE usr.userId = :userId AND sbj.subjectId = :userId")
})
public class UserSubject implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String FINDBYSUBJECT = "UserSubject.findBySubject";
	public static final String FINDBYUSER = "UserSubject.findByUser";
	public static final String FINDBYUSERANDSUBJECT = "UserSubject.findByUserAndSubject";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_SUBJECT_ID", insertable = false, nullable = false, unique = true)
	private Integer userSubjectId;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	private String description;

	//, cascade = CascadeType.PERSIST
	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;

	//bi-directional many-to-one association to Subject
	@ManyToOne
	@JoinColumn(name="SUBJECT_ID")
	private Subject subject;
	

}