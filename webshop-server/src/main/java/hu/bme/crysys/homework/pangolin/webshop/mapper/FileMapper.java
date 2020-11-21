package hu.bme.crysys.homework.pangolin.webshop.mapper;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static hu.bme.crysys.homework.pangolin.webshop.mapper.CommentMapper.mapCommentsToCommentResults;

import hu.bme.crysys.homework.pangolin.webshop.dto.DownloadResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.SearchResponse;
import hu.bme.crysys.homework.pangolin.webshop.dto.SearchResult;
import hu.bme.crysys.homework.pangolin.webshop.dto.UploadRequest;
import hu.bme.crysys.homework.pangolin.webshop.model.Comment;
import hu.bme.crysys.homework.pangolin.webshop.model.File;

@Slf4j
public final class FileMapper {

    private FileMapper() {
    }

    public static SearchResponse mapFilesToSearchResponse(final List<File> files,  final Map<File, List<Comment>> commentsByFileMap) {
        final List<SearchResult> searchResults = mapFilesToSearchResults(files, commentsByFileMap);
        return SearchResponse.builder()
                .results(searchResults)
                .build();
    }

    public static List<SearchResult> mapFilesToSearchResults(final List<File> files, final Map<File, List<Comment>> commentsByFileMap) {
        if (files == null) {
            return emptyList();
        }

        return files.stream().map(f -> mapFileToSearchResult(f, commentsByFileMap.get(f))).collect(toList());
    }

    public static SearchResult mapFileToSearchResult(final File file, final List<Comment> comments) {
        String preview = "..."; // TODO - call the parser and generate preview -> CAFF or CIFF are the file extensions

        return SearchResult.builder()
                .uuid(file.getUuid())
                .uploaderUserName(file.getUploader().getUsername())
                .date(file.getCreationDate())
                .comments(comments == null ? new ArrayList<>() : mapCommentsToCommentResults(comments))
                .preview(preview)
                .build();
    }

    public static File mapUploadRequestToFile(final UploadRequest request, String filesDirectoryPath) throws IOException {
        String fileName = getFileNameFromUploadRequest(request, filesDirectoryPath);
        saveFile(filesDirectoryPath + fileName, request.getFileContentAsString());

        return File.builder()
                .comments(new ArrayList<>())
                .creationDate(LocalDateTime.now())
                .fileName(fileName)
                .location(filesDirectoryPath)
                .uuid(UUID.randomUUID().toString())
                .build();
    }

    public static DownloadResponse mapFileToDownloadResponse(final File file) throws Exception {
        String preview = "..."; // TODO - call the parser and generate preview -> CAFF or CIFF are the file extensions
        String fileContentAsString = getFileContentAsString(file);

        return DownloadResponse.builder()
                .fileContentAsString(fileContentAsString)
                .preview(preview)
                .build();
    }

    private static String getFileNameFromUploadRequest(UploadRequest request, String filesDirectoryPath) throws FileAlreadyExistsException {
        String fileName = request.getRequiredFileName() + "." + request.getFileType();

        Path path = Path.of(filesDirectoryPath + fileName);
        if (Files.exists(path)) {
            throw new FileAlreadyExistsException("File already existing with name: " + fileName);
        }

        return fileName;
    }

    private static void saveFile(String fileNameWithPath, String fileContentAsString) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileNameWithPath))) {
            writer.write(fileContentAsString);
        } catch (IOException e) {
            throw new IOException("File writing exception: " + e.getMessage());
        }
    }

    private static String getFileContentAsString(File file) throws IOException {
        Path filePath = Path.of(
                file.getLocation() + file.getFileName()
        );
        return Files.readString(filePath);
    }

}
