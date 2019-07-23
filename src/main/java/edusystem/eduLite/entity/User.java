package edusystem.eduLite.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the USERS database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="USERS", schema = "EDUCATION")
@NamedQueries({
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u ORDER BY u.createddate ASC"),
	@NamedQuery(name = User.FINDBYNAME, query = "SELECT u FROM User u WHERE u.firstname = :firstname"),
	@NamedQuery(name = User.FINDBYUSERNAME, query = "SELECT u FROM User u WHERE u.username = :username"),
	@NamedQuery(name = User.FINDBYUSERID, query = "SELECT u FROM User u WHERE u.userId = :userId"),
	@NamedQuery(name = "User.findAllByUserType", query = "SELECT u FROM User u JOIN u.usertype usrType WHERE usrType.usertypeId = :usertypeId"),
	@NamedQuery(name = User.FINDBYUSERTYPE, query = "SELECT u FROM User u JOIN u.usertype usrType WHERE u.userId = :userId AND usrType.usertypeId = :usertypeId"),
	@NamedQuery(name = User.FINDBYGRADE, query = "SELECT u FROM User u JOIN u.usertype usrType WHERE u.gradeId = :gradeId AND usrType.usertypeId = :usertypeId ORDER BY u.userId ASC")
	//@NamedQuery(name = "User.deleteById", query = "DELETE u FROM User u JOIN u.usertype usrType WHERE u.userId = :userId AND usrType.usertypeId = 1")
	//SELECT u FROM User u CROSS JOIN Grade g WHERE g.gradeId = :gradeId AND u.userId = g.userId 
	//SELECT * FROM User CROSS JOIN Grade
})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String  FINDBYNAME = "User.findByName";
	public static final String  FINDBYUSERNAME = "User.findByUserName";
	public static final String  FINDBYUSERID = "User.findByUserId";
	public static final String FINDBYUSERTYPE = "User.findByUserType";
	public static final String FINDBYGRADE = "User.findByGrade";

	@Id
	@SequenceGenerator(name="USERS_USERID_GENERATOR", sequenceName="USERS_SEQ", schema = "EDUCATION",initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERS_USERID_GENERATOR")
	@Column(name="USER_ID", unique = true, nullable = false)
	private Long userId;

	private String cellnumber;

	private String createdby;

	private Timestamp createddate;

	@Column(name="EMAIL_ADDRESS")
	private String emailAddress;

	private String firstname;

	@Column(name="GRADE_ID")
	private Integer gradeId;

	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="\"PASSWORD\"")
	private String password;

	@Column(name="PHYSICAL_ADDRESS")
	private String physicalAddress;

	@Column(name="SECOND_NAME")
	private String secondName;

	@Column(name="USERNAME")
	private String username;

	//bi-directional many-to-one association to Usertype
	@ManyToOne
	@JoinColumn(name="USERTYPE_ID")
	private Usertype usertype;

	//bi-directional many-to-one association to UserSubject
	@OneToMany(mappedBy="user")
	private List<UserSubject> userSubjects;
	
	@Column(name="TEACHER_GRADES")
	private String teacherGrades;

	//bi-directional many-to-one association to UserAssignment
	@OneToMany(mappedBy="user")
	private List<UserAssignment> userAssignments;

}