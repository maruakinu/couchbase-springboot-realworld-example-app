package com.springboot.couchbase.springbootrealworld.domain.user.repository;

import com.springboot.couchbase.springbootrealworld.domain.user.entity.UserDocument;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Scope("Couchbase")
@Collection("users")
public interface UserRepository extends CrudRepository<UserDocument, Long> {


    List<UserDocument> findByUsernameOrEmail(String username, String email);
    Optional<UserDocument> findByEmail(String email);

    Optional<UserDocument> findByUsername(String username);

}

