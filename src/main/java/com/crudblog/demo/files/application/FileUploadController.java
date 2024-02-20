package com.crudblog.demo.files.application;


import com.crudblog.demo.files.domain.entity.Media;
import com.crudblog.demo.files.domain.service.IStorageService;
import com.crudblog.demo.files.infrastructure.exception.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    private final IStorageService storageService;

    @Autowired
    public FileUploadController(IStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Media> handleFileUpload(@RequestParam("file") MultipartFile file) {
        Media media = storageService.store(file);

        return ResponseEntity.status(201).body(media);
    }

    @GetMapping("/files")
    @ResponseBody
    public ResponseEntity<Resource> serveFile() {

        Resource file = storageService.loadAsResource("690jxXKCUtWmbsWnN4gibBUYR20ahsrc3P5v0jMIh4FCexCaSF-600x600.jpg");

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
