package edusystem.eduLite.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


/**
 * The persistent class for the USERTYPE database table.
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="USERTYPE", schema = "EDUCATION")
@NamedQueries({
	@NamedQuery(name="Usertype.findAll", query="SELECT u FROM Usertype u")
})
public class Usertype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USERTYPE_ID", nullable = false)
	private int usertypeId;

	private String description;

	@Column(name="\"TYPE\"")
	private String type;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="usertype")
	private List<User> users;

}