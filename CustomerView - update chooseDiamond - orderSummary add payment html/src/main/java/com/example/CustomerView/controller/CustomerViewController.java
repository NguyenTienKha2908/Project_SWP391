package com.example.CustomerView.controller;

import com.example.CustomerView.entity.*;
import com.example.CustomerView.repository.MaterialRepository;
import com.example.CustomerView.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public String saveCategory(@RequestParam("categoryId") int categoryId, @RequestParam("size") int size,
            Model model) {
        int maxProductId = productService.findMaxProductId();
        String maxProductCode = productService.findMaxProductCode();
        int newProductId = maxProductId + 1;
        String newProductCode = generateNewProductCode(maxProductCode);

        Product product = new Product();
        product.setCategory_Id(categoryId);
        product.setProduct_Id(newProductId);
        product.setProduct_Code(newProductCode);
        product.setProduct_Name("Default Name");
        product.setCollection_Id(null);
        product.setDescription("Default Description");
        product.setGender("Unspecified");
        product.setSize(size);
        product.setImg_Url("default-image-url.jpg");
        product.setStatus(true);

        productService.saveProduct(product);

        int maxProductDesignId = productDesignService.findMaxProductDesignId();
        String maxProductDesignCode = productDesignService.findMaxProductDesignCode();
        int newProductDesignId = maxProductDesignId + 1;
        String newProductDesignCode = generateNewProductDesignCode(maxProductDesignCode);

        ProductDesign productDesign = new ProductDesign();
        productDesign.setProduct_Design_Id(newProductDesignId);
        productDesign.setProduct_Design_Code(newProductDesignCode);
        productDesign.setProduct_Id(newProductId);
        productDesign.setProduct_Design_Name("Default Design Name");
        productDesign.setCategory_Id(categoryId);
        productDesign.setCollection_Id(null);
        productDesign.setDescription("Default Design Description");
        productDesign.setGender("Unspecified");
        productDesign.setProduct_Size(size);
        productDesign.setProduct_Design_Shell_Id(null);
        productDesign.setGem_Min_Size(null);
        productDesign.setGem_Max_Size(null);
        productDesign.setStatus(true);

        productDesignService.saveProductDesign(productDesign);

        model.addAttribute("product", product);
        return "redirect:/chooseMaterial?productId=" + product.getProduct_Id();
    }

    @GetMapping("/chooseMaterial")
    public String chooseMaterial(@RequestParam("productId") int productId, Model model) {
        List<Material> materials = materialRepository.findAllMaterialsInProductDesignShell();
        model.addAttribute("materials", materials);
        model.addAttribute("productId", productId);
        return "chooseMaterial";
    }

    @PostMapping("/saveMaterial")
    @Transactional
    public String saveMaterial(@RequestParam("productId") int productId,
            @RequestParam Map<String, String> requestParams) {
        int selectedMaterialId = Integer.parseInt(requestParams.get("materialId"));
        int selectedMaterialWeight = Integer.parseInt(requestParams.get("materialWeight_" + selectedMaterialId));

        MaterialPriceList materialPriceList = materialPriceListService.findTopByMaterialId(selectedMaterialId);
        double price = 0;
        if (materialPriceList != null) {
            price = materialPriceList.getPrice();
        } else {
            System.out.println("No price found for Material_Id: " + selectedMaterialId);
        }

        BigDecimal formattedPrice = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
        double finalPrice = formattedPrice.doubleValue();

        ProductMaterial productMaterial = new ProductMaterial();
        productMaterial.setProduct_id(productId); // changed I to i
        productMaterial.setMaterial_id(selectedMaterialId); // changed I to i
        productMaterial.setMaterial_weight(selectedMaterialWeight); // changed W to w
        double newPrice = price * selectedMaterialWeight;
        productMaterial.setQ_price(newPrice); // changed P to p
        productMaterial.setO_price(newPrice); // changed P to p

        productMaterialService.saveProductMaterial(productMaterial);

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

        return "redirect:/chooseDiamond?productId=" + productId;
    }

    @GetMapping("/chooseDiamond")
    public String chooseDiamond(@RequestParam("productId") int productId, Model model) {
        double minWeight = diamondService.findMinWeight();
        double maxWeight = diamondService.findMaxWeight();

        BigDecimal formattedMinWeight = BigDecimal.valueOf(minWeight).setScale(2, RoundingMode.HALF_UP);
        BigDecimal formattedMaxWeight = BigDecimal.valueOf(maxWeight).setScale(2, RoundingMode.HALF_UP);

        model.addAttribute("productId", productId);
        model.addAttribute("minWeight", formattedMinWeight.doubleValue());
        model.addAttribute("maxWeight", formattedMaxWeight.doubleValue());

        return "chooseDiamond";
    }

    @PostMapping("/saveDiamondRange")
    @ResponseBody
    public ResponseEntity<List<Diamond>> saveDiamondRange(
            @RequestParam("productId") int productId,
            @RequestParam("minWeight") double minWeight,
            @RequestParam("maxWeight") double maxWeight) {

        double globalMinWeight = diamondService.findMinWeight();
        double globalMaxWeight = diamondService.findMaxWeight();

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
        return ResponseEntity.ok(diamonds);
    }

    @PostMapping("/selectDiamond")
    @Transactional
    public String selectDiamond(
            @RequestParam("productId") int productId,
            @RequestParam("diamondId") int diamondId,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "productDescription", required = false) String productDescription,
            RedirectAttributes redirectAttributes) {

        Diamond selectedDiamond = diamondService.getDiamondById(diamondId);
        if (selectedDiamond != null) {
            selectedDiamond.setProduct_Id(productId);
            selectedDiamond.setStatus(false);
            diamondService.saveDiamond(selectedDiamond);

            ProductDesign productDesign = productDesignService.findByProductId(productId);
            if (productDesign == null) {
                return "redirect:/error";
            }
            int categoryId = productDesign.getCategory_Id();
            int productSize = productDesign.getProduct_Size();

            ProductMaterial productMaterial = productMaterialService.getProductMaterialByProduct_id(productId);
            double latestMaterialPrice = productMaterial.getQ_price(); // change from Q_Price to Q_price

            ProductionOrder productionOrder = new ProductionOrder();
            productionOrder.setDate(new Date());
            productionOrder.setCustomer_id("CUS001");
            productionOrder.setCategory_id(categoryId);
            productionOrder.setProduct_size(productSize);
            productionOrder.setImg_url(selectedDiamond.getImg_Url());
            productionOrder.setDescription(productDescription != null ? productDescription : "Default Description");
            productionOrder.setQ_diamond_amount(selectedDiamond.getQ_Price());
            productionOrder.setQ_material_amount(latestMaterialPrice);
            productionOrder.setQ_production_amount(100.0);
            productionOrder.setQ_total_amount(productionOrder.getQ_diamond_amount()
                    + productionOrder.getQ_material_amount() + productionOrder.getQ_production_amount());
            productionOrder.setO_diamond_amount(productionOrder.getQ_diamond_amount());
            productionOrder.setO_material_amount(productionOrder.getQ_material_amount());
            productionOrder.setO_production_amount(productionOrder.getQ_production_amount());
            productionOrder.setO_total_amount(productionOrder.getQ_total_amount());
            productionOrder.setSales_staff_id("SS004");
            productionOrder.setDesign_staff_id(null);
            productionOrder.setProduction_staff_id("PR001");
            productionOrder.setStatus(1);
            productionOrder.setProduct_id(productId);

            productionOrderService.saveProductionOrder(productionOrder);

            Product product = productService.getProductById(productId);
            if (productName != null && !productName.isEmpty()) {
                product.setProduct_Name(productName);
            } else {
                product.setProduct_Name("Default Name");
            }
            if (productDescription != null && !productDescription.isEmpty()) {
                product.setDescription(productDescription);
            } else {
                product.setDescription("Default Description");
            }
            productService.saveProduct(product);

            redirectAttributes.addAttribute("productId", productId);
            redirectAttributes.addAttribute("orderId", productionOrder.getProduction_order_id());

            return "redirect:/orderSummary";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/orderSummary")
    public String orderSummary(@RequestParam("productId") int productId, @RequestParam("orderId") String orderId,
            Model model) {
        Product product = productService.getProductById(productId);
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(orderId);

        Diamond diamond = diamondService.getDiamondByProductId(productId);

        ProductMaterial productMaterial = productMaterialService.getProductMaterialByProduct_id(productId);
        Material material = materialService.getMaterialById(productMaterial.getMaterial_id());// change from iD to id
        Category category = categoryService.getCategoryById(product.getCategory_Id());

        model.addAttribute("product", product);
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("diamond", diamond);
        model.addAttribute("material", material);
        model.addAttribute("category", category);

        return "orderSummary";
    }

    @GetMapping("/payment")
    public String payment(@RequestParam("orderId") String orderId, Model model) {
        System.out.println("Received orderId: " + orderId); 
        model.addAttribute("orderId", orderId);
        return "payment";
    }

    private String generateNewProductCode(String maxProductCode) {
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
}
