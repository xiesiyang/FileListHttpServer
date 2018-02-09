package io.ayang.server;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class FileListUtil {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "/mine/%E5%AD%A6%E4%B9%A0/";
        System.out.println(URLDecoder.decode(s,"utf-8"));
    }

    public static String fileListByPath(String path){
        return fileListToHtml(readPath(path));
    }

    public static List<String> readPath(String path){
        List<String> tmpList = new ArrayList<String>();
        File file = new File(path);
        if(file.isDirectory()){
            String[] list = file.list();
            assert list != null;
            for (int i = 0; i < list.length; i++) {
                String childFilePath = path + list[i];
                File childFile = new File(childFilePath);
                if (!childFile.isHidden()) {
                    if(childFile.isDirectory()){
                        tmpList.add(childFilePath+"/");
                    }else{
                        tmpList.add(childFilePath);
                    }
                }
            }
        }
        return tmpList;
    }

    public static String fileListToHtml(List<String> tmpList){
        MessageFormat messageFormat = new MessageFormat("<p><a href=''{0}'' >{0} </a></p>");
        StringBuffer sb = new StringBuffer("");
        if(tmpList == null){
            return "";
        }
        for (int i = 0; i < tmpList.size(); i++) {
            String s = tmpList.get(i);
            sb.append(messageFormat.format(new Object[]{s}));
        }
        return sb.toString();
    }

}
