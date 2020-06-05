package cn.zz.dgcc.DGIOT;

//import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
//@EnableCreateCacheAnnotation
@MapperScan("cn.zz.dgcc.DGIOT.mapper")
public class DgiotApplication {

	public static void main(String[] args) {
		SpringApplication.run(DgiotApplication.class, args);
	}


}
