package com.example.file_microservice.controller;

import com.example.file_microservice.domain.File;
import com.example.file_microservice.service.AdminServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @program: file_microservice
 * @description:
 * @author: huiyuhang  github.com/Boombaozi
 * @create: 2018-08
 **/

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "所有文件", notes = "", produces = "application/json")
    public ResponseEntity<Object> AllFile(@RequestParam(defaultValue = "0") int pageIndex,
                                          @RequestParam(defaultValue = "10") int pageSize) {

         Pageable pageable = new PageRequest( pageIndex,pageSize);

         return ResponseEntity.status(HttpStatus.OK).body(adminService.findAllFile(pageable));
    }

    @GetMapping("/{app}")
    @ResponseBody
    @ApiOperation(value = "指定应用的文件列表", notes = "", produces = "application/json")
    public ResponseEntity<Object> AllAppFile(@PathVariable String app ,
                                             @RequestParam(defaultValue = "0") int pageIndex,
                                             @RequestParam(defaultValue = "10") int pageSize) {

        Pageable pageable = new PageRequest(pageIndex,pageSize);

        return ResponseEntity.status(HttpStatus.OK).body(adminService.findAllFileByApp(app,pageable));
    }

    @DeleteMapping("/{app}")
    @ResponseBody
    @ApiOperation(value = "清除指定应用的所有文件!", notes = "", produces = "application/json")
    public ResponseEntity<Object> deletefileByApp(@PathVariable String app ){
        adminService.deleteAllbyApp(app);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/all")
    @ResponseBody
    @ApiOperation(value = "清除所有文件!", notes = "", produces = "application/json")
    public ResponseEntity<Object> deleteAllFile(){
        adminService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
