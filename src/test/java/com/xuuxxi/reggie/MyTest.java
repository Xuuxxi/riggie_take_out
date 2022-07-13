package com.xuuxxi.reggie;

import java.io.File;
import java.io.IOException;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/9
 */
public class MyTest {
    public static void main(String[] args) throws IOException {
        File file = new File("");

        String path = file.getCanonicalPath() + "\\src\\main\\resources\\templates";

        File dir = new File(path);
        if(!dir.exists()) dir.mkdir();
        System.out.println(path);
    }
}
