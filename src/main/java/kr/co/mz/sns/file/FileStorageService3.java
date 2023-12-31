package kr.co.mz.sns.file;

import jakarta.annotation.Nullable;
import kr.co.mz.sns.dto.post.GenericPostDto;
import kr.co.mz.sns.dto.user.detail.CompleteUserProfileDto;
import kr.co.mz.sns.exception.FileWriteException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static kr.co.mz.sns.service.file.FileConstants.BASIC_DIRECTORY;
import static kr.co.mz.sns.service.file.FileConstants.SALVE_LOCAL_PUBLIC_DIRECTORY;

@Service
public class FileStorageService3 {

    public static String createDirectory() {
        var fileDirectory = new File(
                SALVE_LOCAL_PUBLIC_DIRECTORY + LocalDateTime.now().toLocalDate().toString().substring(0, 10));
        if (!fileDirectory.mkdirs()) {
            System.out.println("경로가 존재합니다.");
        }
        return fileDirectory.getAbsolutePath();
    }

    public static String createPostDirectory() {
        var fileDirectory = new File(
                BASIC_DIRECTORY + LocalDateTime.now().toLocalDate().toString().substring(0, 10));
        if (!fileDirectory.mkdirs()) {
            System.out.println("경로가 존재합니다.");
        }
        return fileDirectory.getAbsolutePath();
    }

    public static String getFileExtension(@Nullable String fileName) {
        if (fileName == null) {
            return "";
        }
        var dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    public void saveFile(List<MultipartFile> fileList, GenericPostDto insertedPostDto) {
        var uuidList = insertedPostDto.getPostFiles().stream()
                .map(fileDto -> insertedPostDto.getSeq() + "_" + fileDto.getName())
                .toList();

        var index = 0;
        var uuidArray = uuidList.toArray();
        for (var file : fileList) {
            if (!file.isEmpty()) {
                var filePath = createPostDirectory();
                var fileFullPath = filePath + File.separator + uuidArray[index];
                try (
                        var bos = new BufferedOutputStream(new FileOutputStream(fileFullPath));
                        var is = new BufferedInputStream(file.getInputStream())
                ) {
                    var buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                } catch (IOException ioe) {
                    throw new FileWriteException("Failed to save file" + ioe.getMessage(), ioe);
                }
            }
            index++;
        }
    }

    public InputStream downloadFile(CompleteUserProfileDto fileDto) {
        var fileFullPath = fileDto.getPath() + File.separator + fileDto.getUuid() + "." + fileDto.getExtension();
        try (var inputStream = new FileInputStream(fileFullPath)) {
            return inputStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CompleteUserProfileDto> getUserFileList(List<MultipartFile> fileList) {
        var fileDtoList = new ArrayList<CompleteUserProfileDto>();
        for (var file : fileList) {
            if (!file.isEmpty() && file.getOriginalFilename() != null) {
                var uuid = UUID.randomUUID().toString();
                var name = file.getOriginalFilename();
                var path = createDirectory();
                var size = file.getSize();
                var extension = getFileExtension(name);
                fileDtoList.add(new CompleteUserProfileDto(uuid, name, path, size, extension));
            }
        }
        return fileDtoList;
    }

    public <T> List<T> convertTo(List<MultipartFile> fileList, Function<MultipartFile, T> function) {
        if (fileList.isEmpty()) {
            return List.of();
        }

        return fileList.stream()
                .filter(file -> !file.isEmpty() && file.getOriginalFilename() != null)
                .map(function)
                .toList();
    }

    public boolean delete(String filePath) {
        System.out.println(filePath);
        var file = new File(filePath);
        var flag = false;
        if (file.exists()) {
            return file.delete();
        }
        return flag;
    }
}
