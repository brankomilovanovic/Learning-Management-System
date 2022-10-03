package rs.ac.singidunum.novisad.isa.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.singidunum.novisad.isa.dto.TokenDTO;
import rs.ac.singidunum.novisad.isa.dto.UserDTO;
import rs.ac.singidunum.novisad.isa.model.ERole;
import rs.ac.singidunum.novisad.isa.model.MessageResponse;
import rs.ac.singidunum.novisad.isa.model.Role;
import rs.ac.singidunum.novisad.isa.model.User;
import rs.ac.singidunum.novisad.isa.model.UserDetailsImpl;
import rs.ac.singidunum.novisad.isa.security.token.TokenUtils;
import rs.ac.singidunum.novisad.isa.service.RoleService;
import rs.ac.singidunum.novisad.isa.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class LoginRegistrationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private TokenUtils tokenUtils;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDTO userDTO) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
	
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwtToken = tokenUtils.generateJwtToken(authentication);
			
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
			TokenDTO jwtDTO = new TokenDTO(jwtToken, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
			
			return new ResponseEntity<TokenDTO>(jwtDTO, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not exist!"));
		}
	}

	@PostMapping("/registration")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
		if (userService.existsByUsername(userDTO.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userService.existsByEmail(userDTO.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		userDTO.setName(userDTO.getName().substring(0,1).toUpperCase() + userDTO.getName().substring(1).toLowerCase());
		userDTO.setSurname(userDTO.getSurname().substring(0,1).toUpperCase() + userDTO.getSurname().substring(1).toLowerCase());
		User user = new User(null, userDTO.getName(), userDTO.getSurname(), userDTO.getUsername(), userDTO.getEmail(), encoder.encode(userDTO.getPassword()));
		Set<Role> roles = new HashSet<>();
		Role userRole = roleService.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));

		roles.add(userRole);
		user.setRoles(roles);
		userService.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
