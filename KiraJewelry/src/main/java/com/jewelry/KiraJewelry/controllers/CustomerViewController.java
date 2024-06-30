// package com.jewelry.KiraJewelry.controllers;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.jewelry.KiraJewelry.dto.DiamondResponse;
// import com.jewelry.KiraJewelry.models.Category;
// import com.jewelry.KiraJewelry.models.Customer;
// import com.jewelry.KiraJewelry.models.Diamond;
// import com.jewelry.KiraJewelry.models.Employee;
// import com.jewelry.KiraJewelry.models.Material;
// import com.jewelry.KiraJewelry.models.MaterialPriceList;
// import com.jewelry.KiraJewelry.models.Product;
// import com.jewelry.KiraJewelry.models.ProductDesign;
// import com.jewelry.KiraJewelry.models.ProductDesignShell;
// import com.jewelry.KiraJewelry.models.ProductMaterial;
// import com.jewelry.KiraJewelry.models.ProductMaterialId;
// import com.jewelry.KiraJewelry.models.ProductionOrder;
// import com.jewelry.KiraJewelry.repository.MaterialRepository;
// import com.jewelry.KiraJewelry.service.CategoryService;
// import com.jewelry.KiraJewelry.service.CustomerService;
// import com.jewelry.KiraJewelry.service.EmployeeService;
// import com.jewelry.KiraJewelry.service.ImageService;
// import com.jewelry.KiraJewelry.service.MaterialPriceListService;
// import com.jewelry.KiraJewelry.service.MaterialService;
// import com.jewelry.KiraJewelry.service.ProductDesignService;
// import com.jewelry.KiraJewelry.service.ProductDesignShellService;
// import com.jewelry.KiraJewelry.service.ProductMaterialService;
// import com.jewelry.KiraJewelry.service.ProductService;
// import com.jewelry.KiraJewelry.service.ProductionOrderService;
// import com.jewelry.KiraJewelry.service.Diamond.DiamondService;

// import jakarta.servlet.http.HttpSession;

// import java.io.IOException;
// import java.math.BigDecimal;
// import java.math.RoundingMode;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;
// import java.util.Map;

// @Controller
// public class CustomerViewController {

//     @Autowired
//     private CategoryService categoryService;

//     @Autowired
//     private ProductService productService;

//     @Autowired
//     private ProductDesignService productDesignService;

//     @Autowired
//     private MaterialService materialService;

//     @Autowired
//     private MaterialPriceListService materialPriceListService;

//     @Autowired
//     private ProductMaterialService productMaterialService;

//     @Autowired
//     private MaterialRepository materialRepository;

//     @Autowired
//     private ProductDesignShellService productDesignShellService;

//     @Autowired
//     private DiamondService diamondService;

//     @Autowired
//     private ProductionOrderService productionOrderService;

//     @Autowired
//     private CustomerService customerService;

//     @Autowired
//     private EmployeeService employeeService;

//     @Autowired
//     ImageService imageService;

//     @GetMapping("/chooseCategory")
//     public String chooseCategory(Model model) {
//         List<Category> categories = categoryService.getAllCategories();
//         // Initialize list to hold image URLs for each material
//         List<String> imagesByCategory = new ArrayList<>();

//         // Iterate through each material to get its image URL
//         for (Category category : categories) {
//             try {
//                 String imageUrl = imageService.getImgByCateogryID(String.valueOf(category.getCategory_Id()));
//                 imagesByCategory.add(imageUrl);
//                 System.out.println(imageUrl);
//             } catch (IOException ex) {
//                 ex.printStackTrace();
//             }

//         }

//         model.addAttribute("imagesByCategory", imagesByCategory);
//         model.addAttribute("categories", categories);
//         return "customer/customizeJewelry/chooseCategory";
//     }

//     @PostMapping("/saveCustomerCategory")
//     @Transactional
//     public String saveCustomerCategory(
//             @RequestParam("categoryId") int categoryId, @RequestParam("size") int size,
//             Model model) {
//         // Fetch the current maximum product ID and product code from the database
//         int maxProductId = productService.findMaxProductId();
//         String maxProductCode = productService.findMaxProductCode();

//         // Increment the product ID and generate the new product code
//         int newProductId = maxProductId + 1;
//         String newProductCode = generateNewProductCode(maxProductCode);

//         // Create a new product instance and set its fields
//         Product product = new Product();
//         product.setCategory(categoryService.getCategoryById(categoryId));
//         product.setProduct_Id(newProductId);
//         product.setProduct_Code(newProductCode);
//         product.setProduct_Name("Default Name"); // Example default name, change as needed
//         product.setCollection(null); // Setting collection ID to null
//         product.setDescription("Default Description"); // Example description, change as needed
//         product.setGender("Unspecified"); // Example gender, change as needed
//         product.setSize(size); // Set the size from the slider
//         product.setImg_Url("default-image-url.jpg"); // Example image URL, change as needed
//         product.setStatus(true); // Example status, change as needed

