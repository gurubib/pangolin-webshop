package hu.bme.crysys.homework.pangolin.webshop.mapper;

import hu.bme.crysys.homework.pangolin.webshop.dto.AddCommentRequest;
import hu.bme.crysys.homework.pangolin.webshop.dto.CommentResult;
import hu.bme.crysys.homework.pangolin.webshop.model.Comment;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public final class CommentMapper {

    private CommentMapper() {
    }

    public static List<CommentResult> commentsToCommentResults(final List<Comment> comments) {
        if (comments == null) {
            return emptyList();
        }

        return comments.stream().map(CommentMapper::commentToCommentResult).collect(toList());
    }

    public static CommentResult commentToCommentResult(final Comment comment) {
        return CommentResult.builder()
                .uuid(comment.getUuid())
                .text(comment.getText())
                .userName(comment.getUser().getUsername())
                .date(comment.getCreationDate())
                .build();
    }

}
