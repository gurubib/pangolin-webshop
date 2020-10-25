package hu.bme.crysys.homework.pangolin.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import hu.bme.crysys.homework.pangolin.webshop.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // TODO - write missing funs

}
