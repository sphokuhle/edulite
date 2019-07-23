package edusystem.eduLite.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the ASSIGNMENT database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ASSIGNMENT", schema = "EDUCATION")
@NamedQueries({ 
	@NamedQuery(name = "Assignment.findAll", query = "SELECT a FROM Assignment a ORDER BY a.lasttouchedDate DESC"),
	@NamedQuery(name = Assignment.FINDBYSUBJECT, query = "SELECT a FROM Assignment a JOIN a.subject subj WHERE subj.subjectId = :subjectId ORDER BY a.lasttouchedDate DESC" )
})
public class Assignment implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String FINDBYSUBJECT = "Assignment.findBySubject";

	@Id
	@SequenceGenerator(name="ASSIGNMENT_ASSIGNMENTID_GENERATOR", sequenceName="ASSIGNMENTS_SEQ",schema = "EDUCATION",initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ASSIGNMENT_ASSIGNMENTID_GENERATOR")
	@Column(name="ASSIGNMENT_ID", unique = true, nullable = false)
	private Long assignmentId;

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

	@Column(name="\"NAME\"")
	private String name;

	@Column(name="STUDENT_MARK")
	private Integer studentMark;

	//bi-directional many-to-one association to Subject
	@ManyToOne
	@JoinColumn(name="SUBJECT_ID")
	private Subject subject;

	//bi-directional many-to-one association to UserAssignment
	@OneToMany(mappedBy="assignment")
	private List<UserAssignment> userAssignments;

}