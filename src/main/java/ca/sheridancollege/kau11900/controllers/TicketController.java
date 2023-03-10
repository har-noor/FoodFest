package ca.sheridancollege.kau11900.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.kau11900.beans.Ticket;
import ca.sheridancollege.kau11900.beans.User;
import ca.sheridancollege.kau11900.repositories.TicketRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class TicketController {

	@Autowired
	@Lazy
	private TicketRepository ticketRepo;

	@GetMapping("/")
	public String home() {
		return "index.html";
	}

	@GetMapping("/add")
	public String goAdd(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("ticket", new Ticket());
		ArrayList<User> us = ticketRepo.getGuestNames();
		model.addAttribute("list", us);
		return "add.html";
	}

	@PostMapping("/add")
	public String addPage(@ModelAttribute Ticket ticket, Model model, User user) {

		ticketRepo.addTicket(ticket);
		return "redirect:/add";
	}

	@PostMapping("/process") // process the form action
	public String ProcessAdd(@ModelAttribute Ticket ticket, Model model) {
		return "redirect:/add";
	}

	@GetMapping("/view")
	public String goView(Authentication authentication, Model model) {
		
		ArrayList<Ticket> t = new ArrayList<Ticket>();
		ArrayList<Ticket> total = new ArrayList<Ticket>();

		for (GrantedAuthority ga : authentication.getAuthorities()) {
			if (ga.getAuthority().equals("ROLE_VENDOR")) {

				t.addAll(ticketRepo.getTickets());
				model.addAttribute("ticket", t);

			}
		}

		for (GrantedAuthority ga : authentication.getAuthorities()) {
			if (ga.getAuthority().equals("ROLE_GUEST")) {

				t.addAll(ticketRepo.getTicketsByUserName(authentication.getName()));

				total.addAll(ticketRepo.getTotal(authentication.getName()));

				model.addAttribute("ticket", t);
				model.addAttribute("total", total);

			}
		}

		return t.size() > 0 ? "view.html" : null;
	}

	@GetMapping("/register")
	public String goRegister() {
		return "register.html";
	}

	@GetMapping("/edit/{id}")
	public String editTicket(@PathVariable int id, Model model) {
		Ticket ticket = ticketRepo.getTicketsByID(id);
		model.addAttribute("ticket", ticket);
		return "edit.html";
	}

	@GetMapping("/editTicket")
	public String processEdit(@ModelAttribute Ticket ticket) {
		Ticket t = ticket;

		// Update the drink in my database
		ticketRepo.editTicket(t);

		// Go back to the view page
		return "redirect:/view";
	}

	@GetMapping("/delete/{id}")
	public String processDelete(@ModelAttribute Ticket ticket) {

		// Update the drink in my database
		ticketRepo.deleteTicket(ticket);

		// Go back to the view page
		return "redirect:/view";
	}

	@GetMapping("/Login")
	public String loadLoginPage() {
		return "Login.html";

	}

	@GetMapping("/AccessDenied")
	public String loadDenied() {
		return "AccessDenied.html";

	}

	@PostMapping("/register")
	public String doRegistration(@RequestParam String userName, @RequestParam String password, @RequestParam int age,
			@RequestParam String city, @RequestParam String num, @RequestParam String role) {
		ticketRepo.addUser(userName, password, age, city, num, role);
		long uid = ticketRepo.findUserAccount(userName).getUserId();
		ticketRepo.addRole(uid, 2);

		return "redirect:/";
	}

}
