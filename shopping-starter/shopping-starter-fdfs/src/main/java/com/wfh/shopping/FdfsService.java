package com.wfh.shopping;


import org.apache.commons.io.FileUtils;
import org.csource.common.FdfsException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.File;
import java.io.IOException;

public class FdfsService {

    public String[] upload(byte[] imageData, String extensionName, NameValuePair[] pairs) throws IOException, FdfsException {
        TrackerClient trackerClient = new TrackerClient();//跟踪器的客户端
        //从跟踪器的客户端获取跟踪器服务器的信息
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        //存储客户端需要根据跟踪服务器去获取存储服务器的地址
        StorageClient client = new StorageClient(trackerServer, null);
        //上传文件会返回String数组,数组第一个数据是组的名字,第二个数据为在组中存储的位置
        String[] uploadResult = client.upload_file(imageData, extensionName, pairs);
        client.close();
        return uploadResult;
    }

    public String[] upload(byte[] imageData, String extensionName) throws IOException, FdfsException {
        NameValuePair[] pairs = {
                new NameValuePair("extName", extensionName)
        };
        return upload(imageData, extensionName, pairs);
    }

    //本地文件上传
    public String[] upload(String fileName) throws IOException, FdfsException {
        byte[] imageData = FileUtils.readFileToByteArray(new File(fileName));
        int index = fileName.indexOf(".");
        String extensionName = fileName.substring(index + 1);
        return upload(imageData, extensionName);
    }

    public byte[] download(String group, String path) throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        StorageClient client = new StorageClient(trackerServer, null);
        return client.download_file(group, path);
    }
    //删除文件
    public boolean deleteFile(String group ,String path) throws IOException, FdfsException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        StorageClient client = new StorageClient(trackerServer, null);
        return client.delete_file(group,path)==0;
    }
}
