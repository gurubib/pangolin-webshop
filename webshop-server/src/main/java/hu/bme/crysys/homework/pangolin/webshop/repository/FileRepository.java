package hu.bme.crysys.homework.pangolin.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.bme.crysys.homework.pangolin.webshop.model.File;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    // TODO - write missing funs
    List<File> findByFileNameContaining(String fileNameSearchTerm);

    Optional<File> findByUuid(String uuid);
}
