package com.github.xabgesagtx.example.Controller;

import com.github.xabgesagtx.example.Service.CloudSeeScan;
import com.github.xabgesagtx.example.Service.DealCloudSee2;
import com.github.xabgesagtx.example.jni.Jni;
import com.github.xabgesagtx.example.utils.FileUtils;
import com.github.xabgesagtx.example.utils.MyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;


/**
 * 云视通相关
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "cloudSee")
public class CloudSeeController {
    private static final Logger logger = LoggerFactory.getLogger(CloudSeeController.class);

    @Value("${file.downloadpath}")
    private String downloadpath;

    @Value("${file.outputpath}")
    private String cloudPath;

    @Value("${file.count}")
    private  Integer ALL_Count;

    @Value("${file.sleep}")
    private Long SLEEP_TIME;

    @Autowired
    private DealCloudSee2 cloudSee;

    @Autowired
    private CloudSeeScan seeScan;


    @GetMapping(value = "/download")
    public void downloadfqd(HttpServletRequest request,
                            HttpServletResponse response) {
        String fileName = request.getParameter("fileName");
        if (StringUtils.isEmpty(fileName)) {
            logger.error("文件名为空，无法下载");
            response.setStatus(9999);
        } else {
            File file = new File(cloudPath + fileName+".txt");
            FileUtils.FileString(downloadpath, FileUtils.deal(file), false);
            FileUtils.downloadFile(downloadpath, "Group.cfg", response, request);
        }

    }

    @GetMapping(value = "/scan")
    public MyResponse scanner(HttpServletRequest request){
        MyResponse myResponse= new MyResponse(200,true);
        String startHead = request.getParameter("fileHead");
        String startName = request.getParameter("fileName");
        String count = request.getParameter("count");
        logger.info("请求参数为："+startName);
        if (!StringUtils.isEmpty(startName)){

            //原有调用第三方接口，已修改为调用Jni本地接口
          /*  cloudSee.execute(startHead,startName,count);*/
            cloudSee.scanNew(startHead,startName,count);
        }else {
            myResponse.setCode(9999);
            myResponse.setSuccess(false);
        }
        return myResponse;
    }

    @GetMapping(value = "/init")
    public int init(HttpServletRequest request){
        return Jni.initSdk();
    }

    @GetMapping(value = "/onlineTest")
    public int onlineTest(HttpServletRequest request){
        return Jni.isDeviceOnline("T",117760489,600);
    }

    @GetMapping(value = "/destory")
    public void destory(HttpServletRequest request){
         Jni.destory();
    }

    @GetMapping(value = "/scanReturn")
    public void scanReturn(HttpServletRequest request){
        logger.info("开始回调接口，id:"+request.getParameter("id"));
        seeScan.dealReturn(request);
    }
}
