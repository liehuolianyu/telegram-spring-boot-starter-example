package com.github.xabgesagtx.example.Controller;

import com.github.xabgesagtx.example.Service.DealCloudSee;
import com.github.xabgesagtx.example.utils.FileUtils;
import com.github.xabgesagtx.example.utils.MyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;



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
    private DealCloudSee cloudSee;


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

    @Async
    @GetMapping(value = "/scan")
    public MyResponse scanner(HttpServletRequest request){
        MyResponse myResponse= new MyResponse(200,true);
        String startName = request.getParameter("fileName");
        if (!StringUtils.isEmpty(startName)){
            ReentrantLock lock = new ReentrantLock();
            String startHead = startName.substring(0,1);
            String[] tmp = startName.split(";");
            Integer allCount = 0;
            Integer startNum = 0;
            if (tmp.length==2){
                allCount = Integer.valueOf(tmp[1]);
                startNum = Integer.valueOf(tmp[0].trim().substring(1));
            }else {
                allCount = ALL_Count;
                startNum = Integer.valueOf(startName.trim().substring(1));
            }
            Integer endNUm = startNum+allCount;
            List<String> result = new CopyOnWriteArrayList<>();
            long startTime=System.currentTimeMillis();
            for (;startNum<=endNUm;startNum++){
                try {
                    cloudSee.execute(result,startHead+startNum);
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    logger.info(e.getMessage());
                }
                lock.lock();
                if (result.size()>=10){
                    logger.info("已扫描超过10个，先写入文件，最后扫描值为："+startNum);
                    FileUtils.FileWriteListforTure(cloudPath+startHead+endNUm+".txt",result);
                }
                lock.unlock();
				/*	resp = httpsPost.get(url+startHead+startNum);
					if ("200".equals(String.valueOf(resp.statusCode()))){
						logger.info(startHead+startNum+"："+resp.body());
						//logger.info(JSONObject.parseObject(resp.body()).getString("onln"));
						if(!StringUtils.isEmpty(JSONObject.parseObject(resp.body()).getString("onln"))){
						if (JSONObject.parseObject(resp.body()).getString("onln").equals("1")){
							result.add(startHead+startNum);
						}
						}
					}*/
					/*if (result.size()>10){
						logger.info("已扫描超过10个，先写入文件，最后扫描值为："+startNum);
						fileUtils.FileWriteListforTure(FILE_PATH+startHead+endNUm+".txt",result);
						result.clear();
					}*/
            }
            long endtime=System.currentTimeMillis();
            logger.info(("程序运行时间： "+(endtime-startTime)+"ms"));
            logger.info("已扫描完成，写入文件，最后扫描值为："+startNum);
            FileUtils.FileWriteListforTure(cloudPath+startHead+endNUm+".txt",result);
            result.clear();


        }else {
            myResponse.setCode(9999);
            myResponse.setSuccess(false);
        }
        return myResponse;
    }
}