//         productService.saveProduct(product);

//         // Create a new product design instance and set its fields
//         int maxProductDesignId = productDesignService.findMaxProductDesignId();
//         String maxProductDesignCode = productDesignService.findMaxProductDesignCode();

//         int newProductDesignId = maxProductDesignId + 1;
//         String newProductDesignCode = generateNewProductDesignCode(maxProductDesignCode);

//         ProductDesign productDesign = new ProductDesign();
//         productDesign.setProduct_Design_Id(newProductDesignId);
//         productDesign.setProduct_Design_Code(newProductDesignCode);
//         productDesign.setProduct_Id(newProductId); // Link to the new product
//         productDesign.setProduct_Design_Name("Default Design Name"); // Example design name, change as needed
//         productDesign.setCategory_Id(categoryId); // Example category ID, change as needed
//         productDesign.setCollection_Id(null); // Setting collection ID to null
//         productDesign.setDescription("Default Design Description"); // Example design description, change as needed
//         productDesign.setGender("Unspecified"); // Example gender, change as needed
//         productDesign.setProduct_Size(size); // Set the size from the slider
//         productDesign.setProduct_Design_Shell_Id(null); // Set an appropriate shell ID (update as necessary)
//         productDesign.setGem_Min_Size(null); // Setting gem min size to null
//         productDesign.setGem_Max_Size(null); // Setting gem max size to null
//         productDesign.setStatus(true); // Example status, change as needed

//         productDesignService.saveProductDesign(productDesign);

//         model.addAttribute("product", product);

//         return "redirect:/chooseMaterial?productId=" + product.getProduct_Id();
//     }

//     @GetMapping("/chooseMaterial")
//     public String chooseMaterial(@RequestParam("productId") int productId, Model model) {
//         List<Material> materials = materialRepository.findAllMaterialsInProductDesignShell();

//         // Initialize list to hold image URLs for each material
//         List<String> imagesByMaterials = new ArrayList<>();

//         // Iterate through each material to get its image URL
//         for (Material material : materials) {
//             try {
//                 String imageUrl = imageService.getImgByMaterialID(String.valueOf(material.getMaterial_Id()));
//                 imagesByMaterials.add(imageUrl);
//                 System.out.println(imageUrl);
//             } catch (IOException ex) {
//                 ex.printStackTrace();
//             }

//         }

//         model.addAttribute("imagesByMaterials", imagesByMaterials);
//         model.addAttribute("materials", materials);
//         model.addAttribute("productId", productId);
//         return "customer/customizeJewelry/chooseMaterial";
//     }

//     @PostMapping("/saveCustomerMaterial")
//     @Transactional
//     public String saveCustomerMaterial(
//             @RequestParam("productId") int productId,
//             @RequestParam Map<String, String> requestParams) {
//         int selectedMaterialId = Integer.parseInt(requestParams.get("materialId"));
//         int selectedMaterialWeight = Integer.parseInt(requestParams.get("materialWeight_" + selectedMaterialId));

//         MaterialPriceList materialPriceList = materialPriceListService.findTopByMaterialId(selectedMaterialId);
//         float price = 0;
//         if (materialPriceList != null) {
//             price = materialPriceList.getPrice();
//         } else {
//             System.out.println("No price found for Material_Id: " + selectedMaterialId);
//         }

//         BigDecimal formattedPrice = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
//         float finalPrice = formattedPrice.floatValue();

//         ProductMaterial productMaterial = new ProductMaterial();
//         // Save the ProductMaterial entity
//         ProductMaterialId productMaterialId = new ProductMaterialId();
//         productMaterialId.setProduct_Id(productId);
//         productMaterialId.setMaterial_Id(selectedMaterialId);
//         productMaterial.setId(productMaterialId);

//         productMaterial.setMaterial_Weight(selectedMaterialWeight);
//         float newPrice = price*selectedMaterialWeight;
//         productMaterial.setQ_Price(newPrice);
//         productMaterial.setO_Price(newPrice);

//         productMaterialService.saveProductMaterial(productMaterial);

//         ProductDesignShell productDesignShell = productDesignShellService.findByMaterialIdAndWeight(selectedMaterialId,
//                 selectedMaterialWeight);
//         if (productDesignShell != null) {
//             int productDesignShellId = productDesignShell.getProduct_Design_Shell_Id();
//             ProductDesign productDesign = productDesignService.findByProductId(productId);
//             if (productDesign != null) {
//                 productDesign.setProduct_Design_Shell_Id(productDesignShellId);
//                 productDesignService.saveProductDesign(productDesign);
//             }
//         }

