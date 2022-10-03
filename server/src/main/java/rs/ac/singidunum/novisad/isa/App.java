package rs.ac.singidunum.novisad.isa;

import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import rs.ac.singidunum.novisad.isa.model.User;
import rs.ac.singidunum.novisad.isa.model.forum.Forum;
import rs.ac.singidunum.novisad.isa.model.ERole;
import rs.ac.singidunum.novisad.isa.model.Role;
import rs.ac.singidunum.novisad.isa.repository.RoleRepository;
import rs.ac.singidunum.novisad.isa.repository.UserRepository;
import rs.ac.singidunum.novisad.isa.repository.forum.ForumRepository;
import rs.ac.singidunum.novisad.isa.service.RoleService;


@SpringBootApplication
public class App
	implements CommandLineRunner{
	
	@Autowired PasswordEncoder encoder;
	@Autowired UserRepository ob;
	@Autowired RoleService rs;
	@Autowired RoleRepository ry;
	@Autowired ForumRepository forumRepository;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception 
	{	 
		if(forumRepository.findForum().isEmpty()) { // Ako forum ne postoji pravimo ga odmah
			forumRepository.save(new Forum(null, true));
		}
		
		if(!ob.findByUsername("admin").isEmpty() && !ry.findByName(ERole.ROLE_USER).isEmpty() && !ry.findByName(ERole.ROLE_ADMINISTRATOR).isEmpty()
				&& !ry.findByName(ERole.ROLE_PROFESSOR).isEmpty() && !ry.findByName(ERole.ROLE_STUDENT).isEmpty()) {
			System.out.println("Default values already exist");
		} else {
			ry.save(new Role(null, ERole.ROLE_USER));
			ry.save(new Role(null, ERole.ROLE_STUDENT));
			ry.save(new Role(null, ERole.ROLE_PROFESSOR));
			ry.save(new Role(null, ERole.ROLE_ADMINISTRATOR));
			
			Set<Role> roles = new HashSet<>();
			Role userRole = rs.findByName(ERole.ROLE_ADMINISTRATOR).orElseThrow();
			roles.add(userRole);
			
			User u = new User(null, "admin", "admin", "admin", "admin@gmail.com", encoder.encode("123"));
			u.setRoles(roles);
			ob.save(u);
			
			// dodaj enum type of teaching
		}
		
	}
}
