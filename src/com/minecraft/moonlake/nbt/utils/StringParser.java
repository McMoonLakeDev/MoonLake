package com.minecraft.moonlake.nbt.utils;

import com.minecraft.moonlake.nbt.exception.NBTException;

/**
 * Created by MoonLake on 2016/9/23.
 */
public class StringParser {

    public StringParser() {
    }

    public static String parse(String input) throws NBTException {

        char[] chars = input.toCharArray();
        StringBuilder buffer = new StringBuilder();
        StringBuilder unicode = new StringBuilder();
        StringParser.Mode mode = StringParser.Mode.CHAR;
        int row = 0;
        int col = -1;

        for(int i = 0; i < chars.length; ++i) {

            char char0 = chars[i];

            if(char0 == 10) {

                col = 0;
                ++row;
            }
            else {

                ++col;
            }
            switch(mode.ordinal()) {
                case 1:
                    switch(char0) {
                        case '\"':
                            throw new NBTException("unescaped \'\"\' exception, input: " + input);
                        case '&':
                            buffer.append('§');
                            continue;
                        case '\\':
                            mode = StringParser.Mode.ESCAPE;
                            continue;
                        default:
                            buffer.append(char0);
                            continue;
                    }
                case 2:
                    switch(char0) {
                        case '\t':
                        case ' ':
                            mode = StringParser.Mode.SPACE;
                            continue;
                        case '\n':
                        case '\r':
                            buffer.append(char0);
                            mode = StringParser.Mode.SPACE;
                            continue;
                        case '\"':
                            buffer.append('\"');
                            break;
                        case '&':
                            buffer.append('&');
                            break;
                        case '\'':
                            buffer.append('\'');
                            break;
                        case '0':
                            buffer.append('\u0000');
                            break;
                        case '1':
                            buffer.append('\u0001');
                            break;
                        case '2':
                            buffer.append('\u0002');
                            break;
                        case '3':
                            buffer.append('\u0003');
                            break;
                        case '4':
                            buffer.append('\u0004');
                            break;
                        case '5':
                            buffer.append('\u0005');
                            break;
                        case '6':
                            buffer.append('\u0006');
                            break;
                        case '7':
                            buffer.append('\u0007');
                            break;
                        case '\\':
                            buffer.append('\\');
                            break;
                        case 'b':
                            buffer.append('\b');
                            break;
                        case 'f':
                            buffer.append('\f');
                            break;
                        case 'n':
                            buffer.append('\n');
                            break;
                        case 'r':
                            buffer.append('\r');
                            break;
                        case 't':
                            buffer.append('\t');
                            break;
                        case 'u':
                            mode = StringParser.Mode.UNICODE;
                            continue;
                        default:
                            throw new NBTException("can\'t escape symbol, input: " + input + ", char: " + char0);
                    }

                    mode = StringParser.Mode.CHAR;
                    break;
                case 3:
                    switch(char0) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            unicode.append(char0);
                            if(unicode.length() == 4) {
                                char character = (char)Integer.parseInt(unicode.toString(), 16);
                                buffer.append(character);
                                unicode = new StringBuilder();
                                mode = StringParser.Mode.CHAR;
                            }
                            continue;
                        case ':':
                        case ';':
                        case '<':
                        case '=':
                        case '>':
                        case '?':
                        case '@':
                        case 'G':
                        case 'H':
                        case 'I':
                        case 'J':
                        case 'K':
                        case 'L':
                        case 'M':
                        case 'N':
                        case 'O':
                        case 'P':
                        case 'Q':
                        case 'R':
                        case 'S':
                        case 'T':
                        case 'U':
                        case 'V':
                        case 'W':
                        case 'X':
                        case 'Y':
                        case 'Z':
                        case '[':
                        case '\\':
                        case ']':
                        case '^':
                        case '_':
                        case '`':
                        default:
                            throw new NBTException("unexpected hex character: " + char0);
                    }
                case 4:
                    switch(char0) {
                        case '\t':
                        case '\r':
                        case ' ':
                            continue;
                        case '\n':
                            buffer.append(char0);
                            continue;
                        case '\"':
                            throw new NBTException("unescaped \'\"\' exception, input: " + input);
                        case '\\':
                            mode = StringParser.Mode.ESCAPE;
                            continue;
                        default:
                            buffer.append(char0);
                            mode = StringParser.Mode.CHAR;
                            continue;
                    }
                default:
                    throw new NBTException("unknown exception, input: " + input);
            }
        }

        if(mode == StringParser.Mode.CHAR) {

            return buffer.toString();
        }
        else {

            throw new NBTException("unexpected end of string exception, input: " + input);
        }
    }

    public static String wrap(String raw) {

        raw = raw.replace("\\", "\\\\");
        raw = raw.replace("\n", "\\n");
        raw = raw.replace("\b", "\\b");
        raw = raw.replace("\r", "\\r");
        raw = raw.replace("\t", "\\t");
        raw = raw.replace("\f", "\\f");
        raw = raw.replace("\"", "\\\"");
        raw = raw.replace("&", "\\&");
        raw = raw.replace(String.valueOf('§'), "&");
        return raw;
    }

    public enum Mode {

        CHAR,
        ESCAPE,
        UNICODE,
        SPACE;
    }
}
