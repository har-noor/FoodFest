package ca.sheridancollege.kau11900.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ca.sheridancollege.kau11900.beans.Ticket;
import ca.sheridancollege.kau11900.beans.User;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class TicketRepository {

	private NamedParameterJdbcTemplate jdbc;

	public void addTicket(Ticket ticket) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO tickets (userName, eventDay, role, price, quantity) VALUES " + 
					   "(:c1, :c2, :c3, :c4, :c5)";
		
		parameters.addValue("c1" , ticket.getUserName());
		parameters.addValue("c2" , ticket.getEventDay());
		parameters.addValue("c3" , ticket.getRole());
		parameters.addValue("c4" , ticket.getPrice());
		parameters.addValue("c5" , ticket.getQuantity());
		
		jdbc.update(query, parameters);
	}

	public ArrayList<Ticket> getTickets() {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "SELECT * FROM tickets";
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);

		for (Map<String, Object> row : rows) {
			Ticket t = new Ticket();
			t.setId((int)row.get("id"));
			t.setUserName((String)row.get("userName"));			
			t.setEventDay((String)row.get("eventDay"));
			t.setRole((String) row.get("role"));
			t.setPrice((BigDecimal) row.get("price"));
			t.setQuantity((int) row.get("quantity"));
			tickets.add(t);
		}

		return tickets;
	}

	public Ticket getTicketsByID(int id) {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "SELECT * FROM TICKETS WHERE id=:ticket";
		parameters.addValue("ticket", id);
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);

		for (Map<String, Object> row : rows) {
			Ticket t = new Ticket();
			t.setId((int)row.get("id"));
			t.setUserName((String)row.get("userName"));			
			t.setEventDay((String)row.get("eventDay"));
			t.setRole((String) row.get("role"));
			t.setPrice((BigDecimal) row.get("price"));
			t.setQuantity((int) row.get("quantity"));
			tickets.add(t);
			
		}

		if (tickets.size() > 0) {
			return tickets.get(0);
		} else {
			return null;
		}

	}

	public void editTicket(Ticket ticket) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "UPDATE tickets SET userName=:c1, " +
				  "eventDay=:c2, role=:c3, price=:c4, quantity=:c5 WHERE id=:id";
		parameters.addValue("id" , ticket.getId());
		parameters.addValue("c1" , ticket.getUserName());
		parameters.addValue("c2" , ticket.getEventDay());
		parameters.addValue("c3" , ticket.getRole());
		parameters.addValue("c4" , ticket.getPrice());
		parameters.addValue("c5" , ticket.getQuantity());

		jdbc.update(query, parameters);

	}

	public void deleteTicket(Ticket ticket) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "DELETE FROM TICKETS WHERE id=:id";
		parameters.addValue("id", ticket.getId());

		jdbc.update(query, parameters);

	}

	public User findUserAccount(String userName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user WHERE userName=:name";
		parameters.addValue("name", userName);

		ArrayList<User> users = (ArrayList<User>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		return (users.size() > 0) ? users.get(0) : null;
	}

	public List<String> getRolesById(long userId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT user_role.userId, sec_role.roleName " + "FROM user_role, sec_role WHERE "
				+ "user_role.roleId=sec_role.roleId and userId=:id";
		parameters.addValue("id", userId);
		ArrayList<String> roles = new ArrayList<String>();
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String, Object> row : rows) {
			roles.add((String) row.get("roleName"));
		}
		return roles;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public void addUser(String userName, String password, int age, String city, String num, String role) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into SEC_User " + "(userName, encryptedPassword, age, city, num, ENABLED, role)"
				+ " values (:userName, :encryptedPassword, :age, :city, :num, 1, :role)";
		parameters.addValue("userName", userName);
		parameters.addValue("encryptedPassword", passwordEncoder().encode(password));
		parameters.addValue("age", age);
		parameters.addValue("city", city);
		parameters.addValue("num", num);
		parameters.addValue("role", role);
		
		jdbc.update(query, parameters);
	}

	public void addRole(long userId, long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into user_role (userId, roleId)" + "values (:userId, :roleId);";
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		jdbc.update(query, parameters);
	}

	public ArrayList<Ticket> getGuestTickets() {
		// Connection code here
		ArrayList<Ticket> guestContacts = new ArrayList<Ticket>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "SELECT * FROM tickets where role = 'Guest'";

		// More code here
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);

		for (Map<String, Object> row : rows) {
			Ticket t = new Ticket();
			t.setId((int) row.get("id"));
			t.setUserName((String)row.get("userName"));			
			t.setEventDay((String)row.get("eventDay"));
			t.setRole((String) row.get("role"));
			t.setPrice((BigDecimal) row.get("price"));
			t.setQuantity((int) row.get("quantity"));
			guestContacts.add(t);
		}

		return guestContacts;
	}
	
	public ArrayList<User> getGuestNames() {
		// Connection code here
		ArrayList<User> guestNames = new ArrayList<User>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "SELECT userName FROM SEC_USER where role = 'Guest' ORDER BY userName";

		// More code here
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);

		for (Map<String, Object> row : rows) {
			User u = new User();
			
			u.setUserName((String) row.get("userName"));
			guestNames.add(u);
		}

		return guestNames;
	}
	
	public ArrayList<Ticket> getTicketsByUserName(String userName) {

		MapSqlParameterSource parameter = new MapSqlParameterSource();

		String query = "SELECT * FROM tickets where userName = :u";

		parameter.addValue("u", userName);

		ArrayList<Ticket> ticket = (ArrayList<Ticket>) jdbc.query(query, parameter,
				new BeanPropertyRowMapper<Ticket>(Ticket.class));

		return ticket.size() > 0 ? ticket : null;
	}

	public ArrayList<Ticket> getTotal(String userName) {

		MapSqlParameterSource parameter = new MapSqlParameterSource();

		String query = "SELECT SUM(price * quantity) AS price FROM tickets where userName = :u";

		parameter.addValue("u", userName);

		ArrayList<Ticket> ticket = (ArrayList<Ticket>) jdbc.query(query, parameter,
				new BeanPropertyRowMapper<Ticket>(Ticket.class));

		return ticket.size() > 0 ? ticket : null;
	}

	

	

}
