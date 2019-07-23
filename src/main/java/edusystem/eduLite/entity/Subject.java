package edusystem.eduLite.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the SUBJECT database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="SUBJECT", schema = "EDUCATION")
@NamedQueries({
	@NamedQuery(name="Subject.findAll", query="SELECT s FROM Subject s"),
	@NamedQuery(name="Subject.findById", query="SELECT s FROM Subject s WHERE s.subjectId = :subjectId")
})
public class Subject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SUBJECT_ID")
	private Integer subjectId;

	@Column(name="\"NAME\"")
	private String name;

	private String stream;

	@Column(name="SUBJECT_CODE")
	private String subjectCode;
	
	//bi-directional many-to-one association to Assignment
	@OneToMany(mappedBy="subject")
	private List<Assignment> assignments;

	//bi-directional many-to-one association to UserSubject
	@OneToMany(mappedBy="subject")
	private List<UserSubject> userSubjects;
	
	@OneToMany(mappedBy="subject")
	private List<SubjectGrade> subjectGrades;

}