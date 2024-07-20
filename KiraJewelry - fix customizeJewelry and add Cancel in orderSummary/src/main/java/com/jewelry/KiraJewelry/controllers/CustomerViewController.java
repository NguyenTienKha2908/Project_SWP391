package com.jewelry.KiraJewelry.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jewelry.KiraJewelry.dto.DiamondResponse;
import com.jewelry.KiraJewelry.models.Category;
import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.Diamond;
import com.jewelry.KiraJewelry.models.Employee;
import com.jewelry.KiraJewelry.models.Material;
import com.jewelry.KiraJewelry.models.MaterialPriceList;
import com.jewelry.KiraJewelry.models.Product;
import com.jewelry.KiraJewelry.models.ProductDesign;
import com.jewelry.KiraJewelry.models.ProductDesignShell;
import com.jewelry.KiraJewelry.models.ProductMaterial;
import com.jewelry.KiraJewelry.models.ProductMaterialId;
import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.repository.MaterialRepository;
import com.jewelry.KiraJewelry.service.CategoryService;
import com.jewelry.KiraJewelry.service.CustomerService;
import com.jewelry.KiraJewelry.service.EmployeeService;
import com.jewelry.KiraJewelry.service.ImageService;
import com.jewelry.KiraJewelry.service.MaterialPriceListService;
import com.jewelry.KiraJewelry.service.MaterialService;
import com.jewelry.KiraJewelry.service.ProductDesignService;
import com.jewelry.KiraJewelry.service.ProductDesignShellService;
import com.jewelry.KiraJewelry.service.ProductMaterialService;
import com.jewelry.KiraJewelry.service.ProductService;
import com.jewelry.KiraJewelry.service.ProductionOrderService;
import com.jewelry.KiraJewelry.service.Diamond.DiamondService;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private MaterialPriceListService materialPriceListService;

    @Autowired
    private ProductMaterialService productMaterialService;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ProductDesignShellService productDesignShellService;

    @Autowired
    private DiamondService diamondService;

    @Autowired
    private ProductionOrderService productionOrderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    ImageService imageService;

    // Step 1: Hit and Check
    @GetMapping("/customizeJewelry")
    public String customizeJewelry(Model model, HttpSession session) {
        String customerId = (String) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }

        Customer customer = customerService.getCustomerByCustomerId(customerId);
        List<String> exactStatuses = Arrays.asList("C in Category", "C in Material", "C in Diamond", "C in Final Note");
        ProductionOrder latestProductionOrder = productionOrderService
                .findLatestByCustomerIdAndStatusIn(customer.getCustomer_Id(), exactStatuses);

        if (latestProductionOrder != null) {
            String status = latestProductionOrder.getStatus();
            String productionOrderId = latestProductionOrder.getProduction_Order_Id();
            int productId = latestProductionOrder.getProduct().getProduct_Id();

            if ("C in Category".equals(status)) {
                return "redirect:/chooseCategory?productOrderId=" + productionOrderId + "&productId=" + productId;
            } else if ("C in Material".equals(status)) {
                return "redirect:/chooseMaterial?productOrderId=" + productionOrderId + "&productId=" + productId;
            } else if ("C in Diamond".equals(status)) {
                return "redirect:/chooseDiamond?productOrderId=" + productionOrderId + "&productId=" + productId;
            } else if ("C in Final Note".equals(status)) {
                return "redirect:/finalNote?productOrderId=" + productionOrderId + "&productId=" + productId;
            }
        }
        // Create a new Product
        int maxProductId = productService.findMaxProductId();
        String maxProductCode = productService.findMaxProductCode();

        int newProductId = maxProductId + 1;
        String newProductCode = generateNewProductCode(maxProductCode);

        Product product = new Product();
        product.setProduct_Id(newProductId);
        product.setProduct_Code(newProductCode);
        product.setProduct_Name("Default Name");
        product.setCollection(null);
        product.setDescription("Default Description");
        product.setGender("Unspecified");
        product.setSize(0); // Default size
        product.setStatus(false);

        productService.saveProduct(product);
        model.addAttribute("productId", newProductId);

        // Create a new ProductionOrder
        ProductionOrder newProductionOrder = new ProductionOrder();
        newProductionOrder.setProduction_Order_Id(generateProductionOrderId());
        newProductionOrder.setDate(new Date());
        newProductionOrder.setCustomer(customer);
        newProductionOrder.setSales_Staff("SS004"); // Default sales staff ID
        newProductionOrder.setProduction_Staff("PR004"); // Default production staff ID
        newProductionOrder.setStatus("C in Category");
        newProductionOrder.setProduct(productService.getProductById(newProductId));
        productionOrderService.saveProductionOrder(newProductionOrder);

        // Create a new ProductDesign
        int maxProductDesignId = productDesignService.findMaxProductDesignId();
        String maxProductDesignCode = productDesignService.findMaxProductDesignCode();

        int newProductDesignId = maxProductDesignId + 1;
        String newProductDesignCode = generateNewProductDesignCode(maxProductDesignCode);

        ProductDesign productDesign = new ProductDesign();
        productDesign.setProduct_Design_Id(newProductDesignId);
        productDesign.setProduct_Design_Code(newProductDesignCode);
        productDesign.setProduct_Id(product.getProduct_Id());
        productDesign.setProduct_Design_Name("Default Design Name");
        productDesign.setCategory_Id(null);
        productDesign.setCollection_Id(null);
        productDesign.setDescription("Default Design Description");
        productDesign.setGender("Unspecified");
        productDesign.setProduct_Size(0);
        productDesign.setProduct_Design_Shell_Id(null);
        productDesign.setGem_Min_Size(null);
        productDesign.setGem_Max_Size(null);
        productDesign.setStatus(false);
        productDesignService.saveProductDesign(productDesign);
        return "redirect:/chooseCategory?productOrderId=" + newProductionOrder.getProduction_Order_Id() + "&productId="
                + newProductId;
    }

    // Step 2: Choose Category
    @GetMapping("/chooseCategory")
    public String chooseCategory(@RequestParam("productId") Integer productId,
            @RequestParam("productOrderId") String productOrderId, Model model) {
        List<Category> categories = categoryService.getAllCategories();
        List<String> imagesByCategory = new ArrayList<>();

        for (Category category : categories) {
            try {
                String imageUrl = imageService.getImgByCateogryID(String.valueOf(category.getCategory_Id()));
                imagesByCategory.add(imageUrl);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        model.addAttribute("imagesByCategory", imagesByCategory);
        model.addAttribute("categories", categories);
        model.addAttribute("productId", productId);
        model.addAttribute("productOrderId", productOrderId);
        return "customer/customizeJewelry/chooseCategory";
    }

    // Step 3: Save Category
    @PostMapping("/saveCustomerCategory")
    @Transactional
    public String saveCustomerCategory(
            @RequestParam("productId") int productId,
            @RequestParam("categoryId") int categoryId,
            @RequestParam("size") int size,
            Model model) {
        // Log the received parameters
        System.out.println("Received productId: " + productId);
        System.out.println("Received categoryId: " + categoryId);
        System.out.println("Received size: " + size);

        Product product = productService.getProductById(productId);
        if (product != null) {
            product.setCategory(categoryService.getCategoryById(categoryId));
            product.setSize(size);
            productService.saveProduct(product);
        }

        ProductDesign productDesign = productDesignService.findByProductId(product.getProduct_Id());
        if (productDesign == null) {
            int maxProductDesignId = productDesignService.findMaxProductDesignId();
            String maxProductDesignCode = productDesignService.findMaxProductDesignCode();

            int newProductDesignId = maxProductDesignId + 1;
            String newProductDesignCode = generateNewProductDesignCode(maxProductDesignCode);

            productDesign = new ProductDesign();
            productDesign.setProduct_Design_Id(newProductDesignId);
            productDesign.setProduct_Design_Code(newProductDesignCode);
            productDesign.setProduct_Id(product.getProduct_Id());
            productDesign.setProduct_Design_Name("Default Design Name");
            productDesign.setCategory_Id(categoryId);
            productDesign.setCollection_Id(null);
            productDesign.setDescription("Default Design Description");
            productDesign.setGender("Unspecified");
            productDesign.setProduct_Size(size);
            productDesign.setProduct_Design_Shell_Id(null);
            productDesign.setGem_Min_Size(null);
            productDesign.setGem_Max_Size(null);
            productDesign.setStatus(false);
            productDesignService.saveProductDesign(productDesign);
        } else {
            productDesign.setCategory_Id(categoryId);
            productDesign.setProduct_Size(size);
            productDesignService.saveProductDesign(productDesign);
        }

        // Fetch the ProductionOrder object by productId
        ProductionOrder productionOrder = productionOrderService.getProductionOrderByProductId(productId);
        System.out.println("Fetched productionOrder: " + productionOrder);

        if (productionOrder != null) {
            // Update ProductionOrder status and category
            productionOrder.setStatus("C in Material");
            productionOrder.setCategory(categoryService.getCategoryById(categoryId));
            productionOrder.setProduct_Size(size);
            productionOrderService.saveProductionOrder(productionOrder);
        } else {
            model.addAttribute("errorMessage", "Production order not found for Product ID: " + productId);
            return "errorPage"; // Return an error page if production order is not found
        }

        model.addAttribute("productId", product.getProduct_Id());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("productOrderId", productionOrder.getProduction_Order_Id());

        return "redirect:/chooseMaterial?productId=" + product.getProduct_Id() + "&productOrderId="
                + productionOrder.getProduction_Order_Id();
    }

    // Step 4: Choose material
    @GetMapping("/chooseMaterial")
    public String chooseMaterial(@RequestParam("productId") int productId,
            @RequestParam("productOrderId") String productOrderId, Model model) {
        List<Material> materials = materialRepository.findAllMaterialsInProductDesignShell();
        List<String> imagesByMaterials = new ArrayList<>();

        for (Material material : materials) {
            try {
                String imageUrl = imageService.getImgByMaterialID(String.valueOf(material.getMaterial_Id()));
                imagesByMaterials.add(imageUrl);
            } catch (IOException ex) {
                ex.printStackTrace();
                imagesByMaterials.add(null); // Add null image URL if there's an error
            }
        }

        model.addAttribute("imagesByMaterials", imagesByMaterials);
        model.addAttribute("materials", materials);
        model.addAttribute("productId", productId);
        model.addAttribute("productOrderId", productOrderId);
        return "customer/customizeJewelry/chooseMaterial";
    }

    // Step 5: Save Material
    @PostMapping("/saveCustomerMaterial")
    @Transactional
    public String saveCustomerMaterial(@RequestParam("productId") int productId,
            @RequestParam("selectedMaterialId") int selectedMaterialId,
            @RequestParam("selectedMaterialWeight") int selectedMaterialWeight,
            @RequestParam("productOrderId") String productOrderId,
            Model model) {

        MaterialPriceList materialPriceList = materialPriceListService.findTopByMaterialId(selectedMaterialId);
        double price = 0;
        if (materialPriceList != null) {
            price = materialPriceList.getPrice();
        } else {
            System.out.println("No price found for Material_Id: " + selectedMaterialId);
        }

        BigDecimal formattedPrice = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);

        // Fetch existing ProductMaterial by Product_Id and Material_Id
        ProductMaterial existingProductMaterial = productMaterialService.getProductMaterialByProductId(productId);

        if (existingProductMaterial != null && existingProductMaterial.getId().getMaterial_Id() == selectedMaterialId) {
            // Update the existing ProductMaterial
            existingProductMaterial.setMaterial_Weight(selectedMaterialWeight);
            existingProductMaterial.setQ_Price(price * selectedMaterialWeight);
            existingProductMaterial.setO_Price(price * selectedMaterialWeight);
            productMaterialService.saveProductMaterial(existingProductMaterial);
        } else {
            // Create a new ProductMaterial
            if (existingProductMaterial != null) {
                // If the Material_Id is different, delete the existing one
                productMaterialService.deleteProductMaterial(existingProductMaterial);
            }

            ProductMaterial newProductMaterial = new ProductMaterial();
            ProductMaterialId productMaterialId = new ProductMaterialId();
            productMaterialId.setProduct_Id(productId);
            productMaterialId.setMaterial_Id(selectedMaterialId);
            newProductMaterial.setId(productMaterialId);
            newProductMaterial.setMaterial_Weight(selectedMaterialWeight);
            newProductMaterial.setQ_Price(price * selectedMaterialWeight);
            newProductMaterial.setO_Price(price * selectedMaterialWeight);
            productMaterialService.saveProductMaterial(newProductMaterial);
        }

        ProductDesignShell productDesignShell = productDesignShellService.findByMaterialIdAndWeight(selectedMaterialId,
                selectedMaterialWeight);
        if (productDesignShell != null) {
            int productDesignShellId = productDesignShell.getProduct_Design_Shell_Id();
            ProductDesign productDesign = productDesignService.findByProductId(productId);
            if (productDesign != null) {
                productDesign.setProduct_Design_Shell_Id(productDesignShellId);
                productDesignService.saveProductDesign(productDesign);
            }
        }

        // Fetch the ProductionOrder object
        ProductionOrder productionOrder = productionOrderService.getProductionOrderByProductId(productId);
        if (productionOrder != null) {
            // Update ProductionOrder status
            productionOrder.setStatus("C in Diamond");
            productionOrder.setO_Material_Amount(price * selectedMaterialWeight);
            productionOrder.setQ_Material_Amount(price * selectedMaterialWeight);
            productionOrderService.saveProductionOrder(productionOrder);
        } else {
            model.addAttribute("errorMessage", "Production order not found for Product ID: " + productId);
            return "errorPage"; // Return an error page if production order is not found
        }

        model.addAttribute("productId", productId);
        model.addAttribute("materialId", selectedMaterialId);
        model.addAttribute("productOrderId", productionOrder.getProduction_Order_Id());

        return "redirect:/chooseDiamond?productId=" + productId + "&productOrderId="
                + productionOrder.getProduction_Order_Id();
    }

    // Step 6: Choose Diamond
    @GetMapping("/chooseDiamond")
    public String chooseDiamond(
            @RequestParam("productId") int productId,
            @RequestParam("productOrderId") String productOrderId,
            Model model) {
        float minWeight = diamondService.findMinWeight();
        float maxWeight = diamondService.findMaxWeight();

        BigDecimal formattedMinWeight = BigDecimal.valueOf(minWeight).setScale(2, RoundingMode.HALF_UP);
        BigDecimal formattedMaxWeight = BigDecimal.valueOf(maxWeight).setScale(2, RoundingMode.HALF_UP);

        model.addAttribute("productId", productId);
        model.addAttribute("minWeight", formattedMinWeight.floatValue());
        model.addAttribute("maxWeight", formattedMaxWeight.floatValue());

        return "customer/customizeJewelry/chooseDiamond";
    }

    // Step 7: Save diamond
    @PostMapping("/saveDiamondRange")
    @ResponseBody
    public ResponseEntity<List<DiamondResponse>> saveDiamondRange(
            @RequestParam("productId") int productId,
            @RequestParam("minWeight") float minWeight,
            @RequestParam("maxWeight") float maxWeight) {

        float globalMinWeight = diamondService.findMinWeight();
        float globalMaxWeight = diamondService.findMaxWeight();

        if (minWeight < globalMinWeight || maxWeight > globalMaxWeight) {
            return ResponseEntity.badRequest().build();
        }

        ProductDesign productDesign = productDesignService.findByProductId(productId);
        if (productDesign != null) {
            productDesign.setGem_Min_Size(minWeight);
            productDesign.setGem_Max_Size(maxWeight);
            productDesignService.saveProductDesign(productDesign);
        }

        List<Diamond> diamonds = diamondService.findAvailableDiamondsByWeightRange(minWeight, maxWeight);

        List<DiamondResponse> diamondResponses = new ArrayList<>();
        for (Diamond diamond : diamonds) {
            try {
                String imageUrl = imageService.getImgByDiamondID(String.valueOf(diamond.getDia_Id()));
                diamondResponses.add(new DiamondResponse(diamond, imageUrl));
            } catch (IOException ex) {
                ex.printStackTrace();
                diamondResponses.add(new DiamondResponse(diamond, null)); // Add null image URL if there's an error
            }
        }

        return ResponseEntity.ok(diamondResponses);
    }

    @PostMapping("/selectDiamond")
    @Transactional
    public String selectDiamond(
            @RequestParam("productId") int productId,
            @RequestParam("diamondId") int diamondId,
            Model model,
            HttpSession session) throws IOException {

        try {
            Diamond oldDiamond = diamondService.getDiamondByProductId(productId);
            if (oldDiamond != null) {
                oldDiamond.setStatus(true);
                oldDiamond.setProduct(null);
                oldDiamond.setO_Price(1);
            }
            // Fetch the selected diamond
            Diamond selectedDiamond = diamondService.getDiamondById(diamondId);
            if (selectedDiamond != null) {
                selectedDiamond.setProduct(productService.getProductById(productId));
                selectedDiamond.setStatus(false);
                selectedDiamond.setO_Price(selectedDiamond.getQ_Price());
                diamondService.saveDiamond(selectedDiamond);

                // Fetch the ProductDesign to get the category and product size
                ProductDesign productDesign = productDesignService.findByProductId(productId);
                if (productDesign == null) {
                    throw new IllegalArgumentException("ProductDesign not found for productId: " + productId);
                }
                int categoryId = productDesign.getCategory_Id();
                int productSize = productDesign.getProduct_Size();

                // Fetch the latest material price
                ProductMaterial productMaterial = productMaterialService.getProductMaterialByProductId(productId);
                double latestMaterialPrice = productMaterial.getQ_Price();

                String customerId = (String) session.getAttribute("customerId");
                Customer customer = customerService.getCustomerByCustomerId(customerId);

                // Fetch existing ProductionOrder by productId
                ProductionOrder productionOrder = productionOrderService.getProductionOrderByProductId(productId);
                if (productionOrder == null) {
                    throw new IllegalArgumentException("ProductionOrder not found for productId: " + productId);
                }

                // Update ProductionOrder details
                productionOrder.setCategory(categoryService.getCategoryById(categoryId));
                productionOrder.setProduct_Size(productSize);
                productionOrder.setQ_Diamond_Amount(selectedDiamond.getQ_Price());
                productionOrder.setQ_Material_Amount(latestMaterialPrice);
                productionOrder.setQ_Production_Amount(100.0f);
                productionOrder.setQ_Total_Amount(productionOrder.getQ_Diamond_Amount()
                        + productionOrder.getQ_Material_Amount() + productionOrder.getQ_Production_Amount());
                productionOrder.setO_Diamond_Amount(productionOrder.getQ_Diamond_Amount());
                productionOrder.setO_Material_Amount(productionOrder.getQ_Material_Amount());
                productionOrder.setO_Production_Amount(productionOrder.getQ_Production_Amount());
                productionOrder.setO_Total_Amount(productionOrder.getQ_Total_Amount());
                productionOrder.setStatus("C in Final Note");
                productionOrder.setProduct(productService.getProductById(productId));

                productionOrderService.saveProductionOrder(productionOrder);

                model.addAttribute("productId", productId);
                model.addAttribute("orderId", productionOrder.getProduction_Order_Id());
                model.addAttribute("diamondId", selectedDiamond.getDia_Id());
                return "customer/customizeJewelry/finalNote";
            } else {
                throw new IllegalArgumentException("Diamond not found for diamondId: " + diamondId);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error selecting diamond: " + e.getMessage());
            return "errorPage";
        }
    }

    // Step 8: Go to Final Note
    @GetMapping("/finalNote")
    public String FinalNote(@RequestParam("productId") int productId,
            @RequestParam("productOrderId") String productOrderId, Model model) {

        model.addAttribute("productId", productId);
        model.addAttribute("productOrderId", productOrderId);
        return "customer/customizeJewelry/finalNote";
    }

    // Step 9: Get final note from customer
    @PostMapping("/saveFinalNote")
    @Transactional
    public String saveFinalNote(
            @RequestParam("gender") String gender,
            @RequestParam("description") String description,
            @RequestParam("name") String name,
            @RequestParam("productId") int productId,
            @RequestParam("orderId") String orderId,
            RedirectAttributes redirectAttributes,
            HttpSession session) throws IOException {

        Product product = productService.getProductById(productId);
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(orderId);

        if (gender != null && !gender.isEmpty()) {
            product.setGender(gender);
        } else {
            product.setGender("Unisex");
        }
        if (name != null && !name.isEmpty()) {
            product.setProduct_Name(name);
        } else {
            product.setProduct_Name("Customized " + product.getCategory().getCategory_Name());
        }
        if (description != null && !description.isEmpty()) {
            product.setDescription(description);
            productionOrder.setDescription(description);
        } else {
            product.setDescription("Beautiful customized " + product.getCategory().getCategory_Name());
            productionOrder.setDescription("Beautiful customized " + product.getCategory().getCategory_Name());
        }
        productService.saveProduct(product);

        // Set the status to "Customized"
        productionOrder.setStatus("Customized");
        productionOrderService.saveProductionOrder(productionOrder);

        ProductMaterial productMaterial = productMaterialService.getProductMaterialByProduct_id(productId);
        Diamond diamond = diamondService.getDiamondByProductId(productId);
        String cateUrl = imageService
                .getImgByCateogryID(String.valueOf(product.getCategory().getCategory_Id()));
        String materialUrl = imageService
                .getImgByMaterialID(String.valueOf(productMaterial.getId().getMaterial_Id()));
        String diamondUrl = imageService.getImgByDiamondID(String.valueOf(diamond.getDia_Id()));

        redirectAttributes.addAttribute("productId", productId);
        redirectAttributes.addAttribute("orderId", orderId);
        redirectAttributes.addAttribute("diamondId", diamond.getDia_Id());
        redirectAttributes.addAttribute("cateUrl", cateUrl);
        redirectAttributes.addAttribute("materialUrl", materialUrl);
        redirectAttributes.addAttribute("diamondUrl", diamondUrl);

        return "redirect:/orderSummary";
    }

    @GetMapping("/orderSummary")
    public String orderSummary(
            @RequestParam("productId") int productId,
            @RequestParam("orderId") String orderId,
            @RequestParam("diamondId") int diamondId,
            @RequestParam("cateUrl") String cateUrl,
            @RequestParam("materialUrl") String materialUrl,
            @RequestParam("diamondUrl") String diamondUrl,
            Model model, RedirectAttributes redirectAttributes) {

        Product product = productService.getProductById(productId);
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(orderId);
        ProductMaterial productMaterial = productMaterialService.getProductMaterialByProduct_id(productId);
        Diamond diamond = diamondService.getDiamondById(diamondId);

        Material material = materialService.getMaterialById(productMaterial.getId().getMaterial_Id());

        model.addAttribute("cateUrl", cateUrl);
        model.addAttribute("materialUrl", materialUrl);
        model.addAttribute("diamondUrl", diamondUrl);
        model.addAttribute("diamond", diamond);
        model.addAttribute("material", material);
        model.addAttribute("productMaterial", productMaterial);
        model.addAttribute("product", product);
        model.addAttribute("productionOrder", productionOrder);
        return "customer/customizeJewelry/orderSummary";
    }

    @PostMapping("/cancelOrder")
    @Transactional
    public String cancelOrder(@RequestParam("productId") int productId,
            @RequestParam("orderId") String orderId,
            Model model) {
        // Fetch the ProductionOrder by orderId
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(orderId);
        if (productionOrder != null) {
            // Set the status to "Canceled"
            productionOrder.setStatus("Canceled");
            productionOrderService.saveProductionOrder(productionOrder);

            // Fetch the Diamond associated with the productId
            Diamond diamond = diamondService.getDiamondByProductId(productId);
            if (diamond != null) {
                // Set the status of the diamond to 1 (assuming 1 means available or default)
                diamond.setStatus(true);
                diamondService.saveDiamond(diamond);
            }
        } else {
            model.addAttribute("errorMessage", "Order not found for Order ID: " + orderId);
            return "errorPage"; // Return an error page if order is not found
        }

        return "redirect:/"; // Redirect to the order summary page or any other page
    }

    private String generateNewProductCode(String maxProductCode) {
        if (maxProductCode == null) {
            return "PO00001";
        }
        int numericPart = Integer.parseInt(maxProductCode.substring(2));
        int newNumericPart = numericPart + 1;
        return "PO" + String.format("%05d", newNumericPart);
    }

    private String generateNewProductDesignCode(String maxProductDesignCode) {
        if (maxProductDesignCode == null) {
            return "PD00001";
        }
        int numericPart = Integer.parseInt(maxProductDesignCode.substring(2));
        int newNumericPart = numericPart + 1;
        return "PD" + String.format("%05d", newNumericPart);
    }

    private String generateProductionOrderId() {
        ProductionOrder lastOrder = productionOrderService.getTopByOrderByProduction_Order_IdDesc();
        if (lastOrder != null && lastOrder.getProduction_Order_Id() != null) {
            String lastId = lastOrder.getProduction_Order_Id();
            int numericPart = Integer.parseInt(lastId.substring(3)) + 1;
            return String.format("POI%03d", numericPart);
        }
        return "POI001";
    }
}
