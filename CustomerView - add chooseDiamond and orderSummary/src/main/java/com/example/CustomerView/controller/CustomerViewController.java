package com.example.CustomerView.controller;

import com.example.CustomerView.entity.*;
import com.example.CustomerView.repository.MaterialRepository;
import com.example.CustomerView.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        float price = 0;
        if (materialPriceList != null) {
            price = materialPriceList.getPrice();
        } else {
            System.out.println("No price found for Material_Id: " + selectedMaterialId);
        }

        BigDecimal formattedPrice = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
        float finalPrice = formattedPrice.floatValue();

        ProductMaterial productMaterial = new ProductMaterial();
        productMaterial.setProduct_Id(productId);
        productMaterial.setMaterial_Id(selectedMaterialId);
        productMaterial.setMaterial_Weight(selectedMaterialWeight);
        productMaterial.setQ_Price(price);
        productMaterial.setO_Price(0);

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
        float minWeight = diamondService.findMinWeight();
        float maxWeight = diamondService.findMaxWeight();

        BigDecimal formattedMinWeight = BigDecimal.valueOf(minWeight).setScale(2, RoundingMode.HALF_UP);
        BigDecimal formattedMaxWeight = BigDecimal.valueOf(maxWeight).setScale(2, RoundingMode.HALF_UP);

        model.addAttribute("productId", productId);
        model.addAttribute("minWeight", formattedMinWeight.floatValue());
        model.addAttribute("maxWeight", formattedMaxWeight.floatValue());

        return "chooseDiamond";
    }

    @PostMapping("/saveDiamondRange")
    @ResponseBody
    public ResponseEntity<List<Diamond>> saveDiamondRange(
            @RequestParam("productId") int productId,
            @RequestParam("minWeight") float minWeight,
            @RequestParam("maxWeight") float maxWeight) {

        float globalMinWeight = diamondService.findMinWeight();
        float globalMaxWeight = diamondService.findMaxWeight();

        if (minWeight < globalMinWeight || maxWeight > globalMaxWeight) {
            return ResponseEntity.badRequest().build();
        }

        BigDecimal formattedMinWeight = BigDecimal.valueOf(minWeight).setScale(2, RoundingMode.HALF_UP);
        BigDecimal formattedMaxWeight = BigDecimal.valueOf(maxWeight).setScale(2, RoundingMode.HALF_UP);
        float finalMinWeight = formattedMinWeight.floatValue();
        float finalMaxWeight = formattedMaxWeight.floatValue();

        ProductDesign productDesign = productDesignService.findByProductId(productId);
        if (productDesign != null) {
            productDesign.setGem_Min_Size(finalMinWeight);
            productDesign.setGem_Max_Size(finalMaxWeight);
            productDesignService.saveProductDesign(productDesign);
        }

        List<Diamond> diamonds = diamondService.findAvailableDiamondsByWeightRange(finalMinWeight, finalMaxWeight);
        return ResponseEntity.ok(diamonds);
    }

    @PostMapping("/selectDiamond")
    @Transactional
    public String selectDiamond(
            @RequestParam("productId") int productId,
            @RequestParam("diamondId") int diamondId,
            @RequestParam("description") String description,
            RedirectAttributes redirectAttributes) {

        // Fetch the selected diamond
        Diamond selectedDiamond = diamondService.getDiamondById(diamondId);
        if (selectedDiamond != null) {
            selectedDiamond.setProduct_Id(productId);
            selectedDiamond.setStatus(false);
            diamondService.saveDiamond(selectedDiamond);

            // Fetch the ProductDesign to get the category and product size
            ProductDesign productDesign = productDesignService.findByProductId(productId);
            if (productDesign == null) {
                return "redirect:/error";
            }
            int categoryId = productDesign.getCategory_Id();
            int productSize = productDesign.getProduct_Size();

            // Fetch the latest material price
            ProductMaterial productMaterial = productMaterialService.getProductMaterialByProduct_id(productId);
            float latestMaterialPrice = productMaterial.getQ_Price();

            // Create new ProductionOrder and set its properties
            ProductionOrder productionOrder = new ProductionOrder();
            productionOrder.setDate(new Date());
            productionOrder.setCustomer_id("CUS001");
            productionOrder.setCategory_id(categoryId);
            productionOrder.setProduct_size(productSize);
            productionOrder.setImg_url(selectedDiamond.getImg_Url());
            productionOrder.setDescription(description);
            productionOrder.setQ_diamond_amount(selectedDiamond.getQ_Price());
            productionOrder.setQ_material_amount(latestMaterialPrice);
            productionOrder.setQ_production_amount(100.0f);
            productionOrder.setQ_total_amount(productionOrder.getQ_diamond_amount()
                    + productionOrder.getQ_material_amount() + productionOrder.getQ_production_amount());
            productionOrder.setO_diamond_amount(productionOrder.getQ_diamond_amount() + 100);
            productionOrder.setO_material_amount(productionOrder.getQ_material_amount() + 100);
            productionOrder.setO_production_amount(productionOrder.getQ_production_amount() + 100);
            productionOrder.setO_total_amount(productionOrder.getQ_total_amount() + 300);
            productionOrder.setSales_staff_id("SS004");
            productionOrder.setDesign_staff_id(null);
            productionOrder.setProduction_staff_id("PR001");
            productionOrder.setStatus(1);
            productionOrder.setProduct_id(productId);

            productionOrderService.saveProductionOrder(productionOrder);

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
        model.addAttribute("product", product);
        model.addAttribute("productionOrder", productionOrder);
        return "orderSummary";
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
