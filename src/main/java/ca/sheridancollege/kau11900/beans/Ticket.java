package ca.sheridancollege.kau11900.beans;

import java.math.BigDecimal;

import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Ticket {
	
	private int id;
	private String userName;
	private String eventDay;
	private String[] days= {"Friday", "Saturday", "Sunday"};
	private String[] prices= {"50.00"};
	private String role;
	private BigDecimal price;
	private int quantity;
}
