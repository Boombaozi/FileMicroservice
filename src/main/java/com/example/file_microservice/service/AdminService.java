package com.example.file_microservice.service;

import com.example.file_microservice.domain.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @program: file_microservice
 * @description:
 * @author: huiyuhang  github.com/Boombaozi
 * @create: 2018-08
 **/
public interface AdminService {

         List<File> findAllFile(Pageable pageable);

         List<File> findAllFileByApp(String app, Pageable pageable);

         void deleteAllbyApp(String app);

         void deleteAll();
}
