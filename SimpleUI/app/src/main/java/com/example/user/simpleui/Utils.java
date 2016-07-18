package com.example.user.simpleui;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by user on 2016/7/18.
 */
public class Utils {

    public static void writeFile(Context context, String fileName, String content)
    {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_APPEND);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static  String readFile(Context context, String fileName)
    {
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            byte[] buffer = new byte[2048];
            fileInputStream.read(buffer, 0, buffer.length);
            fileInputStream.close();
            return new String(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
