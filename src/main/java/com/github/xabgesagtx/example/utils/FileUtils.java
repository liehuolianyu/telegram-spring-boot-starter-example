package com.github.xabgesagtx.example.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
public class FileUtils {

    private static String charsetCode = "utf-8";

    /**
     * 字符流写入字符串到txt
     */

    public static void FileString(String path, String data,boolean isoverride) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path,isoverride);// 字符流
            writer.write(data);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            list.clear();
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

    /**
     * 下载文件
     * @param path 文件的位置
     * @param fileName 自定义下载文件的名称
     * @param resp http响应
     * @param req http请求
     */
    public static void downloadFile(String path, String fileName, HttpServletResponse resp, HttpServletRequest req){
        try {
            File file = new File(path);
            /**
             * 中文乱码解决
             */
            String type = req.getHeader("User-Agent").toLowerCase();
            if(type.indexOf("firefox")>0 || type.indexOf("chrome")>0){
                /**
                 * 谷歌或火狐
                 */
                fileName = new String(fileName.getBytes(charsetCode), "iso8859-1");
            }else{
                /**
                 * IE
                 */
                fileName = URLEncoder.encode(fileName, charsetCode);
            }
            // 设置响应的头部信息
            resp.setHeader("content-disposition", "attachment;filename=" + fileName);
            // 设置响应内容的类型
            resp.setContentType(getFileContentType(fileName)+"; charset=" + charsetCode);
            // 设置响应内容的长度
            resp.setContentLength((int) file.length());
            // 输出
            outStream(new FileInputStream(file), resp.getOutputStream());
        } catch (Exception e) {
            System.out.println("执行downloadFile发生了异常：" + e.getMessage());
        }
    }

    /**
     * 文件的内容类型
     */
    private static String getFileContentType(String name){
        String result = "";
        String fileType = name.toLowerCase();
        if (fileType.endsWith(".png")) {
            result = "image/png";
        } else if (fileType.endsWith(".gif")) {
            result = "image/gif";
        } else if (fileType.endsWith(".jpg") || fileType.endsWith(".jpeg")) {
            result = "image/jpeg";
        } else if(fileType.endsWith(".svg")){
            result = "image/svg+xml";
        }else if (fileType.endsWith(".doc")) {
            result = "application/msword";
        } else if (fileType.endsWith(".xls")) {
            result = "application/x-excel";
        } else if (fileType.endsWith(".zip")) {
            result = "application/zip";
        } else if (fileType.endsWith(".pdf")) {
            result = "application/pdf";
        } else {
            result = "application/octet-stream";
        }
        return result;
    }

    /**
     * 基础字节数组输出
     */
    private static void outStream(InputStream is, OutputStream os) {
        try {
            byte[] buffer = new byte[10240];
            int length = -1;
            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
                os.flush();
            }
        } catch (Exception e) {
            System.out.println("执行 outStream 发生了异常：" + e.getMessage());
        } finally {
            try {
                os.close();
            } catch (IOException e) {
            }
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }


    /**
     * 处理cloudsee文件
     * @param file
     * @return
     */
    public static String deal(File file){
        StringBuffer sbf = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String tempStr;
            int i = 0;
            int j = 1;
            insert(sbf,"<Groups>");
            insert(sbf,"<Group name=\"NewGroup"+0+ "\" CurGroup=\"true\">");
            while ((tempStr = reader.readLine()) != null) {
                if(i==36){
                    insert(sbf,"</Group>");
                    insert(sbf,"<Group name=\"NewGroup"+j+ "\" CurGroup=\"true\">");
                    j++;
                    i=0;
                }
                todo(sbf,tempStr);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        insert(sbf,"</Group>");
        insert(sbf,"</Groups>");
        return sbf.toString();
    }

    /**
     * 拼接字符时插入空行
     * @param stringBuffer
     * @param str
     */
    public static void insert(StringBuffer stringBuffer,String str){
        stringBuffer.append(str);
        stringBuffer.append("\r\n");
    }

    /**
     * 组装cloudsee数据
     * @param stringBuffer
     * @param no
     */
    public static void todo(StringBuffer stringBuffer,String no){
        insert(stringBuffer,"<Source name=\""+no+"\">");
        insert(stringBuffer,"<IP>192.168.8.128</IP>");
        insert(stringBuffer,"<Port>9101</Port>");
        insert(stringBuffer,"<NetUser>616361</NetUser>");
        insert(stringBuffer,"<Password>313331</Password>");
        insert(stringBuffer,"<bOutWindow>1</bOutWindow>");
        insert(stringBuffer,"<bAlarm>1</bAlarm>");
        insert(stringBuffer,"<bEnablePreview>1</bEnablePreview>");
        insert(stringBuffer,"<bIPCMode>0</bIPCMode>");
        insert(stringBuffer,"<bOrShow>0</bOrShow>");
        insert(stringBuffer,"<bOnlyIP>0</bOnlyIP>");
        insert(stringBuffer,"<bUseYstPort>0</bUseYstPort>");
        insert(stringBuffer,"<bUseLanChk>0</bUseLanChk>");
        insert(stringBuffer,"<CloudSEEVersion>0</CloudSEEVersion>");
        insert(stringBuffer,"<CloudSEEID>"+no+"</CloudSEEID>");
        insert(stringBuffer,"<Channel>1</Channel>");
        insert(stringBuffer,"<Stream>2</Stream>");
        insert(stringBuffer,"<Protocol>0</Protocol>");
        insert(stringBuffer,"<bDecodeOut>0</bDecodeOut>");
        insert(stringBuffer,"<nDecodeOut>1</nDecodeOut>");
        insert(stringBuffer," <nDecodePos>0</nDecodePos>");
        insert(stringBuffer,"<bMatrixOut>0</bMatrixOut>");
        insert(stringBuffer,"<nMatrixOut>1</nMatrixOut>");
        insert(stringBuffer," <nMatrixPos>0</nMatrixPos>");
        insert(stringBuffer," <bAutoConnect>1</bAutoConnect>");
        insert(stringBuffer,"<bAutoRecord>0</bAutoRecord>");
        insert(stringBuffer,"<bAutoSnapshotAlarm>0</bAutoSnapshotAlarm>");
        insert(stringBuffer," <bAutoEMap>0</bAutoEMap>");
        insert(stringBuffer," </Source>");
    }
}