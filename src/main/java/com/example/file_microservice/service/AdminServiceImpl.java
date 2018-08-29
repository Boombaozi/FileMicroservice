package com.example.file_microservice.service;

import com.example.file_microservice.domain.File;
import com.example.file_microservice.repository.FileRepository;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @program: file_microservice
 * @description:
 * @author: huiyuhang  github.com/Boombaozi
 * @create: 2018-08
 **/

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public List<File> findAllFile(Pageable pageable) {
        List<File> files = fileRepository.findAll(pageable).getContent();
        for (int i = 0; i < files.size(); i++) {
            files.get(i).setContent(null);
        }
        return files;
    }

    @Override
    public List<File> findAllFileByApp(String app, Pageable pageable) {
        List<File> files = fileRepository.findAllByApp(app, pageable).getContent();
        for (int i = 0; i < files.size(); i++) {
            files.get(i).setContent(null);
        }
        return files;
    }

    @Override
    public void deleteAllbyApp(String app) {
        fileRepository.deleteAllByApp(app);
    }

    @Override
    public void deleteAll() {
        fileRepository.deleteAll();
    }
}
