package com.example.file_microservice.repository;

import com.example.file_microservice.domain.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface FileRepository extends MongoRepository<File, String> {

    Page<File> findAllByApp(String app, Pageable pageable);

    void deleteAllByApp(String app);

    File findFileByAppAndNameAndMd5(String app,String name,String md5);
}
