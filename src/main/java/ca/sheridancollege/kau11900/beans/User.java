package ca.sheridancollege.kau11900.beans;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	private Long userId;
	private String userName;
	private String encryptedPassword;
	private int age;
	private String city;
	private String num;
	private String role = "Guest";
	private String[] roles= {"Guest"};
}