//         return "redirect:/chooseDiamond?productId=" + productId;
//     }

//     @GetMapping("/chooseDiamond")
//     public String chooseDiamond(@RequestParam("productId") int productId, Model model) {
//         float minWeight = diamondService.findMinWeight();
//         float maxWeight = diamondService.findMaxWeight();

//         BigDecimal formattedMinWeight = BigDecimal.valueOf(minWeight).setScale(2, RoundingMode.HALF_UP);
//         BigDecimal formattedMaxWeight = BigDecimal.valueOf(maxWeight).setScale(2, RoundingMode.HALF_UP);

//         model.addAttribute("productId", productId);
//         model.addAttribute("minWeight", formattedMinWeight.floatValue());
//         model.addAttribute("maxWeight", formattedMaxWeight.floatValue());

//         return "customer/customizeJewelry/chooseDiamond";
//     }

//     @PostMapping("/saveDiamondRange")
//     @ResponseBody
//     public ResponseEntity<List<DiamondResponse>> saveDiamondRange(
//             @RequestParam("productId") int productId,
//             @RequestParam("minWeight") float minWeight,
//             @RequestParam("maxWeight") float maxWeight) {

//         float globalMinWeight = diamondService.findMinWeight();
//         float globalMaxWeight = diamondService.findMaxWeight();

//         if (minWeight < globalMinWeight || maxWeight > globalMaxWeight) {
//             return ResponseEntity.badRequest().build();
//         }

//         BigDecimal formattedMinWeight = BigDecimal.valueOf(minWeight).setScale(2, RoundingMode.HALF_UP);
//         BigDecimal formattedMaxWeight = BigDecimal.valueOf(maxWeight).setScale(2, RoundingMode.HALF_UP);
//         float finalMinWeight = formattedMinWeight.floatValue();
//         float finalMaxWeight = formattedMaxWeight.floatValue();

//         ProductDesign productDesign = productDesignService.findByProductId(productId);
//         if (productDesign != null) {
//             productDesign.setGem_Min_Size(finalMinWeight);
//             productDesign.setGem_Max_Size(finalMaxWeight);
//             productDesignService.saveProductDesign(productDesign);
//         }

//         List<Diamond> diamonds = diamondService.findAvailableDiamondsByWeightRange(finalMinWeight, finalMaxWeight);

//         List<DiamondResponse> diamondResponses = new ArrayList<>();
//         for (Diamond diamond : diamonds) {
//             try {
//                 String imageUrl = imageService.getImgByDiamondID(String.valueOf(diamond.getDia_Id()));
//                 diamondResponses.add(new DiamondResponse(diamond, imageUrl));
//             } catch (IOException ex) {
//                 ex.printStackTrace();
//                 diamondResponses.add(new DiamondResponse(diamond, null)); // Add null image URL if there's an error
//             }
//         }

//         return ResponseEntity.ok(diamondResponses);
//     }

//     @PostMapping("/selectDiamond")
//     @Transactional
//     public String selectDiamond(
//             @RequestParam("productId") int productId,
//             @RequestParam("diamondId") int diamondId,
//             @RequestParam("description") String description,
//             RedirectAttributes redirectAttributes,
//             HttpSession session) throws IOException {

//         // Fetch the selected diamond
//         Diamond selectedDiamond = diamondService.getDiamondById(diamondId);
//         if (selectedDiamond != null) {
//             selectedDiamond.setProduct(productService.getProductById(productId));
//             selectedDiamond.setStatus(false);
//             diamondService.saveDiamond(selectedDiamond);

//             // Fetch the ProductDesign to get the category and product size
//             ProductDesign productDesign = productDesignService.findByProductId(productId);
//             if (productDesign == null) {
//                 return "redirect:/error";
//             }
//             int categoryId = productDesign.getCategory_Id();
//             int productSize = productDesign.getProduct_Size();

//             // Fetch the latest material price
//             ProductMaterial productMaterial = productMaterialService.getProductMaterialByProduct_id(productId);
//             float latestMaterialPrice = productMaterial.getQ_Price();

//             String customerId = (String) session.getAttribute("customerId");
//             Customer customer = customerService.getCustomerByCustomerId(customerId);

