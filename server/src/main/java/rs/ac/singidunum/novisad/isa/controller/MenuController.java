package rs.ac.singidunum.novisad.isa.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/menu")
public class MenuController {
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('STUDENT') or hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/professor")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public String professorAccess() {
		return "Professor Board.";
	}
}
