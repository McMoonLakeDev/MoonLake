package com.minecraft.moonlake.api.encrypt.md5;

import com.minecraft.moonlake.api.encrypt.Encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by MoonLake on 2016/6/4.
 */
public final class MD5Encrypt implements Encrypt {

    private String source;

    /**
     * MD5 加密类
     */
    public MD5Encrypt() {

        this("");
    }

    /**
     * MD5 加密类
     *
     * @param source 字符串源
     */
    public MD5Encrypt(String source) {

        this.source = source;
    }

    /**
     * 设置加密的字符串源
     *
     * @param source 字符串源
     */
    @Override
    public void setSource(String source) {

        this.source = source;
    }

    /**
     * 获取加密的字符串源
     *
     * @return
     */
    @Override
    public String getSource() {

        return source;
    }

    /**
     * 获取字符串源的 MD5 加密的数据
     *
     * @return 经过 MD5 加密的数据 字符串源空则返回 null
     */
    @Override
    public MD5Data encrypt() {

        if(source != null && !source.isEmpty()) {

            String md5 = "";

            try {

                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] bytes = md.digest(source.getBytes());

                int di; StringBuffer sb = new StringBuffer();

                for(int i = 0; i < bytes.length; i++) {

                    di = bytes[i];

                    if(di < 0) {

                        di += 256;
                    }
                    if(di == 0) {

                        sb.append("0");
                    }
                    sb.append(Integer.toHexString(di));
                }
                md5 = sb.toString();
            }
            catch (NoSuchAlgorithmException e) {

                e.printStackTrace();
            }
            return new MD5(md5);
        }
        return null;
    }

    private class MD5 implements MD5Data {

        private final String md5;

        public MD5(String md5) {

            this.md5 = md5;
        }

        /**
         * 获取 MD5 加密后的 32 位数据
         *
         * @return 32 位 MD5 数据
         */
        @Override
        public String to32Bit() {

            return md5;
        }

        /**
         * 获取 MD5 加密后的 32 位大写数据
         *
         * @return 32 位 MD5 大写数据
         */
        @Override
        public String to32BitUpperCase() {

            return to32Bit().toUpperCase();
        }

        /**
         * 获取 MD5 加密后的 16 位数据
         *
         * @return 16 位 MD5 数据
         */
        @Override
        public String to16Bit() {

            return md5.substring(8, 24);
        }

        /**
         * 获取 MD5 加密后的 16 位大写数据
         *
         * @return 16 位 MD5 大写数据
         */
        @Override
        public String to16BitUpperCase() {

            return to16Bit().toUpperCase();
        }
    }
}
