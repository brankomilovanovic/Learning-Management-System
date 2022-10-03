package rs.ac.singidunum.novisad.isa.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.singidunum.novisad.isa.dto.RoleDTO;
import rs.ac.singidunum.novisad.isa.model.ERole;
import rs.ac.singidunum.novisad.isa.model.Role;
import rs.ac.singidunum.novisad.isa.service.RoleService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/roles")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	
	@GetMapping("/allRoles")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<RoleDTO>> getAllRoles() {
		ArrayList<RoleDTO> roles = new ArrayList<RoleDTO>();
		for (Role role : roleService.findAll()) {
			roles.add(new RoleDTO(role.getId(), role.getName()));
		}

		return new ResponseEntity<Iterable<RoleDTO>>(roles, HttpStatus.OK);
	}
	
	@GetMapping("/{role_name}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<RoleDTO> getByName(@PathVariable("role_name") String role_name) {
		Optional<Role> role = roleService.findByName(ERole.valueOf(role_name));
		RoleDTO roleDTO = new RoleDTO();
		if (role.isPresent()) {
			roleDTO = new RoleDTO(role.get().getId(), role.get().getName());
		}
		return new ResponseEntity<RoleDTO>(roleDTO, HttpStatus.OK);
	}
	
	@GetMapping("/countAdministrator")
	public int countAdministrator() {
        return roleService.countAdministrator();
    }
}
