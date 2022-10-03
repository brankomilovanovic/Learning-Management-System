package rs.ac.singidunum.novisad.isa.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.singidunum.novisad.isa.dto.ProfessorDTO;
import rs.ac.singidunum.novisad.isa.dto.RankDTO;
import rs.ac.singidunum.novisad.isa.dto.RoleDTO;
import rs.ac.singidunum.novisad.isa.dto.ScientificAreaDTO;
import rs.ac.singidunum.novisad.isa.dto.TypeRanksDTO;
import rs.ac.singidunum.novisad.isa.dto.UserDTO;
import rs.ac.singidunum.novisad.isa.model.Professor;
import rs.ac.singidunum.novisad.isa.model.Rank;
import rs.ac.singidunum.novisad.isa.model.Role;
import rs.ac.singidunum.novisad.isa.model.ScientificArea;
import rs.ac.singidunum.novisad.isa.service.ProfessorService;
import rs.ac.singidunum.novisad.isa.service.RankService;
import rs.ac.singidunum.novisad.isa.service.ScientificAreaService;
import rs.ac.singidunum.novisad.isa.service.TypeRanksService;
import rs.ac.singidunum.novisad.isa.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ranks")
public class RankController {
	
	@Autowired
	RankService rankService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProfessorService professorService;
	
	@Autowired
	TypeRanksService typeRanksService;
	
	@Autowired
	ScientificAreaService scientificAreaService;
	
	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	Date electionDate;
	Date terminationDate;
	
	@GetMapping("/allRanks")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<RankDTO>> getAll() {
		ArrayList<RankDTO> ranks = new ArrayList<RankDTO>();
		for (Rank rank : rankService.findAll()) {
			
			Set<RoleDTO> roleDTO = new HashSet<>();
			for(Role role: rank.getProfessor().getUser().getRoles()) { 
				roleDTO.add(new RoleDTO(role.getId(), role.getName()));
			}
			
			ProfessorDTO professor = new ProfessorDTO(rank.getProfessor().getId(), rank.getProfessor().getJmbg(), rank.getProfessor().getDateOfBirth(), 
					rank.getProfessor().getAddress(), rank.getProfessor().getPhoneNumber(), rank.getProfessor().getBiography(), 
					new UserDTO(rank.getProfessor().getUser().getId(), rank.getProfessor().getUser().getName(), rank.getProfessor().getUser().getSurname(),
							rank.getProfessor().getUser().getUsername(), rank.getProfessor().getUser().getEmail(), rank.getProfessor().getUser().getPassword(), roleDTO));

			TypeRanksDTO typeRanks = new TypeRanksDTO(rank.getTypeRanks().getId(), rank.getTypeRanks().getName(), rank.getTypeRanks().isActive());
			
			ScientificAreaDTO scientificArea = new ScientificAreaDTO(rank.getScientificArea().getId(), rank.getScientificArea().getName(), rank.getScientificArea().isActive());
			
			ranks.add(new RankDTO(rank.getId(), rank.getElectionDate(), rank.getTerminationDate(), typeRanks, scientificArea, professor));
		}
		return new ResponseEntity<Iterable<RankDTO>>(ranks, HttpStatus.OK);
	}
	
	@GetMapping("findByUsername/{username}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Iterable<RankDTO>> getAllRanksFromProfessor(@PathVariable("username") String username) {
		ArrayList<RankDTO> ranksDTO = new ArrayList<RankDTO>();
		for (Rank ranks : rankService.findByUsername(username)) {
			
			Set<RoleDTO> roleDTO = new HashSet<>();
			for(Role role: ranks.getProfessor().getUser().getRoles()) { 
				roleDTO.add(new RoleDTO(role.getId(), role.getName()));
			}
			
			ProfessorDTO professor = new ProfessorDTO(ranks.getProfessor().getId(), ranks.getProfessor().getJmbg(), ranks.getProfessor().getDateOfBirth(), 
					ranks.getProfessor().getAddress(), ranks.getProfessor().getPhoneNumber(), ranks.getProfessor().getBiography(),
					new UserDTO(ranks.getProfessor().getUser().getId(), ranks.getProfessor().getUser().getName(), ranks.getProfessor().getUser().getSurname(),
							ranks.getProfessor().getUser().getUsername(), ranks.getProfessor().getUser().getEmail(), ranks.getProfessor().getUser().getPassword(), roleDTO));
				
			TypeRanksDTO typeRanks = new TypeRanksDTO(ranks.getTypeRanks().getId(), ranks.getTypeRanks().getName(), ranks.getTypeRanks().isActive());
				
			ScientificAreaDTO scientificArea = new ScientificAreaDTO(ranks.getScientificArea().getId(), ranks.getScientificArea().getName(), ranks.getScientificArea().isActive());
				
			ranksDTO.add(new RankDTO(ranks.getId(), ranks.getElectionDate(), ranks.getTerminationDate(), typeRanks, scientificArea, professor));	
		}
		return new ResponseEntity<Iterable<RankDTO>>(ranksDTO, HttpStatus.OK);
	}
	
	
	@PostMapping("/createRank")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Rank> createRank(@RequestBody Rank newRank){
		Optional<Professor> professor = professorService.findByUsername(newRank.getProfessor().getUser().getUsername());
		newRank.setProfessor(professor.get());
		try {
			rankService.save(newRank);
			return new ResponseEntity<Rank>(newRank, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Rank>(HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/{rankId}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<Rank> update(@PathVariable("rankId") Long rankId, @RequestBody Rank newRank) {
		Rank rank = rankService.findOne(rankId).orElse(null);
		if (rank != null) {
			rankService.save(newRank);
			return new ResponseEntity<Rank>(newRank, HttpStatus.OK);
		}
		return new ResponseEntity<Rank>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMINISTRATOR')")
	public ResponseEntity<ScientificArea> deletRank(@PathVariable("id") Long rankId) {
		if (rankService.findOne(rankId).isPresent()) {
			rankService.delete(rankId);
			return new ResponseEntity<ScientificArea>(HttpStatus.OK);
		}
		return new ResponseEntity<ScientificArea>(HttpStatus.NOT_FOUND);
	}
}
