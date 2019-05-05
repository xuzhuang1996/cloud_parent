package xu.productviewservicefeign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xu.productviewservicefeign.client.ProductClientFeign;
import xu_data_service.pojo.Product;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductClientFeign productClientFeign;
    public List<Product> listProducts(){
        return productClientFeign.listProdcuts();
    }
}
