package com.zbinyds.central.pojo.vo;

import com.zbinyds.central.pojo.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @Package: com.zbinyds.central.pojo.vo
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/4/13 23:20
 */

public class TestUploadVO extends Test implements Serializable {

    private static final long serialVersionUID = 6169757874747437076L;

    private MultipartFile[] files;

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
