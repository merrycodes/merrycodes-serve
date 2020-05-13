package com.merrycodes.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 生成/获取 密钥工具类
 *
 * @author MerryCodes
 * @date 2020/5/10 10:47
 */
public class RsaUtils {

    private static final Integer KEY_SIZE = 2048;

    /**
     * 根据密文生成rsa公钥私钥，并写入文件中
     *
     * @param publicKeyPath  公钥路径
     * @param privateKeyPath 私钥路径
     * @param secret         生成密钥的密文
     * @param size           生成密钥的大小
     * @throws Exception 可能抛出的异常
     *                   {@link NoSuchAlgorithmException}
     *                   {@link IOException}
     */
    public static void generateKey(String publicKeyPath, String privateKeyPath, String secret, Integer size) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(Math.max(size, KEY_SIZE), secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        // 获取公钥并写出
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        publicKeyBytes = Base64.getEncoder().encode(publicKeyBytes);
        writeFile(publicKeyPath, publicKeyBytes);
        // 获取私钥并写出
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        privateKeyBytes = Base64.getEncoder().encode(privateKeyBytes);
        writeFile(privateKeyPath, privateKeyBytes);
    }

    /**
     * @param filename 公钥保存路径
     * @return 公钥 {@link PublicKey}
     * @throws Exception 可能抛出的异常
     *                   {@link IOException}
     *                   {@link InvalidKeySpecException}
     *                   {@link NoSuchAlgorithmException}
     */
    public static PublicKey getPublicKey(String filename) throws Exception {
        byte[] bytes = readFile(filename);
        bytes = Base64.getDecoder().decode(bytes);
        X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(encodedKeySpec);
    }

    /**
     * @param filename 私钥保存路径
     * @return 私钥 {@link PrivateKey}
     * @throws Exception 可能抛出的异常
     *                   {@link IOException}
     *                   {@link InvalidKeySpecException}
     *                   {@link NoSuchAlgorithmException}
     */
    public static PrivateKey getPrivateKey(String filename) throws Exception {
        byte[] bytes = readFile(filename);
        bytes = Base64.getDecoder().decode(bytes);
        PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(encodedKeySpec);
    }

    private static byte[] readFile(String fileNane) throws IOException {
        return Files.readAllBytes(new File(fileNane).toPath());
    }

    private static void writeFile(String path, byte[] bytes) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.write(file.toPath(), bytes);
    }

    /**
     * 生成公钥和私钥
     */
    public static void main(String[] args) throws Exception {
        String publicKeyPath = "";
        String privateKeyPath = "";
        String secret = "";
        Integer size = 2048;
        generateKey(publicKeyPath, privateKeyPath, secret, size);
    }

}
