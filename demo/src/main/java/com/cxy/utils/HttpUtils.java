package com.cxy.utils;

import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HttpUtils
 * @Author chenxiangyu-001
 * @Date 2018/12/21
 * @Version 1.0
 */
public class HttpUtils {

    public static void main(String[] args) {
        List<String> filePaths = new ArrayList<>();
//        filePaths.add("D:\\img\\第二代身份证");
//        filePaths.add("D:\\img\\电影票");
//        filePaths.add("D:\\img\\飞机票");
//        filePaths.add("D:\\img\\护照");
//        filePaths.add("D:\\img\\机动车驾驶证");
        filePaths.add("D:\\img\\警官证");
        filePaths.add("D:\\img\\名片");
        filePaths.add("D:\\img\\汽车票");
        filePaths.add("D:\\img\\入场券");
        filePaths.add("D:\\img\\社保卡");
        filePaths.add("D:\\img\\医学证明");
        filePaths.add("D:\\img\\银行卡");
        filePaths.add("D:\\img\\营业执照");
        for (String filePath : filePaths) {
            calls(filePath);
        }
//        callOne("D:\\\\workspace\\\\dk\\\\labelReader\\\\samples\\\\chepiao\\\\cp_1.jpg");
    }

    private static void callOne(String filename){
        int i = 0;
        String base64 = ImageToBase64ByLocal(filename);
        String clientId = String.valueOf(System.currentTimeMillis()+i);
        String result = callHttp(base64,clientId);
        System.out.println("n="+i+",filename="+filename+",clientId="+clientId+",result="+result);
    }

    private static void calls(String filePath){
        List<String> files = getFiles(filePath);
        for (int i = 0; i < files.size(); i++) {
            String filename = files.get(i);
            String base64 = ImageToBase64ByLocal(filename);
            String clientId = String.valueOf(System.currentTimeMillis()+i);
            String result = callHttp(base64,clientId);
            System.out.println("n="+i+",filename="+filename+",clientId="+clientId+",result="+result);
        }
    }

    public static String ImageToBase64ByLocal(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;

        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            System.out.println("图片转base64错误"+e.getMessage());
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();

        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    private static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        assert tempList != null;
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
            }
        }
        return files;
    }

    public static String callHttp(String image,String clientId){
        try {
            //请求的webservice的url
//            URL url = new URL("http://10.186.84.67:31001/ocr/identification");
            URL url = new URL("http://127.0.0.1:9800/ocr/identification");
            //创建http链接
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置请求的方法类型
            httpURLConnection.setRequestMethod("POST");
            //设置请求的内容类型
            httpURLConnection.setRequestProperty("Content-type", "application/json");
            //设置发送数据
            httpURLConnection.setDoOutput(true);
            //设置接受数据
            httpURLConnection.setDoInput(true);
            //发送数据,使用输出流
            OutputStream outputStream = httpURLConnection.getOutputStream();
            //发送的soap协议的数据
            JSONObject json = new JSONObject();
//            json.put("image",URLEncoder.encode(image, "utf-8"));
//            json.put("clientId",URLEncoder.encode(clientId, "utf-8"));
            json.put("image",image);
            json.put("clientId",clientId);
//            String content = "{image=" + URLEncoder.encode(image, "utf-8")+
//                    "&clientId="+URLEncoder.encode(clientId, "utf-8");
            //发送数据
            outputStream.write(json.toString().getBytes());
            //接收数据
            InputStream inputStream = httpURLConnection.getInputStream();
            //定义字节数组
            byte[] b = new byte[1024];
            //定义一个输出流存储接收到的数据
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //开始接收数据
            int len = 0;
            while (true) {
                len = inputStream.read(b);
                if (len == -1) {
                    //数据读完
                    break;
                }
                byteArrayOutputStream.write(b, 0, len);
            }
            //从输出流中获取读取到数据(服务端返回的)
            String response = byteArrayOutputStream.toString();
//            System.out.println(response);
            return response;
        } catch (Exception e) {
            System.out.println("http 访问错误"+e.getMessage());
        }
        return null;
    }
}
