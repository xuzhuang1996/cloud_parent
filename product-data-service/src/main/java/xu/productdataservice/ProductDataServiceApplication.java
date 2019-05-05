package xu.productdataservice;

import brave.sampler.Sampler;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableEurekaClient
public class ProductDataServiceApplication {
    public static void main(String[] args) {
        int port = 0;
        int defaultPort = 8003;
//        Future<Integer> future = ThreadUtil.execAsync(() ->{
//            int p = 0;
//            System.out.println("请于5秒钟内输入端口号, 推荐  8001 、 8002  或者  8003，超过5秒将默认使用 " + defaultPort);
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
//
//        if(!NetUtil.isUsableLocalPort(port)) {
//            System.err.printf("端口%d被占用了，无法启动%n", port );
//            System.exit(1);
//        }

        port = defaultPort;
        new SpringApplicationBuilder(ProductDataServiceApplication.class).properties("server.port=" + port).run(args);
    }
    //用于被追踪到图形界面，在启动类里配置 Sampler 抽样策略： ALWAYS_SAMPLE 表示持续抽样
    //,做好了后：
    // 1.访问一次 http://127.0.0.1:8012/products 通过 视图微服务去访问数据微服务，这样链路追踪服务器才知道有这事儿发生~
    // 2.然后打开链路追踪服务器 http://localhost:9411/zipkin/dependency/ 就可以看到如图所示的 视图微服务调用数据微服务 的图形了。
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
