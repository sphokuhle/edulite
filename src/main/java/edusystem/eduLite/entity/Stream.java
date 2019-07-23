package edusystem.eduLite.entity;

import java.io.Serializable;

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
 * The persistent class for the STREAM database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="STREAM", schema = "EDUCATION")
@NamedQueries({
	@NamedQuery(name="Stream.findAll", query="SELECT s FROM Stream s")
})
public class Stream implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="FACULTY_ID", nullable = false, unique = true)
	private Integer facultyId;

	@Column(name="\"NAME\"")
	private String name;

}