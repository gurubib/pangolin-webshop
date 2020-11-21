package hu.bme.crysys.homework.pangolin.webshop.mapper;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static hu.bme.crysys.homework.pangolin.webshop.mapper.CommentMapper.mapCommentsToCommentResults;

import hu.bme.crysys.homework.pangolin.webshop.dto.DownloadResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.SearchResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.SearchResult;
import hu.bme.crysys.homework.pangolin.webshop.model.File;

@Slf4j
public final class FileMapper {

    private FileMapper() {
    }

    public static SearchResponse mapFilesToSearchResponse(final List<File> files) {
        final List<SearchResult> searchResults = mapFilesToSearchResults(files);
        return SearchResponse.builder()
                .results(searchResults)
                .build();
    }

    public static List<SearchResult> mapFilesToSearchResults(final List<File> files) {
        if (files == null) {
            return emptyList();
        }

        return files.stream().map(FileMapper::mapFileToSearchResult).collect(toList());
    }

    public static SearchResult mapFileToSearchResult(final File file) {
        String preview = "..."; // TODO - call the parser and generate preview

        return SearchResult.builder()
                .uuid(file.getUuid())
                .uploaderUserName(file.getUploader().getUsername())
                .date(file.getCreationDate())
                .comments(mapCommentsToCommentResults(file.getComments()))
                .preview(preview)
                .build();
    }

    public static DownloadResponse mapFileToDownloadResponse(final File file) throws Exception {
        String preview = "..."; // TODO - call the parser and generate preview
        String fileContentAsString = getFileContentAsString(file);

        return DownloadResponse.builder()
                .fileContentAsString(fileContentAsString)
                .preview(preview)
                .build();
    }

    private static String getFileContentAsString(File file) throws IOException {
        Path filePath = Path.of(
                file.getLocation() + java.io.File.pathSeparator + file.getFileName()
        );
        return Files.readString(filePath);
    }

}
