package com.minecraft.moonlake.encrypt.md5;

import com.minecraft.moonlake.encrypt.Encrypt;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import com.minecraft.moonlake.validate.Validate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <h1>MD5Encrypt</h1>
 * MD5 数据加密实现类
 *
 * @version 1.0
 * @author Month_Light
 */
public class MD5Encrypt implements Encrypt {

    private StringProperty source;

    /**
     * MD5 加密类
     */
    public MD5Encrypt() {

        this(null);
    }

    /**
     * MD5 加密类
     *
     * @param source 字符串源
     */
    public MD5Encrypt(String source) {

        this.source = new SimpleStringProperty(source);
    }

    /**
     * 获取 MD5 字符串加密对象
     *
     * @return 字符串对象
     */
    public StringProperty getSource() {

        return source;
    }

    /**
     * 获取字符串源的 MD5 加密的数据
     *
     * @return 经过 MD5 加密的数据
     * @throws IllegalArgumentException 如果字符串源对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果字符串源对象为 {@code empty} 则抛出异常
     */
    @Override
    public MD5 encrypt() {

        Validate.notNull(source.get(), "The md5 string source object is null.");
        Validate.isTrue(!source.get().isEmpty(), "The md5 string source is empty.");

        String md5 = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(source.get().getBytes());

            int di; StringBuilder sb = new StringBuilder();

            for (byte aByte : bytes) {

                di = aByte;

                if (di < 0) {

                    di += 256;
                }
                if (di < 16) {

                    sb.append("0");
                }
                sb.append(Integer.toHexString(di));
            }
            md5 = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return !md5.equals("") ? new MD5(md5) : null;
    }
}
