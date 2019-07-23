package edusystem.eduLite.dto;

import edusystem.eduLite.entity.Usertype;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserTypeDto {
	private Integer usertypeId;

	private String description;

	private String type;

	//private List<UsersDto> users;
	
	public UserTypeDto(Usertype userType) {
		this.usertypeId = userType.getUsertypeId();
		this.description = userType.getDescription();
		this.type = userType.getType();
		/*this.users = new ArrayList<UsersDto>();
		for (User user : userType.getUsers()) {
			this.users.add(new UsersDto(user));
		}*/
	}
	
	
}
