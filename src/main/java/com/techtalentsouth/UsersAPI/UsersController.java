package com.techtalentsouth.UsersAPI;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;






// The rest controller exists so we can map incoming req to specific methods
// The method will tell the app what to do next i.e. return JSON or return an HTML page 

@RestController
public class UsersController {
	
	@Autowired
	private UsersRepository repository;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers(@RequestParam(value="state", required=false) @Valid String state){
		
		if(state != null) {
			List<User> usersByState = repository.findByState(state);
			return new ResponseEntity<>(usersByState,HttpStatus.OK);
		}
	List<User> allUsers = (List<User>) repository.findAll();	
	return new ResponseEntity<>(allUsers, HttpStatus.OK); 
	}
	
	@GetMapping("/users/{id}")
		public ResponseEntity<Optional<User>> getUserByID(@PathVariable(value="id") Long id){
		
		Optional<User> found = (Optional<User>)repository.findById(id);
		   
			
			if (!found.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				
				
			}
			return new ResponseEntity<>(found, HttpStatus.OK);
			
		}
	
	
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		

		repository.save(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	};	

	@PutMapping("/users/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable(value="id") Long id, @RequestBody @Valid User user, BindingResult bindingResult){
		
	Optional<User> found = repository.findById(id);
	
	if(bindingResult.hasFieldErrors()) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
		
			if(found == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
			
		
	repository.save(user);
	
	return new ResponseEntity<>(HttpStatus.OK);
	
	}
	
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(value="id") Long id) {
	
		
		Optional<User> user = repository.findById(id);
		
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	
     }

}