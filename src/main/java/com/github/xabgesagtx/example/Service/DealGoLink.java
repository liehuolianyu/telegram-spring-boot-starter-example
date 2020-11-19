package com.github.xabgesagtx.example.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xabgesagtx.example.utils.OutputLine;
import com.github.xabgesagtx.example.utils.httpsPost;
import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class DealGoLink {
    private static final Logger logger = LoggerFactory.getLogger(DealGoLink.class);

    public String stopSpeedState() {
        StringBuilder sql = new StringBuilder();
        Connection.Response resp = null;
        String POST_URL = "https://api.golinkapi.com/connect/stop-counting";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        headers.put("Host", "api.golinkapi.com");
        headers.put("Accept", "*/*");
        headers.put("cache-control", "no-cache");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("format", "JSON_ESCAPED_UNICODE");
        headers.put("Content-Length", "711");
        param.put("token", "422623247B7652627E5544437F674553264E275F525A407644597E4046764361466E756E46653F3B2070525D783B5F5E5172572D2C572772677A755C56555570435E78263B7E762C58767D7C26577F51575544452C537120665C7B6125597D2279404D75785C5E646C72232046504452787C765E627A206D5F7C206E77774563405572466D502252505E20626E527B7866646E457E7B256D2C3B472C4C4C4C4525226E6C5D22654D637747274057555C77795B53504D7E4E4C7C702260452D60266D454653526D42236D653F70406678764173607E44407D7D4E2C61507F476E7821655E27526C2D204C664277784E6243585C2C7A4D7653786C756E505B21224C277B75503B515C7A24265745777D5027707A66467A26732929");
        param.put("mask", "27242727727775772D7276272C232770232125227276712D2220752571257672");
        param.put("version", "253A243A213A26");
        param.put("platform", "20");
        param.put("ip", "202D3A2525273A25252D3A2521203434");
        try {
            resp = httpsPost.post(headers, POST_URL, param);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if ("200".equals(String.valueOf(resp.statusCode()))) {
            logger.info("停止加速接口调用成功，返回数据为：" + URLDecoder.decode(resp.body()));
            logger.info(" 返回状态" + JSONObject.parseObject(resp.body()).getString("code"));
            if ("200".equals(JSONObject.parseObject(resp.body()).getString("code"))) {
                sql.append(OutputLine.line2);
            } else {
                sql.append(OutputLine.line3 + "\n");
                sql.append("返回码：").append(JSONObject.parseObject(resp.body()).getString("code") + "\n");
                if (JSON.parseObject(resp.body()).getString("data") != null) {
                    logger.info(" 返回数据" + JSONObject.parseObject(resp.body()).getString("data").toString());
                    sql.append("返回数据：").append(JSONObject.parseObject(resp.body()).getString("data").toString());
                }
            }
        } else {
            logger.info(" 操作失败，请查看日志");
            sql.append("操作失败，请查看日志");
        }
        return sql.toString();
    }


}

