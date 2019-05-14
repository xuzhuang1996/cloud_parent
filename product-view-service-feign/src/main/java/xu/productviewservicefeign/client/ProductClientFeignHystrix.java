package xu.productviewservicefeign.client;

import org.springframework.stereotype.Component;
import xu_data_service.pojo.Product;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductClientFeignHystrix implements ProductClientFeign{
    public List<Product> listProdcuts(){
        List<Product> result = new ArrayList<>();
        result.add(new Product(0,"产品数据微服务不可用",0));
        return result;
    }

}
