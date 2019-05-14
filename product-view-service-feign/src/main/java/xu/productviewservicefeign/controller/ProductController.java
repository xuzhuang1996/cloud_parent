package xu.productviewservicefeign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xu.productviewservicefeign.service.ProductService;
import xu_data_service.pojo.Product;

import java.util.List;

@Controller
@RefreshScope  //实现配置和实例刷新的,对那个version
public class ProductController {

    @Autowired
    ProductService productService;

    @Value("${xu_version}")
    String version;

    @RequestMapping("/products")
    public Object products(Model m) {
        List<Product> ps = productService.listProducts();
        m.addAttribute("version", version);
        m.addAttribute("ps", ps);
        return "products";
    }
}
