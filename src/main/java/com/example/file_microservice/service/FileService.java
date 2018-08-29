
package com.example.file_microservice.service;


import com.example.file_microservice.domain.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;


public interface FileService {

	File saveFile(File file);
	

	void removeFile(String id);
	

	Optional<File> getFileById(String id);


	Page<File> FileList(Pageable pageable);

	File findFileByNameAndMd5(String app, String name,String md5);
}
