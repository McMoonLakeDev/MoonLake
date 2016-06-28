package com.minecraft.moonlake.api.encrypt.md5;

import com.minecraft.moonlake.api.encrypt.Encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * Created by MoonLake on 2016/6/28.
 */
public class MD5FileEncrypt implements Encrypt {

    private File file;

    public MD5FileEncrypt(File file) {

        this.file = file;
    }

    /**
     * 设置加密的文件源
     *
     * @param file 文件对象
     */
    public void setFile(File file) {

        this.file = file;
    }

    /**
     * 获取加密的文件源
     *
     * @return 文件对象
     */
    public File getFile() {

        return file;
    }

    /**
     * 获取文件源加密的数据
     *
     * @return 经过加密的数据 文件源空则返回 null
     */
    @Override
    public MD5Data encrypt() {

        if(file != null && file.exists()) {

            String md5 = "";
            FileInputStream in = null;
            MappedByteBuffer byteBuffer = null;

            try {

                in = new FileInputStream(file);
                byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(byteBuffer);
                BigInteger bi = new BigInteger(1, md.digest());

                return new MD5(bi.toString(16));
            }
            catch (Exception e) {

                e.printStackTrace();
            }
            finally {

                if(in != null) {

                    try {

                        in.close();
                    }
                    catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
