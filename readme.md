# File List HTTP Server

## 介绍

最近看了 netty 的知识,动手写了个 http server 
根据请求路径返回服务器上相同路径下的文件   
由于 windows 和 linux 系统对于根目录的不同解释 
在 windows 上根路径应该是 jdk所在的盘的根目录

## 说明

暂时还不支持文件查看或者下载

## 运行

运行命令
```
mvn package
java -jar ./target/file_ls_http_server-1.0-SNAPSHOT.jar
```
当控制台出现
```$xslt
it is time to open browser 
```
说明程序启动,可以访问路径  

[http://localhost:9099](http://localhost:9099)

查看效果