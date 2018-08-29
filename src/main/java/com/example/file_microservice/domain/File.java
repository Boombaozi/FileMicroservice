package com.example.file_microservice.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Setter
@Getter
@ToString
@Document
public class File {
	@Id
	private String id;
	//所属应用
    private String app;
    //文件名
    private String name;
    //文件类型
    private String contentType;
    //大小
    private long size;
    //md5值
    private String md5;
    //文件内容
    private Binary content;
    //下载路径
    private String path;

    private Date createtime;

    
    protected File() {
    }
    
    public File(String name, String contentType, long size, Binary content) {
    	this.name = name;
    	this.contentType = contentType;
    	this.size = size;
    	this.createtime = new Date();
    	this.content = content;
    }

    //判断两个文件是否是同一个
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        File fileInfo = (File) object;
        return java.util.Objects.equals(md5, fileInfo.md5)
                && java.util.Objects.equals(name, fileInfo.name)
                && java.util.Objects.equals(app, fileInfo.app);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, contentType, size, createtime, md5, id);
    }

}
