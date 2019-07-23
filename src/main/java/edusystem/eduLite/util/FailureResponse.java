package edusystem.eduLite.util;

import javax.ejb.Stateless;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Stateless
public class FailureResponse {
	private String message;
}
