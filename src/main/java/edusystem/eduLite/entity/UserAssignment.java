package edusystem.eduLite.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the USER_ASSIGNMENT database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="USER_ASSIGNMENT", schema = "EDUCATION")
@NamedQueries({
	@NamedQuery(name="UserAssignment.findAll", query="SELECT u FROM UserAssignment u"),
	@NamedQuery(name = "UserAssignment.findByAssignment", 
	query = "SELECT u FROM UserAssignment u JOIN u.assignment assignment WHERE assignment.assignmentId = :assignmentId"),
	@NamedQuery(name = "UserAssignment.findByUser", 
	query = "SELECT u FROM UserAssignment u JOIN u.user user WHERE user.userId = :userId")
})
public class UserAssignment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USER_ASSIGNMENT_USERASSIGNMENTID_GENERATOR", sequenceName="USER_ASSIGNMENT_SEQ",
			schema = "EDUCATION",initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_ASSIGNMENT_USERASSIGNMENTID_GENERATOR")
	@Column(name="USER_ASSIGNMENT_ID")
	private Long userAssignmentId;

	@Column(name="ASSESSMENT_TYPE")
	private String assessmentType;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="CREATED_USER")
	private String createdUser;

	private String description;

	@Column(name="DUE_DATE")
	private Timestamp dueDate;

	@Column(name="LASTTOUCHED_DATE")
	private Timestamp lasttouchedDate;

	@Column(name="LASTTOUCHED_USER")
	private String lasttouchedUser;

	@Column(name="MARK_ALLOCATION")
	private Integer markAllocation;

	@Column(name="MARK_PERCENTAGE")
	private BigDecimal markPercentage;

	@Column(name="STUDENT_MARK")
	private Integer studentMark;
	

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;

	//bi-directional many-to-one association to Assignment
	@ManyToOne
	@JoinColumn(name="ASSIGNMENT_ID")
	private Assignment assignment;

}