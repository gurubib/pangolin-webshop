package hu.bme.crysys.homework.pangolin.webshop.mapper;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import hu.bme.crysys.homework.pangolin.webshop.dto.CommentResult;
import hu.bme.crysys.homework.pangolin.webshop.model.Comment;

public final class CommentMapper {

    private CommentMapper() {
    }

    public static List<CommentResult> mapCommentsToCommentResults(final List<Comment> comments) {
        if (comments == null) {
            return emptyList();
        }

        return comments.stream().map(CommentMapper::mapCommentToCommentResult).collect(toList());
    }

    public static CommentResult mapCommentToCommentResult(final Comment comment) {
        return CommentResult.builder()
                .uuid(comment.getUuid())
                .text(comment.getText())
                .userName(comment.getUser().getUsername())
                .date(comment.getCreationDate())
                .build();
    }

}
