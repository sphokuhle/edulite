package edusystem.eduLite.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the NOTIFICATION database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="NOTIFICATION", schema = "EDUCATION")
@NamedQueries({
	@NamedQuery(name="Notification.findAll", query="SELECT n FROM Notification n")
})
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="NOTIFICATION_ID", nullable = false, unique = true)
	private Integer notificationId;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="CREATED_USER")
	private String createdUser;

	private String description;

	@Column(name="GRADE_ID")
	private Integer gradeId;

	@Column(name="LASTTOUCHED_DATE")
	private Timestamp lasttouchedDate;

	@Column(name="LASTTOUCHED_USER")
	private String lasttouchedUser;

	@Column(name="SUBJECT_ID")
	private Integer subjectId;

	@Column(name="USER_ID")
	private Long userId;

}