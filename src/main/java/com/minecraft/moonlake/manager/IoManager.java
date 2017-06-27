/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.executor.Consumer;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>IoManager</h1>
 * 输入输出流管理实现类
 *
 * @version 1.0
 * @author Month_Light
 */
public class IoManager extends MoonLakeManager {

    /**
     * UTF-8 Charset
     */
    public final static Charset UTF_8 = StringUtil.UTF_8;

    /**
     * 输入输出流管理实现类构造函数
     */
    private IoManager() {

    }

    /**
     * 将输入流写出到指定文件对象
     *
     * @param out 输出文件对象
     * @param is 输入流
     * @throws IllegalArgumentException 如果输出文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果输入流对象为 {@code null} 则抛出异常
     */
    public static void outInputStream(File out, InputStream is) {

        outInputStream(out, is, 1024);
    }

    /**
     * 将输入流写出到指定文件对象
     *
     * @param out 输出文件对象
     * @param is 输入流
     * @param buf 缓冲区大小
     * @throws IllegalArgumentException 如果输出文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果输入流对象为 {@code null} 则抛出异常
     */
    public static void outInputStream(File out, InputStream is, int buf) {

        Validate.notNull(out, "The file out object is null.");
        Validate.notNull(is, "The input stream object is null.");

        try {

            FileOutputStream fos = new FileOutputStream(out);
            byte[] buff = new byte[buf];
            int length;

            try {
                while ((length = is.read(buff)) > 0)
                    fos.write(buff, 0, length);
            } finally {
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件的所有行数到字符串数组
     *
     * @param file 文件对象
     * @return 字符串数组
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     */
    public static String[] readFileTextLines(File file) {

        return readFileTextLines(file, "utf-8");
    }

    /**
     * 读取文件的所有行数到字符串数组
     *
     * @param file 文件对象
     * @param charset 文件编码
     * @return 字符串数组
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     * @throws UnsupportedCharsetException 如果文件编码对象未知则抛出异常
     */
    public static String[] readFileTextLines(File file, String charset) {

        Validate.notNull(charset, "The charset object is null.");

        List<String> lineList = readFileTextLines(file, Charset.forName(charset));
        return lineList.toArray(new String[lineList.size()]);
    }

    /**
     * 读取文件的所有行数到字符串列表
     *
     * @param file 文件对象
     * @param charset 文件编码
     * @return 字符串列表
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果编码对象为 {@code null} 则抛出异常
     */
    public static List<String> readFileTextLines(File file, Charset charset) {

        Validate.notNull(file, "The file object is null.");
        Validate.notNull(charset, "The charset object is null.");

        List<String> lineList = new ArrayList<>();

        if(!file.exists())
            return lineList;

        try {

            String temp;

            try (BufferedReader bufferedReader = newReader(file, charset)) {
                while ((temp = bufferedReader.readLine()) != null)
                    lineList.add(temp);
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lineList;
    }

    /**
     * 以 UTF-8 编码读取文件的所有行数并给予消费者
     *
     * @param file 文件对象
     * @param consumer 消费者
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     */
    public static void readFileTextLinesConsumer(File file, Consumer<List<String>> consumer) {

        readFileTextLinesConsumer(file, UTF_8, consumer);
    }

    /**
     * 读取文件的所有行数并给予消费者
     *
     * @param file 文件对象
     * @param charset 文件编码
     * @param consumer 消费者
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果编码对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果消费者对象为 {@code null} 则抛出异常
     */
    public static void readFileTextLinesConsumer(File file, Charset charset, Consumer<List<String>> consumer) {

        Validate.notNull(consumer, "The consumer object is null.");

        consumer.accept(readFileTextLines(file, charset));
    }

    /**
     * 动态读取语言文件并加载到 Map 集合
     *
     * @param lang 语言文件
     * @return Map 集合 文件不存在则 空集合
     * @throws IllegalArgumentException 如果语言文件对象为 {@code null} 则抛出异常
     */
    public static Map<String, String> readLangFile(File lang) {

        return readLangFile("", lang);
    }

    /**
     * 动态读取语言文件并加载到 Map 集合
     *
     * @param prefix 前缀
     * @param lang 语言文件
     * @return Map 集合 文件不存在则 空集合
     * @throws IllegalArgumentException 如果语言文件对象为 {@code null} 则抛出异常
     */
    public static Map<String, String> readLangFile(String prefix, File lang) {

        Validate.notNull(lang, "The file lang object is null.");

        Map<String, String> temp = new HashMap<>();

        for(final String line : readFileTextLines(lang, UTF_8)) {

            if(!line.equals("") && !line.startsWith("#")) {

                if(!line.contains("="))
                    continue;

                int $ = line.indexOf("=");
                String key = line.substring(0, $);
                String value = line.substring($ + 1);

                if (!value.isEmpty() && value.charAt(0) != '#')
                    temp.put(key, value.contains(StringUtil.COLOR_STRING) ? StringUtil.toColor(prefix + value) : prefix + value);
                else
                    temp.put(key, value.contains(StringUtil.COLOR_STRING) ? StringUtil.toColor(value.substring(1)) : value.substring(1));
            }
        }
        return temp;
    }

    /**
     * 创建一个对指定文件以 {@code UTF-8} 编码读取的缓存读取器
     *
     * @param file 文件
     * @return BufferedReader
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果编码对象为 {@code null} 则抛出异常
     * @throws FileNotFoundException 如果文件不存在则抛出异常
     */
    public static BufferedReader newReader(File file) throws FileNotFoundException {

        return newReader(file, UTF_8);
    }

    /**
     * 创建一个对指定文件以指定编码读取的缓存读取器
     *
     * @param file 文件
     * @param charset 编码
     * @return BufferedReader
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果编码对象为 {@code null} 则抛出异常
     * @throws FileNotFoundException 如果文件不存在则抛出异常
     */
    public static BufferedReader newReader(File file, Charset charset) throws FileNotFoundException {

        Validate.notNull(file, "The file object is null.");
        Validate.notNull(charset, "The charset object is null.");

        return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    }

    /**
     * 创建一个对指定文件以 {@code UTF-8} 编码写出的缓存写出器
     *
     * @param file 文件
     * @return BufferedWriter
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果编码对象为 {@code null} 则抛出异常
     * @throws FileNotFoundException 如果文件不存在则抛出异常
     */
    public static BufferedWriter newWriter(File file) throws FileNotFoundException {

        return newWriter(file, UTF_8);
    }

    /**
     * 创建一个对指定文件以指定编码写出的缓存写出器
     *
     * @param file 文件
     * @param charset 编码
     * @return BufferedWriter
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果编码对象为 {@code null} 则抛出异常
     * @throws FileNotFoundException 如果文件不存在则抛出异常
     */
    public static BufferedWriter newWriter(File file, Charset charset) throws FileNotFoundException {

        Validate.notNull(file, "The file object is null.");
        Validate.notNull(charset, "The charset object is null.");

        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
    }

    /**
     * 获取指定文件名的扩展名
     *
     * @param file 文件
     * @return 扩展名
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     */
    public static String getFileExtension(File file) {

        String fullName = Validate.checkNotNull(file).getName();
        return getFileExtension(fullName);
    }

    /**
     * 获取指定文件名的扩展名
     *
     * @param fullName 文件全名称
     * @return 扩展名
     * @throws IllegalArgumentException 如果文件全名称对象为 {@code null} 则抛出异常
     */
    public static String getFileExtension(String fullName) {

        Validate.notNull(fullName, "The full name object is null.");

        int dotIndex = fullName.lastIndexOf(46);
        return dotIndex == -1 ? null : fullName.substring(dotIndex + 1);
    }

    /**
     * 获取指定文件名不带扩展名
     *
     * @param file 文件
     * @return 文件名不带扩展名
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     */
    public static String getFileNameWithoutExtension(File file) {

        String fullName = Validate.checkNotNull(file).getName();
        return getFileNameWithoutExtension(fullName);
    }

    /**
     * 获取指定文件名不带扩展名
     *
     * @param fullName 文件全名称
     * @return 文件名不带扩展名
     * @throws IllegalArgumentException 如果文件全名称对象为 {@code null} 则抛出异常
     */
    public static String getFileNameWithoutExtension(String fullName) {

        Validate.notNull(fullName, "The full name object is null.");

        String fileName = (new File(fullName)).getName();
        int dotIndex = fileName.lastIndexOf(46);
        return dotIndex == -1 ? fileName : fileName.substring(0, dotIndex);
    }

    /**
     * 创建指定文件对象的父目录
     *
     * @param file 文件
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果创建时错误则抛出异常
     */
    public static void createParentDirs(File file) {

        try {

            File parent = Validate.checkNotNull(file).getCanonicalFile().getParentFile();

            if(parent != null) {

                parent.mkdirs();
            }
        } catch (Exception e) {

            throw new MoonLakeException(e.getMessage(), e);
        }
    }
}
