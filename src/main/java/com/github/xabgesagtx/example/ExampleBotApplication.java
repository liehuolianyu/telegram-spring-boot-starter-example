package com.github.xabgesagtx.example;

import com.github.xabgesagtx.example.utils.NativeLoader;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableCaching
@MapperScan("com.github.xabgesagtx.example.dao")
public class ExampleBotApplication {

	public static void main(String[] args) {
		//根据操作系统判断，如果是linux系统则加载c++方法库
		String systemType = System.getProperty("os.name");
		String ext = (systemType.toLowerCase().indexOf("win") != -1) ? ".dll" : ".so";
		if(ext.equals(".so")) {
			try {
				NativeLoader.loader( "native" );
			} catch (Exception e) {
				System.out.println("加载so库失败");
			}
			// System.loadLibrary( "v2xalgo" );
		}

		SpringApplication.run(ExampleBotApplication.class, args);
	}

}
