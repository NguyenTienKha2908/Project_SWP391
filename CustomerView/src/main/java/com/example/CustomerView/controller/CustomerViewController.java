// package com.example.CustomerView.controller;

// import com.example.CustomerView.entity.Category;
// import com.example.CustomerView.entity.Product;
// import com.example.CustomerView.entity.ProductDesign;
// import com.example.CustomerView.service.CategoryService;
// import com.example.CustomerView.service.ProductService;
// import com.example.CustomerView.service.ProductDesignService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// @Controller
// public class CustomerViewController {

//     @Autowired
//     private CategoryService categoryService;

//     @Autowired
//     private ProductService productService;

//     @Autowired
//     private ProductDesignService productDesignService;

//     @GetMapping("/")
//     public String index() {
//         return "index";
//     }

//     @GetMapping("/chooseCategory")
//     public String chooseCategory(Model model) {
//         model.addAttribute("categories", categoryService.getAllCategories());
//         return "chooseCategory";
//     }



// @PostMapping("/saveCategory")
// public String saveCategory(@RequestParam("categoryId") int categoryId, Model model) {
//     // Fetch the current maximum product ID and product code from the database
//     int maxProductId = productService.findMaxProductId();
//     String maxProductCode = productService.findMaxProductCode();

//     // Increment the product ID and generate the new product code
//     int newProductId = maxProductId + 1;
//     String newProductCode = generateNewProductCode(maxProductCode);

//     // Create a new product instance and set its fields
//     Product product = new Product();
//     product.setCategory_Id(categoryId);
//     product.setProduct_Id(newProductId);
//     product.setProduct_Code(newProductCode);
//     product.setProduct_Name("Default Name"); // Example default name, change as needed
//     product.setCollection_Id(null); // Setting collection ID to null
//     product.setDescription("Default Description"); // Example description, change as needed
//     product.setGender("Unspecified"); // Example gender, change as needed
//     product.setSize(1); // Example size, change as needed
//     product.setImg_Url("default-image-url.jpg"); // Example image URL, change as needed
//     product.setStatus(true); // Example status, change as needed

//     productService.saveProduct(product);

//     // Create a new product design instance and set its fields
//     int maxProductDesignId = productDesignService.findMaxProductDesignId();
//     String maxProductDesignCode = productDesignService.findMaxProductDesignCode();

//     // Increment the product design ID and generate the new product design code
//     int newProductDesignId = maxProductDesignId + 1;
//     String newProductDesignCode = generateNewProductDesignCode(maxProductDesignCode);

//     ProductDesign productDesign = new ProductDesign();
//     productDesign.setProduct_Design_Id(newProductDesignId);
//     productDesign.setProduct_Design_Code(newProductDesignCode);
//     productDesign.setProduct_Id(newProductId); // Link to the new product
//     productDesign.setProduct_Design_Name("Default Design Name"); // Example design name, change as needed
//     productDesign.setCategory_Id(categoryId); // Example category ID, change as needed
//     productDesign.setCollection_Id(null); // Setting collection ID to null
//     productDesign.setDescription("Default Design Description"); // Example design description, change as needed
//     productDesign.setGender("Unspecified"); // Example gender, change as needed
//     productDesign.setProduct_Size(1); // Example product size, change as needed
//     productDesign.setProduct_Design_Shell_Id(null); // Setting shell ID to null
//     productDesign.setGem_Min_Size(null); // Setting gem min size to null
//     productDesign.setGem_Max_Size(null); // Setting gem max size to null
//     productDesign.setStatus(true); // Example status, change as needed

//     productDesignService.saveProductDesign(productDesign);

//     model.addAttribute("product", product);
//     return "redirect:/chooseMaterial?productId=" + product.getProduct_Id();
// }


//     @GetMapping("/chooseMaterial")
//     public String chooseMaterial(@RequestParam("productId") int productId, Model model) {
//         // Add logic to display materials and weights
//         return "chooseMaterial";
//     }

//     @PostMapping("/saveMaterial")
//     public String saveMaterial(@RequestParam("productId") int productId, @RequestParam("materialId") int materialId,
//             @RequestParam("weight") int weight) {
//         // Add logic to save material and weight to product
//         return "redirect:/orderSummary?productId=" + productId;
//     }

//     @GetMapping("/orderSummary")
//     public String orderSummary(@RequestParam("productId") int productId, Model model) {
//         Product product = productService.getProductById(productId);
//         model.addAttribute("product", product);
//         return "orderSummary";
//     }

//     private String generateNewProductCode(String maxProductCode) {
//         // Extract the numeric part from the max product code
//         int numericPart = Integer.parseInt(maxProductCode.substring(2));
//         // Increment the numeric part and generate the new product code
//         int newNumericPart = numericPart + 1;
//         return "PO" + String.format("%05d", newNumericPart);
//     }

