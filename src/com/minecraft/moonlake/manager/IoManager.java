package com.minecraft.moonlake.manager;

import com.minecraft.moonlake._temp.util.Util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MoonLake on 2016/7/17.
 */
public class IoManager extends MoonLakeManager {

    /**
     * 将输入流写出到指定文件对象
     *
     * @param out 输出文件对象
     * @param is 输入流
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
     */
    public static void outInputStream(File out, InputStream is, int buf) {

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

            getMain().getMLogger().warn("写出输入流文件时异常: " + e.getMessage());
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
     * 动态读取语言文件并加载到 Map 集合
     *
     * @param lang 语言文件
     * @return Map 集合 文件不存在则 空集合
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
     */
    public static Map<String, String> readLangFile(String prefix, File lang) {

        Map<String, String> temp = new HashMap<>();

        if(lang.exists()) {

            BufferedReader bf = null;
            InputStreamReader isr = null;

            try {

                isr = new InputStreamReader(new FileInputStream(lang), "UTF-8");
                bf = new BufferedReader(isr);
                String buf = null;

                while ((buf = bf.readLine()) != null) {

                    if(!buf.equals("") && !buf.startsWith("#")) {

                        if(buf.contains("=")) {

                            int $ = buf.indexOf("=");

                            String key = buf.substring(0, $);
                            String value = buf.substring($ + 1);

                            if(value.charAt(0) != '#') {

                                temp.put(key, Util.color(prefix + value));
                            }
                            else {

                                temp.put(key, Util.color(value.substring(1)));
                            }
                        }
                    }
                }
            }
            catch (Exception e) {

                getMain().getMLogger().warn("读取语言文件时异常: " + e.getMessage());
            }
            finally {

                try {

                    if(isr != null) {

                        isr.close();
                    }
                    if(bf != null) {

                        bf.close();
                    }
                }
                catch (Exception e) {

                    getMain().getMLogger().warn("关闭语言文件流时异常: " + e.getMessage());
                }
            }
        }
        return temp;
    }
}
