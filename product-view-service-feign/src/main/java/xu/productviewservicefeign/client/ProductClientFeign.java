package xu.productviewservicefeign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import xu_data_service.pojo.Product;

import java.util.List;

//首先声明是feign的客户端，指定你用到的服务名称PRODUCT-DATA-SERVICE，然后在服务层注入FeiService这个接口，进行远程服务调用
//加入断路器：这就表示，如果访问的 PRODUCT-DATA-SERVICE 不可用的话，就调用 ProductClientFeignHystrix 来进行反馈信息
@FeignClient(value = "PRODUCT-DATA-SERVICE",fallback = ProductClientFeignHystrix.class)
public interface ProductClientFeign {

    @GetMapping("/products")
    public List<Product> listProdcuts();
}
