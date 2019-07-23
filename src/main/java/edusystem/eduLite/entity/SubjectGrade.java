package edusystem.eduLite.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


/**
 * The persistent class for the SUBJECT_GRADE database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="SUBJECT_GRADE", schema = "EDUCATION")
@NamedQueries({
	@NamedQuery(name="SubjectGrade.findAll", query="SELECT s FROM SubjectGrade s"),
	@NamedQuery(name=SubjectGrade.FINBYGRADE, query="SELECT s FROM SubjectGrade s JOIN s.grade gr WHERE gr.gradeId = :gradeId"),
	@NamedQuery(name=SubjectGrade.FINBYSUBJECT, query="SELECT s FROM SubjectGrade s JOIN s.subject sbj WHERE sbj.subjectId = :subjectId"),
	@NamedQuery(name=SubjectGrade.FINBYSUBJECTANDGRADE, query="SELECT s FROM SubjectGrade s JOIN s.subject sbj JOIN s.grade gr WHERE sbj.subjectId = :subjectId AND gr.gradeId = :gradeId")
})
public class SubjectGrade implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String FINBYGRADE = "SubjectGrade.findByGrade";
	public static final String FINBYSUBJECT = "SubjectGrade.findBySubject";
	public static final String FINBYSUBJECTANDGRADE = "SubjectGrade.findBySubjectAndGrade";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SUBJECT_GRADE_ID", insertable = false, nullable = false, unique = true)
	private Integer subjectGradeId;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name="DESCRIPTION")
	private String description;

	//bi-directional many-to-one association to Subject
	@ManyToOne
	@JoinColumn(name="SUBJECT_ID")
	private Subject subject;

	//bi-directional many-to-one association to Grade
	@ManyToOne
	@JoinColumn(name="GRADE_ID")
	private Grade grade;

}