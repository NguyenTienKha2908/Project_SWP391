package com.jewelry.KiraJewelry.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.Diamond;
import com.jewelry.KiraJewelry.models.DiamondPriceList;
import com.jewelry.KiraJewelry.models.Employee;
import com.jewelry.KiraJewelry.models.Material;
import com.jewelry.KiraJewelry.models.MaterialPriceList;
import com.jewelry.KiraJewelry.models.Product;
import com.jewelry.KiraJewelry.models.ProductMaterial;
import com.jewelry.KiraJewelry.models.ProductMaterialId;
import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.models.User;
import com.jewelry.KiraJewelry.service.CustomerService;
import com.jewelry.KiraJewelry.service.EmployeeService;
import com.jewelry.KiraJewelry.service.ImageService;
import com.jewelry.KiraJewelry.service.MaterialPriceListService;
import com.jewelry.KiraJewelry.service.MaterialService;
import com.jewelry.KiraJewelry.service.ProductMaterialService;
import com.jewelry.KiraJewelry.service.ProductService;
import com.jewelry.KiraJewelry.service.ProductionOrderService;
import com.jewelry.KiraJewelry.service.UserService;
import com.jewelry.KiraJewelry.service.Diamond.DiamondService;
import com.jewelry.KiraJewelry.service.DiamondPriceList.DiamondPriceListService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductionOrderController {

    @Autowired
    ImageService imageService;

    @Autowired
    private ProductMaterialService productMaterialService;

    @Autowired
    private ProductionOrderService productionOrderService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialPriceListService materialPriceListService;

    @Autowired
    private DiamondService diamondService;

    @Autowired
    private DiamondPriceListService diamondPriceListService;

    @Autowired
    private ProductService productService;

    // Sales Staff

    @GetMapping("/viewRequestsforSS1")
    public String getAllRequests1(Model model, HttpSession session) {
        String employeeID = (String) session.getAttribute("employeeId");
        List<ProductionOrder> productionOrders = productionOrderService.getProductionOrderByStatusAndId("Requested",
                employeeID);

        List<ProductionOrder> productionOrders2 = productionOrderService.getProductionOrderByStatusAndId("Quoted(RJ)",
                employeeID);
        List<ProductionOrder> productionOrders3 = productionOrderService.getProductionOrderByStatusAndId(
                "Quoted(CRJ)",
                employeeID);
        List<ProductionOrder> listRequests = new ArrayList<>();
        listRequests.addAll(productionOrders);
        listRequests.addAll(productionOrders2);
        listRequests.addAll(productionOrders3);

        model.addAttribute("pageOrders", listRequests);

        model.addAttribute("pageOrders", productionOrders);
        return "employee/sales_staff/viewRequest";
    }

    @GetMapping("/viewRequestsforSS")
    public String getAllRequests(
            @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            Model model, HttpSession session) {
        String employeeId = (String) session.getAttribute("employeeId");

        List<ProductionOrder> combinedList = new ArrayList<>();
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Requested", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Quoted(RJ)", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Quoted(CRJ)", sortDirection));

        // Sort the combined list
        combinedList.sort((o1, o2) -> {
            if ("ASC".equalsIgnoreCase(sortDirection)) {
                return o1.getDate().compareTo(o2.getDate());
            } else {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        // Paginate the combined list
        int start = Math.min(page * 2, combinedList.size());
        int end = Math.min((start + 2), combinedList.size());
        List<ProductionOrder> paginatedList = combinedList.subList(start, end);

        Page<ProductionOrder> combinedPage = new PageImpl<>(paginatedList, PageRequest.of(page, 2),
                combinedList.size());

        model.addAttribute("pageOrders", combinedPage);
        model.addAttribute("sortDirection", sortDirection);

        return "employee/sales_staff/viewRequest";
    }

    @GetMapping("/viewInformationRequestForSS")
    public String getRequestsForSS(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        List<String> imagesByCustomerId = null;

        Employee employee = employeeService.getEmployeeById(productionOrder.getSales_Staff());
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());
        try {
            imagesByCustomerId = imageService.getImgByCustomerID(productionOrder.getCustomer().getCustomer_Id(),
                    productionOrder.getProduction_Order_Id());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        model.addAttribute("customer", customer);
        model.addAttribute("imagesByCustomerId", imagesByCustomerId);
        model.addAttribute("listRequests", productionOrder);
        model.addAttribute("employee", employee);
        return "employee/sales_staff/viewInforRequest";
    }

    @GetMapping("/viewIngredientsPage")
    public String getIngredients(
            @RequestParam("productionOrderId") String productionOrderId,
            Model model,
            HttpSession session,
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "materialName", required = false) String materialName,
            @RequestParam(value = "material", required = false) Material material,
            @RequestParam(value = "materialPriceList", required = false) List<MaterialPriceList> materialPriceList,
            @RequestParam(value = "materials", required = false) List<Material> materials,
            @RequestParam(value = "messageDiamond", required = false) String messageDiamond,
            @RequestParam(value = "diamondName", required = false) String diamondName,
            @RequestParam(value = "diamondPriceList", required = false) List<DiamondPriceList> diamondPriceList,
            @RequestParam(value = "diamonds", required = false) List<Diamond> diamonds) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Product product = productionOrder.getProduct();
        ProductMaterial oldProductMaterial = productMaterialService
                .getProductMaterialByProductId(product.getProduct_Id());
        if (oldProductMaterial != null) {
            Material oldMaterial = materialService.getMaterialById(oldProductMaterial.getId().getMaterial_Id());
            session.setAttribute("material", oldMaterial);

        }
        Diamond oldDiamond = diamondService.getDiamondByProductId(product.getProduct_Id());
        List<Diamond> listDiamonds = diamondService.getAllDiamonds();

        // Extract unique values for dropdowns
        Set<String> origins = listDiamonds.stream().map(Diamond::getOrigin).collect(Collectors.toSet());
        Set<String> colors = listDiamonds.stream().map(Diamond::getColor).collect(Collectors.toSet());
        Set<String> clarities = listDiamonds.stream().map(Diamond::getClarity).collect(Collectors.toSet());
        Set<String> cuts = listDiamonds.stream().map(Diamond::getCut).collect(Collectors.toSet());

        model.addAttribute("listDiamonds", listDiamonds);
        model.addAttribute("origins", origins);
        model.addAttribute("colors", colors);
        model.addAttribute("clarities", clarities);
        model.addAttribute("cuts", cuts);

        session.setAttribute("productionOrder", productionOrder);
        session.setAttribute("product", product);
        model.addAttribute("product", product);
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("productionOrderId", productionOrderId);

        session.setAttribute("oldDiamond", oldDiamond);
        session.setAttribute("productMaterial", oldProductMaterial);

        model.addAttribute("message", message);
        model.addAttribute("materialName", materialName);
        model.addAttribute("materialPriceList", materialPriceList);
        model.addAttribute("materials", materials);
        model.addAttribute("messageDiamond", messageDiamond);
        model.addAttribute("diamondName", diamondName);
        model.addAttribute("materialPriceList", diamondPriceList);
        model.addAttribute("diamonds", diamonds);
        model.addAttribute("material", material);

        return "employee/sales_staff/findIngredientsPage";
    }

    @PostMapping("/updateSize")
    public String updateSize(@RequestParam("production_Order_Id") String productionOrderId,
            @RequestParam("productSize") int productSize,
            Model model) {

        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setProduct_Size(productSize);
        productionOrderService.saveProductionOrder(productionOrder);
        List<Diamond> listDiamonds = diamondService.getAllActiveDiamonds();

        // Extract unique values for dropdowns
        Set<String> origins = listDiamonds.stream().map(Diamond::getOrigin).collect(Collectors.toSet());
        Set<String> colors = listDiamonds.stream().map(Diamond::getColor).collect(Collectors.toSet());
        Set<String> clarities = listDiamonds.stream().map(Diamond::getClarity).collect(Collectors.toSet());
        Set<String> cuts = listDiamonds.stream().map(Diamond::getCut).collect(Collectors.toSet());

        model.addAttribute("listDiamonds", listDiamonds);
        model.addAttribute("origins", origins);
        model.addAttribute("colors", colors);
        model.addAttribute("clarities", clarities);
        model.addAttribute("cuts", cuts);

        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());

        return "employee/sales_staff/findIngredientsPage";

    }

    @GetMapping("/searchMaterial")
    public String searchMaterial(@RequestParam("production_Order_Id") String productionOrderId,
            @RequestParam String materialName, Model model) {

        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        List<Material> material = materialService.findByName(materialName);
        List<MaterialPriceList> listPrice = new ArrayList<>();
        for (Material m : material) {
            MaterialPriceList mpl = materialPriceListService.getLatestPriceByMaterialId(m.getMaterial_Id());
            listPrice.add(mpl);
        }
        String message = "Search for material name : " + materialName;
        List<Diamond> listDiamonds = diamondService.getAllActiveDiamonds();

        // Extract unique values for dropdowns
        Set<String> origins = listDiamonds.stream().map(Diamond::getOrigin).collect(Collectors.toSet());
        Set<String> colors = listDiamonds.stream().map(Diamond::getColor).collect(Collectors.toSet());
        Set<String> clarities = listDiamonds.stream().map(Diamond::getClarity).collect(Collectors.toSet());
        Set<String> cuts = listDiamonds.stream().map(Diamond::getCut).collect(Collectors.toSet());

        model.addAttribute("listDiamonds", listDiamonds);
        model.addAttribute("origins", origins);
        model.addAttribute("colors", colors);
        model.addAttribute("clarities", clarities);
        model.addAttribute("cuts", cuts);
        model.addAttribute("message", message);
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        model.addAttribute("productionOrderId", productionOrderId);
        model.addAttribute("materialName", materialName);
        model.addAttribute("materialPriceList", listPrice);
        model.addAttribute("materials", material);
        return "employee/sales_staff/findIngredientsPage";
    }

    @GetMapping("/saveMaterial")
    public String saveMaterial(
            @RequestParam("production_Order_Id") String productionOrderId,
            @RequestParam("material_Id") int materialId,
            Model model,
            RedirectAttributes redirectAttributes) {

        String message = "";

        try {
            ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

            int productId = productionOrder.getProduct().getProduct_Id();
            ProductMaterial productMaterials = productMaterialService.getProductMaterialByProductId(productId);
            if (productMaterials != null) {
                productMaterialService.deleteProductMaterialById(productMaterials.getId().getProduct_Id(),
                        productMaterials.getId().getMaterial_Id());
                productionOrder.setQ_Material_Amount(0);
                productionOrderService.saveProductionOrder(productionOrder);

            }
            ProductMaterial productMaterial = new ProductMaterial();
            ProductMaterialId productMaterialId = new ProductMaterialId();
            productMaterialId.setProduct_Id(productionOrder.getProduct().getProduct_Id());
            productMaterialId.setMaterial_Id(materialId);
            productMaterial.setId(productMaterialId);

            productMaterialService.saveProductMaterial(productMaterial);
            Material material = materialService.getMaterialById(materialId);
            MaterialPriceList mpl = materialPriceListService.getLatestPriceByMaterialId(materialId);
            message = "You just choose material :" + materialId + ", Material Code : " + material.getMaterial_Code()
                    + ", Material Name : " + material.getMaterial_Name() + ", Price 1 units - Day effective : "
                    + mpl.getPrice() + " - " + mpl.getEff_Date();

            redirectAttributes.addAttribute("material", material);
            model.addAttribute("productionOrder", productionOrder);
            model.addAttribute("product", productionOrder.getProduct());
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred while saving the material: " + e.getMessage();
        }
        List<Diamond> listDiamonds = diamondService.getAllActiveDiamonds();

        // Extract unique values for dropdowns
        Set<String> origins = listDiamonds.stream().map(Diamond::getOrigin).collect(Collectors.toSet());
        Set<String> colors = listDiamonds.stream().map(Diamond::getColor).collect(Collectors.toSet());
        Set<String> clarities = listDiamonds.stream().map(Diamond::getClarity).collect(Collectors.toSet());
        Set<String> cuts = listDiamonds.stream().map(Diamond::getCut).collect(Collectors.toSet());

        model.addAttribute("listDiamonds", listDiamonds);
        model.addAttribute("origins", origins);
        model.addAttribute("colors", colors);
        model.addAttribute("clarities", clarities);
        model.addAttribute("cuts", cuts);

        return "redirect:/viewIngredientsPage?productionOrderId=" + productionOrderId;
    }

    @GetMapping("/saveMaterialWeight")
    public String saveMaterialWeight(@RequestParam("production_Order_Id") String productionOrderId,
            @RequestParam(value = "materialWeight", required = false) Double materialWeight,
            Model model) {
        String message = "";
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        int productId = productionOrder.getProduct().getProduct_Id();
        ProductMaterial productMaterial = productMaterialService.getProductMaterialByProductId(productId);
        if (productMaterial == null) {
            message = "You have to choose material first !";
        } else {

            if (materialWeight == null) {
                message = "Material weight save error !";
            } else {
                productMaterial.setMaterial_Weight(materialWeight);

                int materialId = productMaterial.getId().getMaterial_Id();
                MaterialPriceList mpl = materialPriceListService.getLatestPriceByMaterialId(materialId);
                double materialPrice = mpl.getPrice();
                double productionOrderMaterialPrice = materialPrice * materialWeight;
                productionOrder.setQ_Material_Amount(productionOrderMaterialPrice);
                productionOrderService.saveProductionOrder(productionOrder);

                productMaterial.setQ_Price(productionOrderMaterialPrice);
                productMaterialService.saveProductMaterial(productMaterial);
                message = "Save material weight successfully !";
            }
        }

        List<Diamond> listDiamonds = diamondService.getAllActiveDiamonds();

        // Extract unique values for dropdowns
        Set<String> origins = listDiamonds.stream().map(Diamond::getOrigin).collect(Collectors.toSet());
        Set<String> colors = listDiamonds.stream().map(Diamond::getColor).collect(Collectors.toSet());
        Set<String> clarities = listDiamonds.stream().map(Diamond::getClarity).collect(Collectors.toSet());
        Set<String> cuts = listDiamonds.stream().map(Diamond::getCut).collect(Collectors.toSet());

        model.addAttribute("listDiamonds", listDiamonds);
        model.addAttribute("origins", origins);
        model.addAttribute("colors", colors);
        model.addAttribute("clarities", clarities);
        model.addAttribute("cuts", cuts);
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        model.addAttribute("message", message);
        // return "employee/sales_staff/findIngredientsPage";
        return "redirect:/viewIngredientsPage?productionOrderId=" +
                productionOrderId;
    }

    @GetMapping("/searchDiamond")
    public String searchDiamond(@RequestParam("production_Order_Id") String productionOrderId,
            @RequestParam(value = "diamondName", required = false) String diamondName,
            @RequestParam(value = "caratWeight", required = false) Double caratWeight,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "clarity", required = false) String clarity,
            @RequestParam(value = "cut", required = false) String cut,
            @RequestParam(value = "origin", required = false) String origin,
            Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        if (caratWeight == null && diamondName.isEmpty() && color == null && origin == null && clarity == null
                && cut == null) {
            model.addAttribute("messageDiamond", "Don't let all values null !");
        } else {

            List<Diamond> diamond = new ArrayList<Diamond>();
            if (caratWeight == null) {
                diamond = diamondService.getByListDiamondsLackWeight(diamondName, color, clarity, cut, origin);
            } else {
                diamond = diamondService.getByListDiamonds(diamondName, caratWeight, color, clarity, cut, origin);
            }
            if (diamond == null || diamond.isEmpty()) {
                model.addAttribute("messageDiamond", "Please select valid values to find diamond !");
            } else {
                List<Diamond> dia = new ArrayList<>();
                for (Diamond d : diamond) {
                    if (d.isStatus()) {
                        dia.add(d);
                    }
                }

                model.addAttribute("diamonds", dia);

                List<DiamondPriceList> diamondPriceList = new ArrayList<>();
                for (Diamond d : dia) {
                    List<DiamondPriceList> dplList = diamondPriceListService.findPriceListByCriteria(
                            d.getCarat_Weight(),
                            d.getColor(),
                            d.getClarity(),
                            d.getCut(),
                            d.getOrigin());
                    for (DiamondPriceList dpl : dplList) {
                        if (dpl != null) {
                            diamondPriceList.add(dpl);
                        }
                    }
                }
                model.addAttribute("diamondPriceList", diamondPriceList);

            }
        }
        List<Diamond> listDiamonds = diamondService.getAllDiamonds();

        // Extract unique values for dropdowns
        Set<String> origins = listDiamonds.stream().map(Diamond::getOrigin).collect(Collectors.toSet());
        Set<String> colors = listDiamonds.stream().map(Diamond::getColor).collect(Collectors.toSet());
        Set<String> clarities = listDiamonds.stream().map(Diamond::getClarity).collect(Collectors.toSet());
        Set<String> cuts = listDiamonds.stream().map(Diamond::getCut).collect(Collectors.toSet());

        model.addAttribute("listDiamonds", listDiamonds);
        model.addAttribute("origins", origins);
        model.addAttribute("colors", colors);
        model.addAttribute("clarities", clarities);
        model.addAttribute("cuts", cuts);
        model.addAttribute("diamondName", diamondName);
        model.addAttribute("caratWeight", caratWeight);
        model.addAttribute("color", color);
        model.addAttribute("clarity", clarity);
        model.addAttribute("cut", cut);
        model.addAttribute("origin", origin);
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        return "employee/sales_staff/findIngredientsPage";
    }

    @GetMapping("/saveDiamond")
    public String saveDiamond(
            @RequestParam("production_Order_Id") String productionOrderId,
            @RequestParam("dia_Id") int dia_Id,
            Model model) {

        String messageDiamond = "";

        try {
            ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

            int productId = productionOrder.getProduct().getProduct_Id();
            Diamond diamond = diamondService.getDiamondById(dia_Id);
            List<Diamond> diamonds = diamondService.getAllDiamonds();
            for (Diamond d : diamonds) {
                if (d.getProduct() != null && d.getProduct().getProduct_Id() == productId) {
                    d.setProduct(null);
                    d.setStatus(true);
                    productionOrder.setQ_Diamond_Amount(0.0);
                    productionOrderService.saveProductionOrder(productionOrder);
                }
            }
            diamond.setStatus(false);
            diamond.setProduct(productService.getProductById(productId));

            List<DiamondPriceList> dpl = diamondPriceListService.findPriceListByCriteria(diamond.getCarat_Weight(),
                    diamond.getColor(), diamond.getClarity(), diamond.getCut(), diamond.getOrigin());
            for (DiamondPriceList d : dpl) {
                productionOrder.setQ_Diamond_Amount(d.getPrice());
                productionOrderService.saveProductionOrder(productionOrder);
                messageDiamond = "You just choose diamond |" +
                        "Code : " + diamond.getDia_Code() + ";" +
                        "Name : " + diamond.getDia_Name() + ";" +
                        "Origin : " + diamond.getOrigin() + ";" +
                        "Carat weight : " + diamond.getCarat_Weight() + ";" +
                        "Color : " + diamond.getColor() + ";" +
                        "Clarity : " + diamond.getClarity() + ";" +
                        "Cut : " + diamond.getCut() + ";" +
                        "Price : " + d.getPrice();

            }
            model.addAttribute("productionOrder", productionOrder);
            model.addAttribute("product", productionOrder.getProduct());
        } catch (Exception e) {
            e.printStackTrace();
            messageDiamond = "An error occurred while saving the diamond: " + e.getMessage();
        }
        List<Diamond> listDiamonds = diamondService.getAllActiveDiamonds();

        // Extract unique values for dropdowns
        Set<String> origins = listDiamonds.stream().map(Diamond::getOrigin).collect(Collectors.toSet());
        Set<String> colors = listDiamonds.stream().map(Diamond::getColor).collect(Collectors.toSet());
        Set<String> clarities = listDiamonds.stream().map(Diamond::getClarity).collect(Collectors.toSet());
        Set<String> cuts = listDiamonds.stream().map(Diamond::getCut).collect(Collectors.toSet());
        model.addAttribute("listDiamonds", listDiamonds);
        model.addAttribute("origins", origins);
        model.addAttribute("colors", colors);
        model.addAttribute("clarities", clarities);
        model.addAttribute("cuts", cuts);
        model.addAttribute("messageDiamond", messageDiamond);
        return "redirect:/viewIngredientsPage?productionOrderId=" + productionOrderId;
    }

    @PostMapping("/saveProductionOrder")
    public String saveProductionOrder(@RequestParam("productionOrderId") String productionOrderId,
            @RequestParam(value = "staff", required = false) String employeeId,
            Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        if (employeeId != null && !employeeId.isEmpty()) {
            Employee employee = employeeService.getEmployeeById(employeeId);
            productionOrder.setSales_Staff(employee.getEmployee_Id());
            productionOrder.setStatus("Requested");
        }

        productionOrderService.saveProductionOrder(productionOrder);
        return "redirect:/viewRequestsforManager";
    }

    @GetMapping("/saveProductionPrice")
    public String saveProductionPrice(@RequestParam("productionAmount") double productionAmount,
            @RequestParam("production_Order_Id") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setQ_Production_Amount(productionAmount);
        productionOrder.setQ_Total_Amount(productionOrder.getQ_Diamond_Amount() + productionOrder.getQ_Material_Amount()
                + productionOrder.getQ_Diamond_Amount());
        productionOrderService.saveProductionOrder(productionOrder);

        List<Diamond> listDiamonds = diamondService.getAllActiveDiamonds();

        // Extract unique values for dropdowns
        Set<String> origins = listDiamonds.stream().map(Diamond::getOrigin).collect(Collectors.toSet());
        Set<String> colors = listDiamonds.stream().map(Diamond::getColor).collect(Collectors.toSet());
        Set<String> clarities = listDiamonds.stream().map(Diamond::getClarity).collect(Collectors.toSet());
        Set<String> cuts = listDiamonds.stream().map(Diamond::getCut).collect(Collectors.toSet());
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        model.addAttribute("listDiamonds", listDiamonds);
        model.addAttribute("origins", origins);
        model.addAttribute("colors", colors);
        model.addAttribute("clarities", clarities);
        model.addAttribute("cuts", cuts);
        return "redirect:/viewIngredientsPage?productionOrderId=" + productionOrderId;
    }

    @GetMapping("/previewQuotePage")
    public String viewPreviewQuotePage(@RequestParam("production_Order_Id") String productionOrderId, Model model,
            HttpSession session) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());

        ProductMaterial pm = productMaterialService
                .getProductMaterialByProductId(productionOrder.getProduct().getProduct_Id());

        Material material = materialService.getMaterialById(pm.getId().getMaterial_Id());
        MaterialPriceList mpl = materialPriceListService.getMaterialPriceListById(material.getMaterial_Id());
        Diamond diamond = diamondService.getDiamondByProductId(productionOrder.getProduct().getProduct_Id());

        List<DiamondPriceList> dpls = diamondPriceListService.findPriceListByCriteria(diamond.getCarat_Weight(),
                diamond.getColor(), diamond.getClarity(), diamond.getCut(), diamond.getOrigin());

        DiamondPriceList dpl = new DiamondPriceList();
        for (DiamondPriceList d : dpls) {
            dpl = d;
        }

        model.addAttribute("diamondPriceList", dpl);

        model.addAttribute("diamond", diamond);

        model.addAttribute("materialPriceList", mpl);
        model.addAttribute("productMaterial", pm);
        model.addAttribute("material", material);
        model.addAttribute("customer", customer);
        model.addAttribute("user", customer.getUser());
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        return "employee/sales_staff/previewQuotePage";
    }

    @GetMapping("/changeStatusProductionOrder")
    public String changeStatusProductionOrder(@RequestParam("production_Order_Id") String productionOrderId,
            Model model, HttpSession session) {

        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Quoted(NA)");
        productionOrderService.saveProductionOrder(productionOrder);

        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());

        ProductMaterial pm = productMaterialService
                .getProductMaterialByProductId(productionOrder.getProduct().getProduct_Id());

        Material material = materialService.getMaterialById(pm.getId().getMaterial_Id());
        MaterialPriceList mpl = materialPriceListService.getMaterialPriceListById(material.getMaterial_Id());
        Diamond diamond = diamondService.getDiamondByProductId(productionOrder.getProduct().getProduct_Id());

        List<DiamondPriceList> dpls = diamondPriceListService.findPriceListByCriteria(diamond.getCarat_Weight(),
                diamond.getColor(), diamond.getClarity(), diamond.getCut(), diamond.getOrigin());

        DiamondPriceList dpl = new DiamondPriceList();
        for (DiamondPriceList d : dpls) {
            dpl = d;
        }

        model.addAttribute("diamondPriceList", dpl);

        model.addAttribute("diamond", diamond);

        model.addAttribute("materialPriceList", mpl);
        model.addAttribute("productMaterial", pm);
        model.addAttribute("material", material);
        model.addAttribute("customer", customer);
        model.addAttribute("user", customer.getUser());
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        return "redirect:/viewQuotesforSS";
    }

    @GetMapping("/viewQuotesforSS")
    public String getAllQuotes(
            @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            Model model, HttpSession session) {
        String employeeId = (String) session.getAttribute("employeeId");

        List<ProductionOrder> combinedList = new ArrayList<>();
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Quoted", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Quoted(NA)", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Quoted(WC)", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Quoted(RJ)", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Quoted(CRJ)", sortDirection));

        // Filter orders by sales staff
        List<ProductionOrder> customerOrders = combinedList.stream()
                .filter(porder -> employeeId.equalsIgnoreCase(porder.getSales_Staff()))
                .collect(Collectors.toList());

        // Sort the combined list
        customerOrders.sort((o1, o2) -> {
            if ("ASC".equalsIgnoreCase(sortDirection)) {
                return o1.getDate().compareTo(o2.getDate());
            } else {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        // Paginate the combined list
        int start = Math.min(page * 2, customerOrders.size());
        int end = Math.min((start + 2), customerOrders.size());
        List<ProductionOrder> paginatedList = customerOrders.subList(start, end);

        Page<ProductionOrder> combinedPage = new PageImpl<>(paginatedList, PageRequest.of(page, 2),
                customerOrders.size());

        model.addAttribute("pageOrders", combinedPage);
        model.addAttribute("sortDirection", sortDirection);

        return "employee/sales_staff/viewQuote";
    }

    @GetMapping("/viewInformationQuoteForSS")
    public String getInforQuotesForSS(@RequestParam("production_Order_Id") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());

        ProductMaterial pm = productMaterialService
                .getProductMaterialByProductId(productionOrder.getProduct().getProduct_Id());

        Material material = materialService.getMaterialById(pm.getId().getMaterial_Id());
        MaterialPriceList mpl = materialPriceListService.getMaterialPriceListById(material.getMaterial_Id());
        Diamond diamond = diamondService.getDiamondByProductId(productionOrder.getProduct().getProduct_Id());

        List<DiamondPriceList> dpls = diamondPriceListService.findPriceListByCriteria(diamond.getCarat_Weight(),
                diamond.getColor(), diamond.getClarity(), diamond.getCut(), diamond.getOrigin());

        DiamondPriceList dpl = new DiamondPriceList();
        for (DiamondPriceList d : dpls) {
            dpl = d;
        }

        model.addAttribute("diamondPriceList", dpl);

        model.addAttribute("diamond", diamond);

        model.addAttribute("materialPriceList", mpl);
        model.addAttribute("productMaterial", pm);
        model.addAttribute("material", material);
        model.addAttribute("customer", customer);
        model.addAttribute("user", customer.getUser());
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        return "employee/sales_staff/viewInforQuote";
    }

    @GetMapping("/viewOrdersforSalesStaff")
    public String getAllOrdersForSalesStaff(
            @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            Model model, HttpSession session) {
        Pageable pageable = PageRequest.of(page, 10); // 10 items per page
        String employeeId = (String) session.getAttribute("employeeId");

        List<ProductionOrder> combinedList = new ArrayList<>();
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Completed", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Payment In Confirm", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Deposit In Confirm", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Last Payment In Confirm", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Delivering", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Delivered", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Ordered", sortDirection));

        // Filter orders by sales staff
        List<ProductionOrder> customerOrders = combinedList.stream()
                .filter(porder -> employeeId.equalsIgnoreCase(porder.getSales_Staff()))
                .collect(Collectors.toList());

        // Sort the combined list
        customerOrders.sort((o1, o2) -> {
            if ("ASC".equalsIgnoreCase(sortDirection)) {
                return o1.getDate().compareTo(o2.getDate());
            } else {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        // Paginate the combined list
        int start = Math.min(page * 2, customerOrders.size());
        int end = Math.min((start + 2), customerOrders.size());
        List<ProductionOrder> paginatedList = customerOrders.subList(start, end);

        Page<ProductionOrder> combinedPage = new PageImpl<>(paginatedList, PageRequest.of(page, 2),
                customerOrders.size());

        model.addAttribute("pageOrders", combinedPage);
        model.addAttribute("sortDirection", sortDirection);
        return "employee/sales_staff/viewOrder";
    }

    @GetMapping("/viewInOrderForSS")
    public String viewInOrderForSS(@RequestParam("orderId") String orderId, Model model) throws IOException {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(orderId);
        if (productionOrder == null) {
            // Handle the case where the production order is not found
            return "error";
        }

        String customerName = productionOrder.getCustomer().getFull_Name();
        Customer customer = customerService.getCustomerIdByCustomerName(customerName);

        ProductMaterial productMaterial = productMaterialService
                .getProductMaterialByProduct_id(productionOrder.getProduct().getProduct_Id());
        Diamond diamond = diamondService.getDiamondByProductId(productionOrder.getProduct().getProduct_Id());
        Material material = materialService.getMaterialById(productMaterial.getId().getMaterial_Id());
        Product product = productService.getProductById(productMaterial.getId().getProduct_Id());
        String cateUrl = imageService
                .getImgByCateogryID(String.valueOf(productionOrder.getCategory().getCategory_Id()));
        String materialUrl = imageService
                .getImgByMaterialID(String.valueOf(productMaterial.getId().getMaterial_Id()));
        String diamondUrl = imageService.getImgByDiamondID(String.valueOf(diamond.getDia_Id()));

        model.addAttribute("customer", customer);
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("diamond", diamond);
        model.addAttribute("product", product);
        model.addAttribute("productMaterial", productMaterial);
        model.addAttribute("material", material);
        model.addAttribute("cateUrl", cateUrl);
        model.addAttribute("materialUrl", materialUrl);
        model.addAttribute("diamondUrl", diamondUrl);

        return "employee/sales_staff/viewInForOrder";
    }

    @PostMapping("/confirmPaymentBySS")
    public String confirmPaymentBySS(
            @RequestParam("productionOrderId") String productionOrderId,
            Model model,
            HttpSession session) {
        String employeeID = (String) session.getAttribute("employeeId");
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Delivering");

        productionOrderService.saveProductionOrder(productionOrder);
        String message = "Update Status Successfully";
        return "redirect:/viewInOrderForSS?orderId=" + productionOrderId + "&update_success";
    }

    @PostMapping("/confirmDepositBySS")
    public String confirmDepositBySS(
            @RequestParam("productionOrderId") String productionOrderId,
            Model model,
            HttpSession session) {
        String employeeID = (String) session.getAttribute("employeeId");
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Ordered");

        productionOrderService.saveProductionOrder(productionOrder);
        String message = "Update Status Successfully";
        return "redirect:/viewInOrderForSS?orderId=" + productionOrderId + "&update_success";
    }

    @PostMapping("/confirmCustomizedDepositBySS")
    public String confirmCustomizedDepositBySS(
            @RequestParam("productionOrderId") String productionOrderId,
            Model model,
            HttpSession session) {
        String employeeID = (String) session.getAttribute("employeeId");
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Confirmed");

        productionOrderService.saveProductionOrder(productionOrder);
        String message = "Update Status Successfully";
        return "redirect:/viewInOrderForSS?orderId=" + productionOrderId + "&update_success";
    }

    @GetMapping("/viewRequestsforManager1")
    public String getAllRequestsForManager1(Model model, HttpSession session) {

        List<ProductionOrder> combinedList = new ArrayList<>();

        List<ProductionOrder> requestProductionOrders = productionOrderService.getProductionOrderByStatus("Requested");
        List<ProductionOrder> createdProductionOrders = productionOrderService.getProductionOrderByStatus("Created");

        List<ProductionOrder> allProductionOrders = new ArrayList<>(requestProductionOrders);
        allProductionOrders.addAll(createdProductionOrders);

        model.addAttribute("listRequests", allProductionOrders);
        return "employee/manager/viewRequest";
    }

    @GetMapping("/viewRequestsforManager")
    public String getAllRequestsForManager(
            @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            Model model, HttpSession session) {
        Pageable pageable = PageRequest.of(page, 2); // 2 items per page

        List<ProductionOrder> requestProductionOrders = productionOrderService.getAllOrdersByStatus("Requested",
                sortDirection);
        List<ProductionOrder> createdProductionOrders = productionOrderService.getAllOrdersByStatus("Created",
                sortDirection);

        List<ProductionOrder> allProductionOrders = new ArrayList<>(requestProductionOrders);
        allProductionOrders.addAll(createdProductionOrders);

        // Sort the combined list
        allProductionOrders.sort((o1, o2) -> {
            if ("ASC".equalsIgnoreCase(sortDirection)) {
                return o1.getDate().compareTo(o2.getDate());
            } else {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        // Paginate the combined list
        int start = Math.min(page * 2, allProductionOrders.size());
        int end = Math.min((start + 2), allProductionOrders.size());
        List<ProductionOrder> paginatedList = allProductionOrders.subList(start, end);

        Page<ProductionOrder> combinedPage = new PageImpl<>(paginatedList, PageRequest.of(page, 2),
                allProductionOrders.size());

        model.addAttribute("pageOrders", combinedPage);
        model.addAttribute("sortDirection", sortDirection);
        return "employee/manager/viewRequest";
    }

    @PostMapping("/deleteProductionOrder")
    public String deleteProductionOrder(@RequestParam("productionOrderId") String productionOrderId,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        String status = productionOrder.getStatus();
        Product product = productService.getProductById(productionOrder.getProduct().getProduct_Id());
        int productId = product.getProduct_Id();

        // Xóa tất cả các đối tượng liên quan trước khi xóa Product
        if (productionOrder.getStatus() != "Created" && productionOrder.getStatus() != "Requested") {
            Diamond diamond = diamondService.getDiamondByProductId(productId);
            if (diamond != null) {
                diamond.setProduct(null);
                diamond.setStatus(true);
            }
            ProductMaterial productMaterial = productMaterialService.getProductMaterialByProductId(productId);
            if (productMaterial != null) {
                productMaterialService.deleteProductMaterial(productMaterial);
            }
        }

        // Xóa ProductionOrder
        productionOrderService.deleteProductionOrderById(productionOrderId);

        // Xóa Product
        productService.deleteProductById(productId);

        // Xóa các hình ảnh liên quan
        List<String> imageLists = new ArrayList<>();
        try {
            imageLists = imageService.getImgByCustomerID(productionOrder.getCustomer().getCustomer_Id(),
                    productionOrderId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String imgUrl : imageLists) {
            try {
                imageService.deleteImage(imgUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (user.getRole_Id() == 1) {
            if ("Created".equals(status) || "Requested".equals(status)) {
                return "redirect:/userRequests";
            } else if ("Quoted(NA)".equals(status) || "Quoted".equals(status) || "Quoted(WC)".equals(status)
                    || "Quoted(RJ)".equals(status) || "Quoted(CRJ)".equals(status)) {

                return "redirect:/userQuotes";
            }
            return "redirect:/userOrders";
        } else {
            if ("Created".equals(status) || "Requested".equals(status)) {
                return "redirect:/viewRequestsforManager";
            } else if ("Quoted(NA)".equals(status) || "Quoted".equals(status) || "Quoted(WC)".equals(status)
                    || "Quoted(RJ)".equals(status) || "Quoted(CRJ)".equals(status)) {

                return "redirect:/viewQuotesforManager";
            }
            return "redirect:/viewOrdersforManager";
        }

    }

    @GetMapping("/viewQuotesforManager1")
    public String getAllQuotesForManager1(Model model, HttpSession session) {

        List<ProductionOrder> combinedList = new ArrayList<>();

        List<ProductionOrder> listQuotes = productionOrderService.getProductionOrderByStatus("Quoted");
        List<ProductionOrder> listQuotes2 = productionOrderService.getProductionOrderByStatus("Quoted(NA)");
        List<ProductionOrder> listQuotes3 = productionOrderService.getProductionOrderByStatus("Quoted(WC)");
        List<ProductionOrder> listQuotes4 = productionOrderService
                .getProductionOrderByStatus("Quoted(RJ)");
        List<ProductionOrder> listQuotes5 = productionOrderService
                .getProductionOrderByStatus("Quoted(CRJ)");

        List<ProductionOrder> lists = new ArrayList<>();

        lists.addAll(listQuotes);
        lists.addAll(listQuotes2);
        lists.addAll(listQuotes3);
        lists.addAll(listQuotes4);
        lists.addAll(listQuotes5);
        model.addAttribute("listQuotes", lists);
        return "employee/manager/viewQuote";
    }

    @GetMapping("/viewQuotesforManager")
    public String getAllQuotesForManager(
            @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            Model model, HttpSession session) {

        List<ProductionOrder> combinedList = new ArrayList<>();

        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Quoted", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Quoted(NA)", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Quoted(WC)", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Quoted(RJ)", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Quoted(CRJ)", sortDirection));

        // Sort the combined list
        combinedList.sort((o1, o2) -> {
            if ("ASC".equalsIgnoreCase(sortDirection)) {
                return o1.getDate().compareTo(o2.getDate());
            } else {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        // Paginate the combined list
        int start = Math.min(page * 10, combinedList.size());
        int end = Math.min((start + 10), combinedList.size());
        List<ProductionOrder> paginatedList = combinedList.subList(start, end);

        Page<ProductionOrder> combinedPage = new PageImpl<>(paginatedList, PageRequest.of(page, 10),
                combinedList.size());

        model.addAttribute("pageOrders", combinedPage);
        model.addAttribute("sortDirection", sortDirection);

        return "employee/manager/viewQuote";
    }

    @Autowired
    public ProductionOrderController(ProductionOrderService productionOrderService) {
        this.productionOrderService = productionOrderService;
    }

    @GetMapping("/viewOrdersforManager")
    public String getAllOrdersForManager(
            @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            Model model, HttpSession session) {
        List<ProductionOrder> combinedList = new ArrayList<>();

        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Ordered", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Ordered(NP)", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Confirmed", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Delivering", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Delivered", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Completed", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Last Payment In Confirm", sortDirection));

        // Sort the combined list
        combinedList.sort((o1, o2) -> {
            if ("ASC".equalsIgnoreCase(sortDirection)) {
                return o1.getDate().compareTo(o2.getDate());
            } else {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        // Paginate the combined list
        int start = Math.min(page * 10, combinedList.size());
        int end = Math.min((start + 10), combinedList.size());
        List<ProductionOrder> paginatedList = combinedList.subList(start, end);

        Page<ProductionOrder> combinedPage = new PageImpl<>(paginatedList, PageRequest.of(page, 10),
                combinedList.size());

        model.addAttribute("pageOrders", combinedPage);
        model.addAttribute("sortDirection", sortDirection);
        return "employee/manager/viewOrder";
    }

    @GetMapping("/viewInformationRequestForManager")
    public String getRequestsForManager(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        List<User> users = userService.getUsersByRoleId(4);
        List<Employee> employees = new ArrayList<>();
        for (User user : users) {
            int userId = user.getUser_Id();
            Employee employee = employeeService.getEmployeeByUserId(userId);
            if (employee != null) {
                employees.add(employee);
            }
        }
        List<String> imagesByCustomerId = null;
        try {
            imagesByCustomerId = imageService.getImgByCustomerID(productionOrder.getCustomer().getCustomer_Id(),
                    productionOrder.getProduction_Order_Id());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        model.addAttribute("imagesByCustomerId", imagesByCustomerId);
        model.addAttribute("listRequests", productionOrder);
        model.addAttribute("employees", employees);
        return "employee/manager/viewInforRequest";
    }

    @GetMapping("/viewInformationQuoteForManager")
    public String getQuotesForManager(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());

        ProductMaterial pm = productMaterialService
                .getProductMaterialByProductId(productionOrder.getProduct().getProduct_Id());

        Material material = materialService.getMaterialById(pm.getId().getMaterial_Id());
        MaterialPriceList mpl = materialPriceListService.getMaterialPriceListById(material.getMaterial_Id());
        Diamond diamond = diamondService.getDiamondByProductId(productionOrder.getProduct().getProduct_Id());

        List<DiamondPriceList> dpls = diamondPriceListService.findPriceListByCriteria(diamond.getCarat_Weight(),
                diamond.getColor(), diamond.getClarity(), diamond.getCut(), diamond.getOrigin());

        DiamondPriceList dpl = new DiamondPriceList();
        for (DiamondPriceList d : dpls) {
            dpl = d;
        }

        model.addAttribute("diamondPriceList", dpl);
        model.addAttribute("diamond", diamond);
        model.addAttribute("materialPriceList", mpl);
        model.addAttribute("productMaterial", pm);
        model.addAttribute("material", material);
        model.addAttribute("customer", customer);
        model.addAttribute("user", customer.getUser());
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        return "employee/manager/viewInforQuote";
    }

    @GetMapping("/rejectQuote")
    public String getRejectQuote(@RequestParam("production_Order_Id") String productionOrderId,
            Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Quoted(RJ)");
        productionOrderService.saveProductionOrder(productionOrder);
        return "redirect:/viewQuotesforSS";
    }

    @GetMapping("/acceptQuote")
    public String acceptQuote(@RequestParam("production_Order_Id") String productionOrderId) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        productionOrder.setStatus("Quoted(WC)");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/viewQuotesforManager";

    }

    // @GetMapping("/viewOrdersforManager")
    // public String getAllOrdersForManager(@RequestParam(value = "sort", required =
    // false, defaultValue = "ASC") String sortDirection,
    // @RequestParam(defaultValue = "0") int page,
    // Model model, HttpSession session) {
    // PageRequest pageable = PageRequest.of(page,10);
    // // Page<ProductionOrder> pageOrders =
    // productionOrderService.getProductionOrderByStatus("Ordered", sortDirection,
    // pageable);
    // List<ProductionOrder> listOrders =
    // productionOrderService.getProductionOrderByStatus("Ordered", sortDirection,
    // pageable);
    // List<ProductionOrder> listOrder2 =
    // productionOrderService.getProductionOrderByStatus("Ordered(NP)",
    // sortDirection, pageable);
    // List<ProductionOrder> listOrder3 =
    // productionOrderService.getProductionOrderByStatus("Confirmed", sortDirection,
    // pageable);
    // List<ProductionOrder> listOrder4 =
    // productionOrderService.getProductionOrderByStatus("Delivering",
    // sortDirection, pageable);
    // List<ProductionOrder> listOrder5 =
    // productionOrderService.getProductionOrderByStatus("Delivered", sortDirection,
    // pageable);
    // List<ProductionOrder> listOrder6 =
    // productionOrderService.getProductionOrderByStatus("Completed", sortDirection,
    // pageable);
    // List<ProductionOrder> listOrder7 = productionOrderService
    // .getProductionOrderByStatus("Last Payment In Confirm", sortDirection,
    // pageable);
    // List<ProductionOrder> lists = new ArrayList<>();
    // lists.addAll(listOrders);
    // lists.addAll(listOrder2);
    // lists.addAll(listOrder3);
    // lists.addAll(listOrder4);
    // lists.addAll(listOrder5);
    // lists.addAll(listOrder6);
    // lists.addAll(listOrder7);

    // model.addAttribute("listOrders", lists);
    // model.addAttribute("sortDirection", sortDirection);
    // return "employee/manager/viewOrder";
    // }

    // @GetMapping("/viewOrdersforManager")
    // public String getAllOrdersForManager(@RequestParam(value = "sort", required =
    // false, defaultValue = "ASC") String sortDirection,
    // @RequestParam(defaultValue = "0") int page,
    // Model model, HttpSession session) {
    // Pageable pageable = PageRequest.of(page, 10); // 10 items per page

    // // Fetch paginated and sorted results for each status
    // Page<ProductionOrder> orderedOrders =
    // productionOrderService.getAllOrdersByStatus("Ordered", sortDirection,
    // pageable);
    // Page<ProductionOrder> orderedNpOrders =
    // productionOrderService.getAllOrdersByStatus("Ordered(NP)", sortDirection,
    // pageable);
    // Page<ProductionOrder> confirmedOrders =
    // productionOrderService.getAllOrdersByStatus("Confirmed", sortDirection,
    // pageable);
    // Page<ProductionOrder> deliveringOrders =
    // productionOrderService.getAllOrdersByStatus("Delivering", sortDirection,
    // pageable);
    // Page<ProductionOrder> deliveredOrders =
    // productionOrderService.getAllOrdersByStatus("Delivered", sortDirection,
    // pageable);
    // Page<ProductionOrder> completedOrders =
    // productionOrderService.getAllOrdersByStatus("Completed", sortDirection,
    // pageable);
    // Page<ProductionOrder> lastPaymentInConfirmOrders =
    // productionOrderService.getAllOrdersByStatus("Last Payment In Confirm",
    // sortDirection, pageable);

    // // Combine all results
    // List<ProductionOrder> combinedList = new ArrayList<>();
    // combinedList.addAll(orderedOrders.getContent());
    // combinedList.addAll(orderedNpOrders.getContent());
    // combinedList.addAll(confirmedOrders.getContent());
    // combinedList.addAll(deliveringOrders.getContent());
    // combinedList.addAll(deliveredOrders.getContent());
    // combinedList.addAll(completedOrders.getContent());
    // combinedList.addAll(lastPaymentInConfirmOrders.getContent());

    // // Paginate the combined list manually
    // int start = Math.min(page * 10, combinedList.size());
    // int end = Math.min((start + 10), combinedList.size());
    // List<ProductionOrder> paginatedList = combinedList.subList(start, end);

    // Page<ProductionOrder> combinedPage = new PageImpl<>(paginatedList,
    // PageRequest.of(page, 10), combinedList.size());

    // model.addAttribute("pageOrders", combinedPage);
    // model.addAttribute("sortDirection", sortDirection);
    // return "employee/manager/viewOrder";
    // }

    @GetMapping("/viewInformationOrderForManager")
    public String getOrdersForManager(@RequestParam("productionOrderId") String productionOrderId, Model model)
            throws IOException {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());
        User user1 = userService.getUsersByUserId(customer.getUser().getUser_Id());

        List<User> sales = userService.getUsersByRoleId(4);
        List<User> designs = userService.getUsersByRoleId(5);
        List<User> productions = userService.getUsersByRoleId(6);

        List<Employee> employeesS = new ArrayList<>();
        List<Employee> employeesD = new ArrayList<>();
        List<Employee> employeesP = new ArrayList<>();

        for (User user : sales) {
            int userId = user.getUser_Id();
            Employee employee = employeeService.getEmployeeByUserId(userId);
            if (employee != null) {
                employeesS.add(employee);
            }
        }

        for (User user : designs) {
            int userId = user.getUser_Id();
            Employee employee = employeeService.getEmployeeByUserId(userId);
            if (employee != null) {
                employeesD.add(employee);
            }
        }

        for (User user : productions) {
            int userId = user.getUser_Id();
            Employee employee = employeeService.getEmployeeByUserId(userId);
            if (employee != null) {
                employeesP.add(employee);
            }
        }

        ProductMaterial productMaterial = productMaterialService
                .getProductMaterialByProduct_id(productionOrder.getProduct().getProduct_Id());
        Diamond diamond = diamondService.getDiamondByProductId(productionOrder.getProduct().getProduct_Id());
        Material material = materialService.getMaterialById(productMaterial.getId().getMaterial_Id());
        Product product = productService.getProductById(productMaterial.getId().getProduct_Id());
        String cateUrl = imageService
                .getImgByCateogryID(String.valueOf(productionOrder.getCategory().getCategory_Id()));
        String materialUrl = imageService
                .getImgByMaterialID(String.valueOf(productMaterial.getId().getMaterial_Id()));
        String diamondUrl = imageService.getImgByDiamondID(String.valueOf(diamond.getDia_Id()));

        model.addAttribute("customer", customer);
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("diamond", diamond);
        model.addAttribute("product", product);
        model.addAttribute("productMaterial", productMaterial);
        model.addAttribute("material", material);
        model.addAttribute("cateUrl", cateUrl);
        model.addAttribute("materialUrl", materialUrl);
        model.addAttribute("diamondUrl", diamondUrl);

        model.addAttribute("designStaff", employeesD);
        model.addAttribute("productionStaff", employeesP);
        model.addAttribute("user", user1);
        return "employee/manager/viewInforOrder";
    }

    @PostMapping("/assignStaffByManager")
    public String assignStaffByManager(@RequestParam("productionOrderId") String productionOrderId,
            @RequestParam(value = "dstaff", required = false) String demployeeId,
            @RequestParam(value = "pstaff", required = false) String pemployeeId,
            Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        if (demployeeId != null && !demployeeId.isEmpty()) {
            Employee employee = employeeService.getEmployeeById(demployeeId);
            productionOrder.setDesign_Staff(employee.getEmployee_Id());
        }

        if (pemployeeId != null && !pemployeeId.isEmpty()) {
            Employee employee = employeeService.getEmployeeById(pemployeeId);
            productionOrder.setProduction_Staff(employee.getEmployee_Id());
        }
        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/viewOrdersforManager";
    }

    @GetMapping("/viewEditPage")
    public String viewEditPage(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        List<Material> materials = materialService.getAllMaterials();
        // List<MaterialPriceList> mpl = materialPriceListService.getAllPriceLists();
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("materials", materials);
        // model.addAttribute("materialPriceLists", mpl);
        return "employee/sales_staff/edit";
    }

    @GetMapping("/prepareQuotePage")
    public String prepareQuotePage(@RequestParam("productionOrderId") String productionOrderId, Model model) {

        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("customer", customer);
        return "employee/sales_staff/prepareQuotePage";
    }

    @GetMapping("/viewMaterialAndGem")
    public String getViewAssets(Model model) {
        List<Material> material = materialService.getAllMaterials();
        model.addAttribute("material", material);
        return "employee/sales_staff/materialAndGem";
    }

    @GetMapping("/viewRequestsforDE1")
    public String getAllRequestsForDE1(Model model, HttpSession session) {
        String employeeID = (String) session.getAttribute("employeeId");
        List<ProductionOrder> createdOrders = productionOrderService.getProductionOrderByStatus("Ordered");
        List<ProductionOrder> requestedOrders = productionOrderService.getProductionOrderByStatus("Ordered(NP)");

        List<ProductionOrder> allOrders = new ArrayList<>();
        allOrders.addAll(createdOrders);
        allOrders.addAll(requestedOrders);

        List<ProductionOrder> employeeOrders = allOrders.stream()
                .filter(porder -> employeeID.equalsIgnoreCase(porder.getDesign_Staff()))
                .collect(Collectors.toList());

        model.addAttribute("listRequests", employeeOrders);
        return "employee/design_staff/viewRequest";
    }

    @GetMapping("/viewRequestsforDE")
    public String getAllRequestsForDE(
            @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            Model model, HttpSession session) {
        String employeeID = (String) session.getAttribute("employeeId");

        List<ProductionOrder> combinedList = new ArrayList<>();

        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Ordered", sortDirection));
        combinedList.addAll(productionOrderService.getAllOrdersByStatus("Ordered(NP)", sortDirection));

        List<ProductionOrder> employeeOrders = combinedList.stream()
                .filter(porder -> employeeID.equalsIgnoreCase(porder.getDesign_Staff()))
                .collect(Collectors.toList());
        // Sort the combined list
        employeeOrders.sort((o1, o2) -> {
            if ("ASC".equalsIgnoreCase(sortDirection)) {
                return o1.getDate().compareTo(o2.getDate());
            } else {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        // Paginate the combined list
        int start = Math.min(page * 10, employeeOrders.size());
        int end = Math.min((start + 10), employeeOrders.size());
        List<ProductionOrder> paginatedList = employeeOrders.subList(start, end);

        Page<ProductionOrder> combinedPage = new PageImpl<>(paginatedList, PageRequest.of(page, 10),
                employeeOrders.size());

        model.addAttribute("pageOrders", combinedPage);
        model.addAttribute("sortDirection", sortDirection);

        return "employee/design_staff/viewRequest";
    }

    @GetMapping("/viewInformationRequestForDE")
    public String getRequestsForDE(@RequestParam("productionOrderId") String productionOrderId, Model model)
            throws IOException {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        List<String> imagesByCustomerId = null;
        // List<String> imagesByStaffId = null;
        Map<String, List<String>> imagesByStaffId = new HashMap<>();

        Employee employee = employeeService.getEmployeeById(productionOrder.getDesign_Staff());
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());
        try {
            imagesByCustomerId = imageService.getImgByCustomerID(productionOrder.getCustomer().getCustomer_Id(),
                    productionOrder.getProduction_Order_Id());
            // imagesByStaffId = imageService.getImgByStaffId(employee.getEmployee_Id(),
            // productionOrderId);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            imagesByStaffId = imageService.getImgOrderedByStaffId(employee.getEmployee_Id());
            System.out.println(imagesByStaffId);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String messageReject = "";
        if (productionOrder.getStatus().equalsIgnoreCase("Ordered(NP)")) {
            messageReject = "The design is rejected";
        } else if (productionOrder.getStatus().equalsIgnoreCase("Ordered")) {
            messageReject = "Awaiting for customer's approval";

        }

        Product product = productService.getProductById(productionOrder.getProduct().getProduct_Id());

        ProductMaterial productMaterial = productMaterialService
                .getProductMaterialByProduct_id(product.getProduct_Id());
        Diamond diamond = diamondService.getDiamondByProductId(product.getProduct_Id());
        Material material = materialService.getMaterialById(productMaterial.getId().getMaterial_Id());

        String cateUrl = imageService
                .getImgByCateogryID(String.valueOf(product.getCategory().getCategory_Id()));
        String materialUrl = imageService
                .getImgByMaterialID(String.valueOf(productMaterial.getId().getMaterial_Id()));
        String diamondUrl = imageService.getImgByDiamondID(String.valueOf(diamond.getDia_Id()));

        model.addAttribute("messageReject", messageReject);

        model.addAttribute("productMaterial", productMaterial);
        model.addAttribute("material", material);
        model.addAttribute("diamond", diamond);
        model.addAttribute("cateUrl", cateUrl);
        model.addAttribute("materialUrl", materialUrl);
        model.addAttribute("diamondUrl", diamondUrl);

        model.addAttribute("imagesByStaffId", imagesByStaffId);
        model.addAttribute("customer", customer);
        model.addAttribute("imagesByCustomerId", imagesByCustomerId);
        model.addAttribute("imagesByStaffId", imagesByStaffId);
        model.addAttribute("listRequests", productionOrder);
        model.addAttribute("employee", employee);
        return "employee/design_staff/viewInforRequest";
    }

    @PostMapping("/uploadDesignForDE")
    public String uploadDesignForDE(
            @RequestParam("productionOrderId") String productionOrderId,
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Employee employee = employeeService.getEmployeeById(productionOrder.getDesign_Staff());
        try {
            String url = imageService.uploadForProductionOrder(file, "Customer_Design", employee.getEmployee_Id(),
                    productionOrder.getProduction_Order_Id());
            System.out.println(url);
        } catch (Exception e) {
            return "redirect:/viewInformationRequestForDE?productionOrderId=" + productionOrderId + "&error";
        }
        return "redirect:/viewInformationRequestForDE?productionOrderId=" + productionOrderId + "&success";
    }

    @GetMapping("/viewAllDesign1")
    public String viewAllDesign1(
            HttpSession session,
            Model model) {

        Map<String, List<String>> imagesByStaffId = new HashMap<>();

        Employee employee = employeeService.getEmployeeById((String) session.getAttribute("employeeId"));
        List<ProductionOrder> productionOrders = productionOrderService.getProductionOrderByStatusAndDEId("Ordered",
                employee.getEmployee_Id());
        try {
            imagesByStaffId = imageService.getImgOrderedByStaffId(employee.getEmployee_Id());
            System.out.println(imagesByStaffId);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        model.addAttribute("imagesByStaffId", imagesByStaffId);
        model.addAttribute("listRequests", productionOrders);
        model.addAttribute("employee", employee);
        return "employee/design_staff/viewAllDesign";
    }

    @GetMapping("/viewAllDesign")
    public String viewAllDesign(
            @RequestParam(defaultValue = "0") int page,
            HttpSession session,
            Model model) {

        Employee employee = employeeService.getEmployeeById((String) session.getAttribute("employeeId"));
        List<ProductionOrder> productionOrders = productionOrderService.getProductionOrderByDEEmployeeId(
                employee.getEmployee_Id());

        // Paginate the production orders list
        int pageSize = 2;
        int start = Math.min(page * pageSize, productionOrders.size());
        int end = Math.min(start + pageSize, productionOrders.size());
        List<ProductionOrder> paginatedList = productionOrders.subList(start, end);

        Map<String, List<String>> imagesByStaffId = new HashMap<>();
        try {
            imagesByStaffId = imageService.getImgOrderedByStaffId(employee.getEmployee_Id());
            System.out.println(imagesByStaffId);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Map<String, List<String>> paginatedImagesByStaffId = new HashMap<>();
        for (ProductionOrder order : paginatedList) {
            if (imagesByStaffId.containsKey(order.getProduction_Order_Id())) {
                paginatedImagesByStaffId.put(order.getProduction_Order_Id(),
                        imagesByStaffId.get(order.getProduction_Order_Id()));
            }
        }

        Page<ProductionOrder> productionOrdersPage = new PageImpl<>(paginatedList, PageRequest.of(page, pageSize),
                productionOrders.size());

        model.addAttribute("pageOrders", productionOrdersPage);
        model.addAttribute("imagesByStaffId", imagesByStaffId);
        model.addAttribute("employee", employee);
        return "employee/design_staff/viewAllDesign";
    }

    @PostMapping("/handleUserReject")
    public String handleUserReject(@RequestParam("productionOrderId") String productionOrderId, Model model,
            HttpSession session) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Ordered(NP)");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/userOrder?orderId=" + productionOrderId + "&reject";
    }

    @GetMapping("/handleUserApprove")
    public String handleUserapprove(@RequestParam("productionOrderId") String productionOrderId, Model model,
            HttpSession session) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Confirmed");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/userOrder?orderId=" + productionOrderId + "&approved";
    }

    @GetMapping("/viewRequestsforPR")
    public String getAllRequestsForPR(
            @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            Model model, HttpSession session) {
        String employeeID = (String) session.getAttribute("employeeId");
        List<ProductionOrder> createdOrders = productionOrderService.getProductionOrderByStatus("Confirmed");
        List<ProductionOrder> requestedOrders = productionOrderService.getProductionOrderByStatus("Completed");

        List<ProductionOrder> allOrders = new ArrayList<>();
        allOrders.addAll(createdOrders);
        allOrders.addAll(requestedOrders);

        List<ProductionOrder> employeeOrders = allOrders.stream()
                .filter(porder -> employeeID.equalsIgnoreCase(porder.getProduction_Staff()))
                .collect(Collectors.toList());

        // Sort the combined list
        employeeOrders.sort((o1, o2) -> {
            if ("ASC".equalsIgnoreCase(sortDirection)) {
                return o1.getDate().compareTo(o2.getDate());
            } else {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        // Paginate the combined list
        int start = Math.min(page * 10, employeeOrders.size());
        int end = Math.min((start + 10), employeeOrders.size());
        List<ProductionOrder> paginatedList = employeeOrders.subList(start, end);

        Page<ProductionOrder> combinedPage = new PageImpl<>(paginatedList, PageRequest.of(page, 10),
                employeeOrders.size());

        model.addAttribute("pageOrders", combinedPage);
        model.addAttribute("sortDirection", sortDirection);

        return "employee/production_staff/viewRequest";
    }

    @GetMapping("/viewRequestsforPR1")
    public String getAllRequestsForPR1(Model model, HttpSession session) {
        String employeeID = (String) session.getAttribute("employeeId");
        List<ProductionOrder> createdOrders = productionOrderService.getProductionOrderByStatus("Confirmed");
        List<ProductionOrder> requestedOrders = productionOrderService.getProductionOrderByStatus("Completed");

        List<ProductionOrder> allOrders = new ArrayList<>();
        allOrders.addAll(createdOrders);
        allOrders.addAll(requestedOrders);

        List<ProductionOrder> employeeOrders = allOrders.stream()
                .filter(porder -> employeeID.equalsIgnoreCase(porder.getProduction_Staff()))
                .collect(Collectors.toList());

        model.addAttribute("listRequests", employeeOrders);
        return "employee/production_staff/viewRequest";
    }

    @GetMapping("/viewInformationRequestForPR")
    public String getRequestsForPR(@RequestParam("productionOrderId") String productionOrderId, Model model)
            throws IOException {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        List<String> imagesByCustomerId = null;
        Map<String, List<String>> imagesByStaffId = new HashMap<>();
        Map<String, List<String>> imagesByPRId = new HashMap<>();

        Employee employeePR = employeeService.getEmployeeById(productionOrder.getProduction_Staff());
        Employee employeeDE = employeeService.getEmployeeById(productionOrder.getDesign_Staff());
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());
        try {
            imagesByCustomerId = imageService.getImgByCustomerID(productionOrder.getCustomer().getCustomer_Id(),
                    productionOrder.getProduction_Order_Id());
            imagesByStaffId = imageService.getImgOrderedByStaffId(employeeDE.getEmployee_Id());
            imagesByPRId = imageService.getImgOrderedByPRStaffId(employeePR.getEmployee_Id());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Product product = productService.getProductById(productionOrder.getProduct().getProduct_Id());

        ProductMaterial productMaterial = productMaterialService
                .getProductMaterialByProduct_id(product.getProduct_Id());
        Diamond diamond = diamondService.getDiamondByProductId(product.getProduct_Id());
        Material material = materialService.getMaterialById(productMaterial.getId().getMaterial_Id());

        String cateUrl = imageService
                .getImgByCateogryID(String.valueOf(product.getCategory().getCategory_Id()));
        String materialUrl = imageService
                .getImgByMaterialID(String.valueOf(productMaterial.getId().getMaterial_Id()));
        String diamondUrl = imageService.getImgByDiamondID(String.valueOf(diamond.getDia_Id()));

        model.addAttribute("productMaterial", productMaterial);
        model.addAttribute("material", material);
        model.addAttribute("diamond", diamond);
        model.addAttribute("cateUrl", cateUrl);
        model.addAttribute("materialUrl", materialUrl);
        model.addAttribute("diamondUrl", diamondUrl);

        model.addAttribute("imagesByStaffId", imagesByStaffId);
        model.addAttribute("imagesByPRId", imagesByPRId);
        model.addAttribute("customer", customer);
        model.addAttribute("imagesByCustomerId", imagesByCustomerId);
        model.addAttribute("imagesByStaffId", imagesByStaffId);
        model.addAttribute("listRequests", productionOrder);
        model.addAttribute("employee", employeePR);
        return "employee/production_staff/viewInforRequest";
    }

    @PostMapping("/uploadPhotoForPR")
    public String uploadPhotoForPR(
            @RequestParam("productionOrderId") String productionOrderId,
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        Employee employee = employeeService.getEmployeeById(productionOrder.getProduction_Staff());
        try {
            String url = imageService.uploadForProductionOrder(file, "Customer_Progress_Photo",
                    employee.getEmployee_Id(),
                    productionOrder.getProduction_Order_Id());
            System.out.println(url);
        } catch (Exception e) {
            return "redirect:/viewInformationRequestForPR?productionOrderId=" + productionOrderId + "&error";
        }
        return "redirect:/viewInformationRequestForPR?productionOrderId=" + productionOrderId + "&success";
    }

    @GetMapping("/viewAllPhotos1")
    public String viewAllPhotos1(
            HttpSession session,
            Model model) {

        Map<String, List<String>> imagesByStaffId = new HashMap<>();

        Employee employee = employeeService.getEmployeeById((String) session.getAttribute("employeeId"));
        List<ProductionOrder> productionOrders = productionOrderService.getProductionOrderByStatusAndDEId("Ordered",
                employee.getEmployee_Id());
        try {
            imagesByStaffId = imageService.getImgOrderedByPRStaffId(employee.getEmployee_Id());
            System.out.println(imagesByStaffId);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        model.addAttribute("imagesByStaffId", imagesByStaffId);
        model.addAttribute("listRequests", productionOrders);
        model.addAttribute("employee", employee);
        return "employee/production_staff/viewAllProgressPhoto";
    }

    @GetMapping("/viewAllPhotos2")
    public String viewAllPhotos2(
            @RequestParam(defaultValue = "0") int page,
            HttpSession session,
            Model model) {

        Employee employee = employeeService.getEmployeeById((String) session.getAttribute("employeeId"));
        List<ProductionOrder> productionOrders = productionOrderService.getProductionOrderByPREmployeeId(
                employee.getEmployee_Id());

        // Paginate the production orders list
        int pageSize = 2;
        int start = Math.min(page * pageSize, productionOrders.size());
        int end = Math.min(start + pageSize, productionOrders.size());

        Map<String, List<String>> imagesByStaffId = new HashMap<>();
        List<ProductionOrder> paginatedList = productionOrders.subList(start, end);
        try {
            imagesByStaffId = imageService.getImgOrderedByPRStaffId(employee.getEmployee_Id());
            System.out.println(imagesByStaffId);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Map<String, List<String>> paginatedImagesByStaffId = new HashMap<>();
        for (ProductionOrder order : paginatedList) {
            if (imagesByStaffId.containsKey(order.getProduction_Order_Id())) {
                paginatedImagesByStaffId.put(order.getProduction_Order_Id(),
                        imagesByStaffId.get(order.getProduction_Order_Id()));
            }
        }

        Page<ProductionOrder> productionOrdersPage = new PageImpl<>(paginatedList, PageRequest.of(page, pageSize),
                productionOrders.size());

        model.addAttribute("imagesByStaffId", imagesByStaffId);
        model.addAttribute("pageOrders", productionOrdersPage);
        model.addAttribute("employee", employee);
        return "employee/production_staff/viewAllProgressPhoto";
    }

    @GetMapping("/viewAllPhotos")
    public String viewAllPhotos(@RequestParam(defaultValue = "0") int page, HttpSession session, Model model) {
        String employeeId = (String) session.getAttribute("employeeId");
        Employee employee = employeeService.getEmployeeById(employeeId);
        List<ProductionOrder> productionOrders = productionOrderService
                .getProductionOrderByPREmployeeId(employee.getEmployee_Id());

        // Paginate the production orders list
        int pageSize = 2;
        int start = Math.min(page * pageSize, productionOrders.size());
        int end = Math.min(start + pageSize, productionOrders.size());

        List<ProductionOrder> paginatedList = productionOrders.subList(start, end);

        Map<String, List<String>> imagesByStaffId = new HashMap<>();
        try {
            imagesByStaffId = imageService.getImgOrderedByPRStaffId(employee.getEmployee_Id());
            System.out.println(imagesByStaffId);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Filter images by paginated production order list
        Map<String, List<String>> paginatedImagesByStaffId = new HashMap<>();
        for (ProductionOrder order : paginatedList) {
            if (imagesByStaffId.containsKey(order.getProduction_Order_Id())) {
                paginatedImagesByStaffId.put(order.getProduction_Order_Id(),
                        imagesByStaffId.get(order.getProduction_Order_Id()));
            }
        }

        Page<ProductionOrder> productionOrdersPage = new PageImpl<>(paginatedList, PageRequest.of(page, pageSize),
                productionOrders.size());

        model.addAttribute("imagesByStaffId", paginatedImagesByStaffId);
        model.addAttribute("pageOrders", productionOrdersPage);
        model.addAttribute("employee", employee);

        return "employee/production_staff/viewAllProgressPhoto";
    }

    @GetMapping("/viewAllDesign2")
    public String viewAllDesign2(
            @RequestParam(defaultValue = "0") int page,
            HttpSession session,
            Model model) {

        Employee employee = employeeService.getEmployeeById((String) session.getAttribute("employeeId"));
        List<ProductionOrder> productionOrders = productionOrderService.getProductionOrderByDEEmployeeId(
                employee.getEmployee_Id());

        // Paginate the production orders list
        int pageSize = 2;
        int start = Math.min(page * pageSize, productionOrders.size());
        int end = Math.min(start + pageSize, productionOrders.size());
        List<ProductionOrder> paginatedList = productionOrders.subList(start, end);

        Map<String, List<String>> imagesByStaffId = new HashMap<>();
        try {
            imagesByStaffId = imageService.getImgOrderedByStaffId(employee.getEmployee_Id());
            System.out.println(imagesByStaffId);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Map<String, List<String>> paginatedImagesByStaffId = new HashMap<>();
        for (ProductionOrder order : paginatedList) {
            if (imagesByStaffId.containsKey(order.getProduction_Order_Id())) {
                paginatedImagesByStaffId.put(order.getProduction_Order_Id(),
                        imagesByStaffId.get(order.getProduction_Order_Id()));
            }
        }

        Page<ProductionOrder> productionOrdersPage = new PageImpl<>(paginatedList, PageRequest.of(page, pageSize),
                productionOrders.size());

        model.addAttribute("pageOrders", productionOrdersPage);
        model.addAttribute("imagesByStaffId", imagesByStaffId);
        model.addAttribute("employee", employee);
        return "employee/design_staff/viewAllDesign";
    }

    @PostMapping("/updateStatusByPR")
    public String updateStatusByPR(
            @RequestParam("productionOrderId") String productionOrderId,
            @RequestParam(value = "status") String status,
            Model model,
            HttpSession session) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus(status);

        productionOrderService.saveProductionOrder(productionOrder);
        return "redirect:/viewInformationRequestForPR?productionOrderId=" + productionOrderId + "&update_success";
    }

    @PostMapping("/receiveProductByCustomer")
    public String receiveProductByCustomer(
            @RequestParam("productionOrderId") String productionOrderId,
            Model model,
            HttpSession session) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Delivered");
        productionOrder.getProduct().setStatus(true);

        productionOrderService.saveProductionOrder(productionOrder);
        return "redirect:/userHistoryOrders?customerId=" + productionOrder.getCustomer().getCustomer_Id()
                + "&update_delivered";
    }

    // Customer-ProductionOrder
    @GetMapping("/viewInformationPOForCustomer")
    public String getPOForCustomer(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());

        ProductMaterial pm = productMaterialService
                .getProductMaterialByProductId(productionOrder.getProduct().getProduct_Id());

        Material material = materialService.getMaterialById(pm.getId().getMaterial_Id());
        MaterialPriceList mpl = materialPriceListService.getMaterialPriceListById(material.getMaterial_Id());
        Diamond diamond = diamondService.getDiamondByProductId(productionOrder.getProduct().getProduct_Id());

        List<DiamondPriceList> dpls = diamondPriceListService.findPriceListByCriteria(diamond.getCarat_Weight(),
                diamond.getColor(), diamond.getClarity(), diamond.getCut(), diamond.getOrigin());

        DiamondPriceList dpl = new DiamondPriceList();
        for (DiamondPriceList d : dpls) {
            dpl = d;
        }

        model.addAttribute("diamondPriceList", dpl);
        model.addAttribute("diamond", diamond);
        model.addAttribute("materialPriceList", mpl);
        model.addAttribute("productMaterial", pm);
        model.addAttribute("material", material);
        model.addAttribute("customer", customer);
        model.addAttribute("user", customer.getUser());
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        return "customer/userQuote";
    }

    @GetMapping("/customerRejectQuote")
    public String getCustomerRejectQuote(@RequestParam("production_Order_Id") String productionOrderId,
            Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Quoted(CRJ)");
        productionOrderService.saveProductionOrder(productionOrder);
        return "redirect:/userQuotes";
    }

    @GetMapping("/customerAcceptQuote")
    public String getCustomerAcceptQuote(@RequestParam("production_Order_Id") String productionOrderId) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        productionOrder.setO_Diamond_Amount(productionOrder.getQ_Diamond_Amount());
        productionOrder.setO_Material_Amount(productionOrder.getQ_Material_Amount());
        productionOrder.setO_Production_Amount(productionOrder.getQ_Production_Amount());
        productionOrder.setO_Total_Amount(productionOrder.getQ_Total_Amount());
        productionOrder.setStatus("Ordered(WP)");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/userOrders";
    }

    @GetMapping("/viewInformationOrderForCustomer")
    public String getOrderForCustomer(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());

        ProductMaterial pm = productMaterialService
                .getProductMaterialByProductId(productionOrder.getProduct().getProduct_Id());

        Material material = materialService.getMaterialById(pm.getId().getMaterial_Id());
        MaterialPriceList mpl = materialPriceListService.getMaterialPriceListById(material.getMaterial_Id());
        Diamond diamond = diamondService.getDiamondByProductId(productionOrder.getProduct().getProduct_Id());

        List<DiamondPriceList> dpls = diamondPriceListService.findPriceListByCriteria(diamond.getCarat_Weight(),
                diamond.getColor(), diamond.getClarity(), diamond.getCut(), diamond.getOrigin());

        DiamondPriceList dpl = new DiamondPriceList();
        for (DiamondPriceList d : dpls) {
            dpl = d;
        }

        model.addAttribute("diamondPriceList", dpl);
        model.addAttribute("diamond", diamond);
        model.addAttribute("materialPriceList", mpl);
        model.addAttribute("productMaterial", pm);
        model.addAttribute("material", material);
        model.addAttribute("customer", customer);
        model.addAttribute("user", customer.getUser());
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        return "customer/userOrder";
    }

    @GetMapping("/customerPaymentOrder")
    public String getCustomerPaymentOrder(@RequestParam("production_Order_Id") String productionOrderId) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        productionOrder.setStatus("Ordered");

        productionOrderService.saveProductionOrder(productionOrder);

        return "customer/allUserOrder";
    }

}