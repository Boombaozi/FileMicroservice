package com.example.file_microservice.service;

import com.example.file_microservice.domain.File;
import com.example.file_microservice.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @program: file_microservice
 * @description:
 * @author: huiyuhang  github.com/Boombaozi
 * @create: 2018-08
 **/
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    @Override
    public void removeFile(String id) {
        fileRepository.deleteById(id);
    }

    @Override
    public Optional<File> getFileById(String id) {
        return fileRepository.findById(id);
    }


    @Override
    public Page<File> FileList(Pageable pageable) {
        return fileRepository.findAll(pageable);
    }


    @Override
    public File findFileByNameAndMd5(String app, String name, String md5) {
       return fileRepository.findFileByAppAndNameAndMd5(app,name,md5);
    }
}
