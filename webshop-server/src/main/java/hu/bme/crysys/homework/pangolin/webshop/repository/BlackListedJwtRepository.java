package hu.bme.crysys.homework.pangolin.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import hu.bme.crysys.homework.pangolin.webshop.model.BlackListedJwt;

@Repository
public interface BlackListedJwtRepository extends JpaRepository<BlackListedJwt, Long> {

    List<BlackListedJwt> findByTokenEquals(String token);

    @Modifying
    @Transactional
    void deleteByExpireDateBefore(LocalDateTime date);

}