//             // Create new ProductionOrder and set its properties
//             ProductionOrder productionOrder = new ProductionOrder();
//             productionOrder.setProduction_Order_Id(generateProductionOrderId());
//             productionOrder.setDate(new Date());
//             productionOrder.setCustomer(customer);
//             productionOrder.setCategory(categoryService.getCategoryById(categoryId));
//             productionOrder.setProduct_Size(productSize);
//             // productionOrder.setImg_Url(selectedDiamond.getImg_Url());
//             productionOrder.setDescription(description);
//             productionOrder.setQ_Diamond_Amount(selectedDiamond.getQ_Price());
//             productionOrder.setQ_Material_Amount(latestMaterialPrice);
//             productionOrder.setQ_Production_Amount(100.0f);
//             productionOrder.setQ_Total_Amount(productionOrder.getQ_Diamond_Amount()
//                     + productionOrder.getQ_Material_Amount() + productionOrder.getQ_Production_Amount());
//             productionOrder.setO_Diamond_Amount(productionOrder.getQ_Diamond_Amount() + 100);
//             productionOrder.setO_Material_Amount(productionOrder.getQ_Material_Amount() + 100);
//             productionOrder.setO_Production_Amount(productionOrder.getQ_Production_Amount() + 100);
//             productionOrder.setO_Total_Amount(productionOrder.getQ_Material_Amount() + 300);

//             Employee salesStaff = employeeService.getEmployeeById("SS004");
//             Employee designStaff = employeeService.getEmployeeById("DE004");
//             Employee productionStaff = employeeService.getEmployeeById("PR004");
//             // productionOrder.setSales_Staff(salesStaff);
//             // productionOrder.setDesign_Staff(designStaff);
//             // productionOrder.setProduction_Staff(productionStaff);
//             productionOrder.setStatus("Customized");

//             productionOrder.setProduct(productService.getProductById(productId));

//             productionOrderService.saveProductionOrder(productionOrder);

//             redirectAttributes.addAttribute("productId", productId);
//             redirectAttributes.addAttribute("orderId", productionOrder.getProduction_Order_Id());

//             String cateUrl = imageService.getImgByCateogryID(String.valueOf(productionOrder.getCategory().getCategory_Id()));
//             String materialUrl = imageService.getImgByMaterialID(String.valueOf(productMaterial.getId().getMaterial_Id()));
//             String diamondUrl = imageService.getImgByDiamondID(String.valueOf(selectedDiamond.getDia_Id()));

//             Material material = materialService.getMaterialById(productMaterial.getId().getMaterial_Id());

//             redirectAttributes.addAttribute("diamond", selectedDiamond);
//             redirectAttributes.addAttribute("productMaterial", productMaterial);
//                 redirectAttributes.addAttribute("material", material);

//             redirectAttributes.addAttribute("cateUrl", cateUrl);
//             redirectAttributes.addAttribute("materialUrl", materialUrl);
//             redirectAttributes.addAttribute("diamondUrl", diamondUrl);

//             // MultipartFile cUrl
//             // String url = imageService.uploadForProductionOrder(file,
//             // "Customer_Customize_Order", customerId,
//             // productionOrder.getProduction_Order_Id());

//             return "redirect:/orderSummary";
//         } else {
//             return "redirect:/error";
//         }
//     }

//     @GetMapping("/orderSummary")
//     public String orderSummary(@RequestParam("productId") int productId, @RequestParam("orderId") String orderId,
//             Model model, RedirectAttributes redirectAttributes) {
//         Product product = productService.getProductById(productId);
//         ProductionOrder productionOrder = productionOrderService.getProductionOrderById(orderId);

//         model.addAttribute("cateUrl", redirectAttributes.getAttribute("cateUrl"));
//         model.addAttribute("materialUrl", redirectAttributes.getAttribute("materialUrl"));
//         model.addAttribute("diamondUrl", redirectAttributes.getAttribute("diamondUrl"));
//         model.addAttribute("diamond", redirectAttributes.getAttribute("diamond"));
//         model.addAttribute("material", redirectAttributes.getAttribute("material"));
//         model.addAttribute("productMaterial", redirectAttributes.getAttribute("productMaterial"));
//         model.addAttribute("product", product);
//         model.addAttribute("productionOrder", productionOrder);
//         return "customer/customizeJewelry/orderSummary";
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

//     private String generateProductionOrderId() {
//         // Lấy production_Order_Id cuối cùng đã được chèn vào
//         ProductionOrder lastOrder = productionOrderService.getTopByOrderByProduction_Order_IdDesc();
//         if (lastOrder != null) {
//             if (lastOrder.getProduction_Order_Id() != null) {
//                 String lastId = lastOrder.getProduction_Order_Id();
//                 // Trích xuất phần số và tăng lên
//                 int numericPart = Integer.parseInt(lastId.substring(3)) + 1;
//                 // Định dạng lại ID mới theo mẫu mong muốn
//                 return String.format("POI%03d", numericPart);
//             } else {
//                 // Nếu không có order nào trước đó, bắt đầu với POI001
//                 return "POI001";
//             }
//         } else {
//             return "POI001";
//         }
//     }
// }
