package xu.productviewservicefeign;

import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
//修改视图微服务项目，以使得它可以把信息共享给监控中心。
//修改ProductViewServiceFeignApplication， 增加 @EnableCircuitBreaker
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class ProductViewServiceFeignApplication {

    public static void main(String[] args) {
        int port = 0;
        int defaultPort = 8013;
//        Future<Integer> future = ThreadUtil.execAsync(() ->{
//            int p = 0;
//            System.out.println("请于5秒钟内输入端口号, 推荐  8012 、 8013  或者  8014，超过5秒将默认使用"+defaultPort);
//            Scanner scanner = new Scanner(System.in);
//            while(true) {
//                String strPort = scanner.nextLine();
//                if(!NumberUtil.isInteger(strPort)) {
//                    System.err.println("只能是数字");
//                    continue;
//                }
//                else {
//                    p = Convert.toInt(strPort);
//                    scanner.close();
//                    break;
//                }
//            }
//            return p;
//        });
//        try{
//            port=future.get(5, TimeUnit.SECONDS);
//        }
//        catch (InterruptedException | ExecutionException | TimeoutException e){
//            port = defaultPort;
//        }
//        if(!NetUtil.isUsableLocalPort(port)) {
//            System.err.printf("端口%d被占用了，无法启动%n", port );
//            System.exit(1);
//        }
        port = defaultPort;
        new SpringApplicationBuilder(ProductViewServiceFeignApplication.class).properties("server.port=" + port).run(args);
    }
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}

