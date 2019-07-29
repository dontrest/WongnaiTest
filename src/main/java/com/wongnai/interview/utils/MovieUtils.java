package com.wongnai.interview.utils;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieUtils {
    // use to load JSON String from [MOVIE_DATA_URL]
    public static String readJsonFromUrl(String url) throws IOException {
        //open stream
        InputStream is = new URL(url).openStream();
        try {
            //prepare buffer and setting charset in format UTF-8
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            // read all json string
            String jsonText = readAll(rd);
            return jsonText;
        } finally {
            is.close();
        }
    }

    //this method use to load JSON string from reader
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static boolean SearchWithWholeWord(String src , String find){
        String pattern = "\\b"+find+"\\b";
        Pattern p= Pattern.compile(pattern);
        Matcher m=p.matcher(src);
        return m.find();
    }
}
