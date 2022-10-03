package rs.ac.singidunum.novisad.isa.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.singidunum.novisad.isa.dto.ProfessorDTO;
import rs.ac.singidunum.novisad.isa.dto.RoleDTO;
import rs.ac.singidunum.novisad.isa.dto.StudentDTO;
import rs.ac.singidunum.novisad.isa.dto.UserDTO;
import rs.ac.singidunum.novisad.isa.model.ERole;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.Role;
import rs.ac.singidunum.novisad.isa.model.User;
import rs.ac.singidunum.novisad.isa.service.RoleService;
import rs.ac.singidunum.novisad.isa.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	String roleName;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@GetMapping
	public Iterable<UserDTO> getAll(@RequestParam(name = "pageNo") Integer pageNo, 
									@RequestParam(name = "pageSize") Integer pageSize, 
									@RequestParam(name = "sortBy") String sortBy,
									@RequestParam(name = "name") String name,
									@RequestParam(name = "surname") String surname,
									@RequestParam(name = "roleId") String roleId) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        if(name.equals("") && surname.equals("") && roleId.equals("")){
	        Page<User> userPaged = userService.findAll(pageable);
	        Page<UserDTO> userDTO = userPaged.map(user -> {
	        	Set<RoleDTO> roles = new HashSet<>();
				for(Role role: user.getRoles()) { 
					roles.add(new RoleDTO(role.getId(), role.getName()));
				}
				UserDTO dto = new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getPassword(), roles);
			    return dto;
			});
	        return userDTO.getContent();
        } else {
        	Page<User> userPaged = userService.findByNameAndSurname(name, surname, roleId, pageNo, pageSize);
	        Page<UserDTO> userDTO = userPaged.map(user -> {
	        	Set<RoleDTO> roles = new HashSet<>();
				for(Role role: user.getRoles()) { 
					roles.add(new RoleDTO(role.getId(), role.getName()));
				}
				UserDTO dto = new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getPassword(), roles);
			    return dto;
			});
	        return userDTO.getContent();
        }
    }
	
	@GetMapping("/{username}")
	public ResponseEntity<UserDTO> getByUsername(@PathVariable("username") String username) {
		Optional<User> user = userService.findByUsername(username);
		UserDTO userDTO = new UserDTO();
		if (user.isPresent()) {
			Set<RoleDTO> roles = new HashSet<>();
			for(Role role: user.get().getRoles()) { 
				if(user.get().getStudent()!=null && user.get().getProfessor() ==null) {
					roles.add(new RoleDTO(role.getId(), role.getName()));
					userDTO = new UserDTO(user.get().getId(), user.get().getName(), user.get().getSurname(), user.get().getUsername(),
							user.get().getEmail(), user.get().getPassword(), roles,
							new StudentDTO(user.get().getStudent().getId(), user.get().getStudent().getJmbg(), user.get().getStudent().getDateOfBirth(),
									user.get().getStudent().getAddress(), user.get().getStudent().getPhoneNumber(),new UserDTO(user.get().getId(), null, null, null, null,null, null)),
							null);
				}
				else if(user.get().getProfessor() != null && user.get().getStudent()==null) {
					roles.add(new RoleDTO(role.getId(), role.getName()));
					userDTO = new UserDTO(user.get().getId(), user.get().getName(), user.get().getSurname(), user.get().getUsername(),
							user.get().getEmail(), user.get().getPassword(), roles,null,
							new ProfessorDTO(user.get().getProfessor().getId(), user.get().getProfessor().getJmbg(), user.get().getProfessor().getDateOfBirth(),
							user.get().getProfessor().getAddress(),user.get().getProfessor().getPhoneNumber(),user.get().getProfessor().getBiography(),
							new UserDTO(user.get().getId(), null, null, null, null, null, null)));
				}
				else if(user.get().getStudent()!=null && user.get().getProfessor()!=null) {
					roles.add(new RoleDTO(role.getId(), role.getName()));
					userDTO = new UserDTO(user.get().getId(), user.get().getName(), user.get().getSurname(), user.get().getUsername(),
							user.get().getEmail(), user.get().getPassword(), roles,
							new StudentDTO(user.get().getStudent().getId(), user.get().getStudent().getJmbg(), user.get().getStudent().getDateOfBirth(),
									user.get().getStudent().getAddress(), user.get().getStudent().getPhoneNumber(),new UserDTO(user.get().getId(), null, null, null, null,null, null)),
							new ProfessorDTO(user.get().getProfessor().getId(), user.get().getProfessor().getJmbg(), user.get().getProfessor().getDateOfBirth(),
									user.get().getProfessor().getAddress(),user.get().getProfessor().getPhoneNumber(),user.get().getProfessor().getBiography(),
									new UserDTO(user.get().getId(), null, null, null, null, null, null)));
				}
				else {
					roles.add(new RoleDTO(role.getId(), role.getName()));
					userDTO = new UserDTO(user.get().getId(), user.get().getName(), user.get().getSurname(), user.get().getUsername(),
							user.get().getEmail(), user.get().getPassword(), roles);
				}
				}
			
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		}
		return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<User> create(@RequestBody User user) {
		try {
			user.setName(user.getName().substring(0,1).toUpperCase() + user.getName().substring(1).toLowerCase());
			user.setSurname(user.getSurname().substring(0,1).toUpperCase() + user.getSurname().substring(1).toLowerCase());
			user.setPassword(encoder.encode(user.getPassword()));
			userService.save(user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	//@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User user) {
		User userExsist = userService.findOne(id).orElse(null);
		if (userExsist != null) {
			user.setName(user.getName().substring(0,1).toUpperCase() + user.getName().substring(1).toLowerCase());
			user.setSurname(user.getSurname().substring(0,1).toUpperCase() + user.getSurname().substring(1).toLowerCase());
			if(!user.getPassword().equals(userExsist.getPassword())) {
				user.setPassword(encoder.encode(user.getPassword()));
			}		
			userService.save(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<User> delete(@PathVariable("id") Long id) {
		if (userService.findOne(id).isPresent()) {
			userService.delete(id);
			return new ResponseEntity<User>(HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/checkEmail/{userId}/{mail}")
	//@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> checkEmail(@PathVariable("userId") String userId, @PathVariable("mail") String mail) {
		if (userService.existsByEmail(mail) == true) {
			if(!userId.equals("null")) {
				Optional<User> user = userService.findOne(Long.parseLong(userId));
				if(!mail.equals(user.get().getEmail())) { return ResponseEntity.badRequest().body(new MessageResponse("Error: E-Mail is already taken!")); }
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: E-Mail is already taken!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("E-Mail is free!"));
	}
	
	@GetMapping("/checkUsername/{userId}/{username}")
	//@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> checkUsername(@PathVariable("userId") String userId, @PathVariable("username") String username) {
		if (userService.existsByUsername(username) == true) {
			if(!userId.equals("null")) {
				Optional<User> user = userService.findOne(Long.parseLong(userId));
				if(!username.equals(user.get().getUsername())) { return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!")); }
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
			}
		} 
		return ResponseEntity.ok(new MessageResponse("Username is free!"));
	}
	
	@GetMapping("/countUser")
	public int countUser(@RequestParam(name = "name") String name,
						 @RequestParam(name = "surname") String surname,
						 @RequestParam(name = "roleId") String roleId) {
        return userService.countUser(name, surname, roleId);
    }
	
	//JUST FOR TEST
	@GetMapping("/generate300Users")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<?> generate300Users() {
		Set<Role> roles = new HashSet<>();
		Role userRole = roleService.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		for (int i = 0; i < 300; i++) {
			User user = new User(null, "ime" + i, "prezime" + i, "korisnickoime" + i, "mail@adresa" + i + ".com", encoder.encode("sifra") + i);
			roles.add(userRole);
			user.setRoles(roles);
			userService.save(user);
		}
		return new ResponseEntity<User>(HttpStatus.OK);
	}

}
