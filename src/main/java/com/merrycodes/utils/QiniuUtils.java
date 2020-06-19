package com.merrycodes.utils;

import com.merrycodes.config.QiniuConfig;
import com.qiniu.cdn.CdnManager;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.InputStream;

/**
 * 七牛云工具类
 *
 * @author MerryCodes
 * @date 2020/6/15 20:50
 */
public class QiniuUtils {

    private final Auth auth;

    private final String bucket;

    private static QiniuUtils instance;

    private final CdnManager cdnManager;

    private final UploadManager uploadManager;

    private final BucketManager bucketManager;

    public static QiniuUtils getInstance(QiniuConfig qiniuConfig) {
        if (instance == null) {
            instance = new QiniuUtils(qiniuConfig);
        }
        return instance;
    }

    private QiniuUtils(QiniuConfig qiniuConfig) {
        if (instance != null) {
            throw new RuntimeException();
        }
        auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        bucket = qiniuConfig.getBucket();
        Configuration configuration = new Configuration(Region.region2());
        cdnManager = new CdnManager(auth);
        uploadManager = new UploadManager(configuration);
        bucketManager = new BucketManager(auth, configuration);
    }

    /**
     * 默认上传到七牛云中的文件名为:(年)(月)(日)(小时)(分钟)(秒).(文件后缀)
     *
     * @return 上传凭证 Token
     */
    public String getUploadToken() {
        StringMap policy = new StringMap();
        policy.put("saveKey", "$(year)$(mon)$(day)$(hour)$(min)$(sec)$(ext)");
        return getUploadToken(policy);
    }

    /**
     * 自定义上传策略
     *
     * @return 上传凭证 Token
     */
    public String getUploadToken(StringMap policy) {
        return auth.uploadToken(bucket, null, 3600, policy);
    }

    /**
     * 上传文件
     *
     * @param stream 输入流
     * @param key    上传文件保存的文件名（未指定则使用文件内容的hash值作为文件名）
     * @param token  上传凭证
     * @return {@link Response}
     * @throws QiniuException 异常 {@link QiniuException}
     */
    public Response uploadFile(InputStream stream, String key, String token) throws QiniuException {
        return uploadManager.put(stream, key, token, null, null);
    }

    /**
     * 删除文件
     *
     * @param key 删除文件的文件名
     * @throws QiniuException 异常 {@link QiniuException}
     */
    public void delete(String key) throws QiniuException {
        bucketManager.delete(bucket, key);
    }

    /**
     * 刷新文件外链
     *
     * @param url 待刷新文件外链列表
     * @throws QiniuException 异常 {@link QiniuException}
     */
    public void refreshUrl(String... url) throws QiniuException {
        cdnManager.refreshUrls(url);
    }

}
