package hu.bme.crysys.homework.pangolin.webshop.mapper;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static hu.bme.crysys.homework.pangolin.webshop.mapper.CommentMapper.commentsToCommentResults;

import hu.bme.crysys.homework.pangolin.webshop.dto.SearchResult;
import hu.bme.crysys.homework.pangolin.webshop.model.File;

public final class FileMapper {

    private FileMapper() {
    }

    public static List<SearchResult> filesToSearchResults(final List<File> files) {
        if (files == null) {
            return emptyList();
        }

        return files.stream().map(FileMapper::fileToSearchResult).collect(toList());
    }

    public static SearchResult fileToSearchResult(final File file) {
        return SearchResult.builder()
                .uuid(file.getUuid())
                .fileName(file.getFileName())
                .location(file.getLocation())
                .uploaderUserName(file.getUploader().getUsername())
                .date(file.getCreationDate())
                .comments(commentsToCommentResults(file.getComments()))
                .build();
    }

}
