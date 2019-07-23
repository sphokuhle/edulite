package edusystem.eduLite.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the GRADE database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="GRADE", schema = "EDUCATION")
@NamedQueries({
	@NamedQuery(name="Grade.findAll", query="SELECT g FROM Grade g ORDER BY g.gradeId ASC"),
	@NamedQuery(name="Grade.findByUser", query="SELECT g FROM Grade g WHERE g.userId = :userId ORDER BY g.gradeId ASC")
})
public class Grade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="GRADE_ID", nullable = false, unique = true)
	private Integer gradeId;

	@Column(name="GRADE_CODE")
	private String gradeCode;

	@Column(name="\"LOCATION\"")
	private String location;

	@Column(name="\"NAME\"")
	private String name;

	@Column(name="USER_ID")
	private Long userId;

	//bi-directional many-to-one association to SubjectGrade
	@OneToMany(mappedBy="grade")
	private List<SubjectGrade> subjectGrades;
	
}