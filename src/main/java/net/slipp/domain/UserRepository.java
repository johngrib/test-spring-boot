package net.slipp.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by johngrib on 2017. 5. 20..
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findById(long id);
}
