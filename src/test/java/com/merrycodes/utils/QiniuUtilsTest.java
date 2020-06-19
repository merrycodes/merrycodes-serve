package com.merrycodes.utils;

import com.merrycodes.config.QiniuConfig;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotEquals;

/**
 * @author MerryCodes
 * @date 2020/6/15 21:01
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class QiniuUtilsTest {

    private QiniuConfig qiniuConfig;

    private QiniuUtils qiniuUtils;

    @Autowired
    public void setQiniuConfig(QiniuConfig qiniuConfig) {
        this.qiniuConfig = qiniuConfig;
    }

    @Before
    public void init() {
        qiniuUtils = QiniuUtils.getInstance(qiniuConfig);
    }

    @Test
    public void getUploadToken() {
        String tokenOne = qiniuUtils.getUploadToken();
        String tokenTwo = qiniuUtils.getUploadToken();
        System.out.printf("第一个token：%s,第二个token：%s", tokenOne, tokenTwo);
        assertNotEquals(tokenOne, tokenTwo);
    }

    @Test
    public void uploadFile() throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get("本地文件存放地址"));
        Response response = qiniuUtils.uploadFile(inputStream, null, qiniuUtils.getUploadToken());
        DefaultPutRet defaultPutRet = JsonUtils.readValue(response.bodyString(), DefaultPutRet.class).orElse(null);
        System.out.println(qiniuConfig.getHost() + defaultPutRet.key);
    }

    @Test
    public void delete() throws QiniuException {
        String key = "20200618151704.png";
        qiniuUtils.delete(key);
        qiniuUtils.refreshUrl(qiniuConfig.getHost() + key);
    }
}