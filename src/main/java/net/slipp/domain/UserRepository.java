package net.slipp.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by johngrib on 2017. 5. 20..
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