//     private String generateNewProductDesignCode(String maxProductDesignCode) {
//         if (maxProductDesignCode == null) {
//             // If there are no existing product design codes, start from a default value
//             return "PD00001";
//         }
//         // Extract the numeric part from the max product design code
//         int numericPart = Integer.parseInt(maxProductDesignCode.substring(2));
//         // Increment the numeric part and generate the new product design code
//         int newNumericPart = numericPart + 1;
//         return "PD" + String.format("%05d", newNumericPart);
//     }
    
// }


package com.example.CustomerView.controller;

import com.example.CustomerView.entity.Product;
import com.example.CustomerView.entity.ProductDesign;
import com.example.CustomerView.entity.ProductMaterial;
import com.example.CustomerView.service.CategoryService;
import com.example.CustomerView.service.ProductService;
import com.example.CustomerView.service.ProductDesignService;
import com.example.CustomerView.service.MaterialService;
import com.example.CustomerView.service.ProductMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Controller
public class CustomerViewController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDesignService productDesignService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ProductMaterialService productMaterialService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/chooseCategory")
    public String chooseCategory(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "chooseCategory";
    }

    @PostMapping("/saveCategory")
    @Transactional
    public String saveCategory(@RequestParam("categoryId") int categoryId, @RequestParam("size") int size, Model model) {
        // Fetch the current maximum product ID and product code from the database
        int maxProductId = productService.findMaxProductId();
        String maxProductCode = productService.findMaxProductCode();

        // Increment the product ID and generate the new product code
        int newProductId = productService.generateNewProductId();
        String newProductCode = productService.generateNewProductCode();

        // Create a new product instance and set its fields
        Product product = new Product();
        product.setCategory_Id(categoryId);
        product.setProduct_Id(newProductId);
        product.setProduct_Code(newProductCode);
        product.setProduct_Name("Default Name"); // Example default name, change as needed
        product.setCollection_Id(null); // Setting collection ID to null
        product.setDescription("Default Description"); // Example description, change as needed
        product.setGender("Unspecified"); // Example gender, change as needed
        product.setSize(size); // Set the size from the slider
        product.setImg_Url("default-image-url.jpg"); // Example image URL, change as needed
        product.setStatus(true); // Example status, change as needed

        productService.saveProduct(product);

        // Create a new product design instance and set its fields
        int maxProductDesignId = productDesignService.findMaxProductDesignId();
        String maxProductDesignCode = productDesignService.findMaxProductDesignCode();

        // Increment the product design ID and generate the new product design code
        int newProductDesignId = productDesignService.generateNewProductDesignId();
        String newProductDesignCode = productDesignService.generateNewProductDesignCode();

        ProductDesign productDesign = new ProductDesign();
        productDesign.setProduct_Design_Id(newProductDesignId);
        productDesign.setProduct_Design_Code(newProductDesignCode);
        productDesign.setProduct_Id(newProductId); // Link to the new product
        productDesign.setProduct_Design_Name("Default Design Name"); // Example design name, change as needed
        productDesign.setCategory_Id(categoryId); // Example category ID, change as needed
        productDesign.setCollection_Id(null); // Setting collection ID to null
        productDesign.setDescription("Default Design Description"); // Example design description, change as needed
        productDesign.setGender("Unspecified"); // Example gender, change as needed
        productDesign.setProduct_Size(size); // Set the size from the slider
        productDesign.setProduct_Design_Shell_Id(null); // Setting shell ID to null
        productDesign.setGem_Min_Size(null); // Setting gem min size to null
        productDesign.setGem_Max_Size(null); // Setting gem max size to null
        productDesign.setStatus(true); // Example status, change as needed

        productDesignService.saveProductDesign(productDesign);

        model.addAttribute("product", product);
        return "redirect:/chooseMaterial?productId=" + product.getProduct_Id();
    }

    @GetMapping("/chooseMaterial")
    public String chooseMaterial(@RequestParam("productId") int productId, Model model) {
        model.addAttribute("productId", productId);
        model.addAttribute("materials", materialService.getAllMaterials());
        return "chooseMaterial";
    }

    @PostMapping("/saveMaterial")
    public String saveMaterial(@RequestParam("productId") int productId, @RequestParam("materialWeight") List<Integer> materialWeights,
                               @RequestParam("materialId") List<Integer> materialIds) {
        for (int i = 0; i < materialIds.size(); i++) {
            ProductMaterial productMaterial = new ProductMaterial();
            productMaterial.setProductId(productId);
            productMaterial.setMaterialId(materialIds.get(i));
            productMaterial.setWeight(materialWeights.get(i));
            productMaterialService.saveProductMaterial(productMaterial);
        }
        return "redirect:/orderSummary?productId=" + productId;
    }

    @GetMapping("/orderSummary")
    public String orderSummary(@RequestParam("productId") int productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "orderSummary";
    }
}

