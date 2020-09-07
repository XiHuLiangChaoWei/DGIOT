package cn.zz.dgcc.DGIOT;

//import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import cn.zz.dgcc.DGIOT.socket.TcpSocket;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication()
@ServletComponentScan
//@EnableCreateCacheAnnotation
@MapperScan("cn.zz.dgcc.DGIOT.mapper")
public class DgiotApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(DgiotApplication.class, args);

//		System.out.println("服务已启动， 等待连接！");
//		//建立TCP连接服务,绑定端口
//		ServerSocket tcpServer = new ServerSocket(5252);
//		//接受连接,每个TCP连接都是一个java线程
//		while(true){
//			Socket clientSocket = tcpServer.accept();
//			new TcpSocket(clientSocket).start();
//		}
	}
}



