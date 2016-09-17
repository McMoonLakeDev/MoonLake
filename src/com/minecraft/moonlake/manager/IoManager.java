package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MoonLake on 2016/7/17.
 */
public class IoManager extends MoonLakeManager {

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

        FileOutputStream fs = null;

        try {

            fs = new FileOutputStream(out);
            byte[] buff = new byte[buf];

            int len;

            while((len = is.read(buff)) > 0) {

                fs.write(buff, 0, len);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        finally {
        	
        	try {
        		
        		if(fs != null) {
        			
        			fs.flush();
        			fs.close();
        		}
        		if(is != null) {
        			
        			is.close();
        		}
        	}
        	catch(Exception e) {
        		
        	}
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
     * @throws IllegalArgumentException 如果文件编码对象未知则抛出异常
     */
    public static String[] readFileTextLines(File file, String charset) {

        Validate.notNull(file, "The file object is null.");

        if(!file.exists()) {

            return new String[0];
        }
        String[] lines = null;
        BufferedReader bf = null;
        InputStreamReader isr = null;

        try {

            isr = new InputStreamReader(new FileInputStream(file), charset == null ? "utf-8" : charset);
            bf = new BufferedReader(isr);
            List<String> texts = new ArrayList<>();
            String temp = null;

            while((temp = bf.readLine()) != null) {

                texts.add(temp);
            }
            lines = texts.toArray(new String[texts.size()]);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        finally {

            try {

                if(bf != null) {

                    bf.close();
                }
                if(isr != null) {

                    isr.close();
                }
            }
            catch (Exception e) {

            }
        }
        return lines;
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

        String[] lines = readFileTextLines(lang);

        for(final String line : lines) {

            if(!line.equals("") && !line.startsWith("#")) {

                if(line.contains("=")) {

                    int $ = line.indexOf("=");

                    String key = line.substring(0, $);
                    String value = line.substring($ + 1);

                    if (value.charAt(0) != '#') {

                        temp.put(key, StringUtil.toColor(prefix + value).get());
                    }
                    else {

                        temp.put(key, StringUtil.toColor(value.substring(1)).get());
                    }
                }
            }
        }
        return temp;
    }
}
