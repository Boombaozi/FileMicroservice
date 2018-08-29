package com.example.file_microservice.controller;

import com.example.file_microservice.domain.File;
import com.example.file_microservice.service.FileService;
import com.example.file_microservice.util.MD5Util;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/File")
public class FileController {

    @Autowired
    private FileService fileService;


    @GetMapping("/view/{id}")
    @ResponseBody
    @ApiOperation(value = "图片显示链接", notes = "用来在线显示图片", produces = "application/json")
    public ResponseEntity<Object> showImage(@PathVariable String id) {
        Optional<File> file = fileService.getFileById(id);

        if (file.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.get().getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.get().getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
                    .body(file.get().getContent().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    @ApiOperation(value = "文件下载链接", notes = "用来下载文件", produces = "application/json")
    public ResponseEntity<Object> FileDownload(@PathVariable String id) {
        Optional<File> file = fileService.getFileById(id);
        if (file.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.get().getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
                    .body(file.get().getContent().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }
    }

    @PostMapping("/upload/{app}")
    @ResponseBody
    @ApiOperation(value = "上传文件", notes = "上传文件接口，返回文件的url", produces = "application/json")
    public ResponseEntity<Object> handleFileUpload(@PathVariable String app, @RequestParam("file") MultipartFile file) {
        File returnFile = null;
        try {
            File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                    new Binary(file.getBytes()));
            f.setMd5(MD5Util.getMD5(file.getInputStream()));
            //设置应用名称
            f.setApp(app);
            //查找是否有相同文件
            File old = fileService.findFileByNameAndMd5(f.getApp(), f.getName(), f.getMd5());

            if (old!=null &&old.equals(f)) {
                return ResponseEntity.status(HttpStatus.OK).body(old.getPath());
            } else {
                returnFile = fileService.saveFile(f);
                String path = "/File/view/" + returnFile.getId();
                returnFile.setPath(path);
                File file1=  fileService.saveFile(returnFile);
                return ResponseEntity.status(HttpStatus.OK).body(path+",/File/view/"+file1.getId());
            }
        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


    //删除文件接口
    @DeleteMapping("/{id}")
    @ResponseBody
    @ApiOperation(value = "删除文件", notes = "", produces = "application/json")
    public ResponseEntity<Object> deleteFile(@PathVariable String id) {
        try {
            fileService.removeFile(id);
            return ResponseEntity.status(HttpStatus.OK).body("DELETE Success!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}