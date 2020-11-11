package com.github.xabgesagtx.example.utils;

import java.io.*;
import java.util.List;
public class fileUtils {

    /**
     * 字符流写入字符串到txt
     */

    public static void FileString(String path, String data) {
        try {
            FileWriter writer = new FileWriter(path);// 字符流
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 字节输出到txt
     *
     * @param path
     * @param data
     */

    public static void FileString2(String path, String data) {
        try {
            FileOutputStream outputStream = new FileOutputStream(path);// 字节流
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 设置编码格式写出到txt
     *
     * @param path
     * @param data
     */
    public static void FileString3(String path, String data) {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");// 设置编码格式
            writer.write(data);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 追加写入到txt
     *
     * @param path
     * @param data
     */

    public static void FileString4(String path, String data) {
        try {
            FileOutputStream outputStream = new FileOutputStream(path, true);// 追加写入
            outputStream.write(("\r\n" + data).getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 存储list到文件
     *
     * @param path
     * @param list
     */

    public static <T> void FileWriteList1(String path, List<T> list) {
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            ObjectOutputStream stream = new ObjectOutputStream(outputStream);
            stream.writeObject(list);
            stream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 设置编码格式存储list到txt
     *
     * @param path
     * @param list
     */


    public static <T> void FileWriteList(String path, List<T> list) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
            for (T s : list) {
                bufferedWriter.write(s.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            bufferedWriter.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     *
     * @param path
     * @param list
     * @param <T>
     */


    public static <T> void  FileWriteListforTure(String path, List<T> list){
        BufferedWriter bufferedWriter = null;
        File file = new File(path);
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path, true)));
            for (T s : list) {
                bufferedWriter.write(s.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            bufferedWriter.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    /**
     * 读取本地文件数据设置指定编码
     *
     * @param path
     */

    public static String FileInputString(String path) {
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String data = null;
            while ((data = reader.readLine()) != null) {
                buffer.append(data + "\r\n");
            }
            reader.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 根据字节读取文件
     *
     * @param path
     * @return
     */

    public static String FileInputString2(String path) {
        StringBuffer buffer = new StringBuffer();
        try {
            FileInputStream inputStream = new FileInputStream(path);
            byte[] bytes = new byte[1024];
            int bytead = 0;
            while ((bytead = inputStream.read(bytes)) != -1) {
                buffer.append(new String(bytes, 0, bytead));
            }
            inputStream.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 获取本地文件中的list
     *
     * @param path
     */


    public static <T> void FileInputList(String path) {
        try {
            FileInputStream inputStream = new FileInputStream(path);
            ObjectInputStream stream = new ObjectInputStream(inputStream);
            List<T> list = (List<T>) stream.readObject();
            inputStream.close();
            stream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 高效读取指定编码格式的文件
     * @param path
     * @return
     */

    public static String FileInput3(String path) {
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String data = null;
            while ((data = bufferedReader.readLine()) != null) {
                buffer.append(data+"\r\n");
            }
            bufferedReader.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buffer.toString();
    }
}