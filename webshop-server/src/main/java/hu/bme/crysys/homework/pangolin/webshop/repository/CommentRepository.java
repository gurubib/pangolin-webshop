package hu.bme.crysys.homework.pangolin.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import hu.bme.crysys.homework.pangolin.webshop.model.Comment;
import hu.bme.crysys.homework.pangolin.webshop.model.File;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByFile(File file);

    // TODO - write missing funs

}
