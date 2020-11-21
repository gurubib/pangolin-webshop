package hu.bme.crysys.homework.pangolin.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import hu.bme.crysys.homework.pangolin.webshop.model.File;
import hu.bme.crysys.homework.pangolin.webshop.model.User;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByFileNameContaining(String fileNameSearchTerm);

    Optional<File> findByUuid(String uuid);

    List<File> findByUploader(User uploader);

}
