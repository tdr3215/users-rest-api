package com.techtalentsouth.UsersAPI;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User,Long>{
	
	List<User> findByState(String state);

//	Optional<User> findById(Long id);
	
}
