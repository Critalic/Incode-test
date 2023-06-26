package com.example.incode.repository.mongo;

import com.example.incode.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Page<User> findAll(Pageable pageable);

    boolean existsByEmail(String email);
}
