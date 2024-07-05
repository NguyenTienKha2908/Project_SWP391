package com.jewelry.KiraJewelry.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.Diamond;
import com.jewelry.KiraJewelry.models.DiamondPriceList;
import com.jewelry.KiraJewelry.models.Employee;
import com.jewelry.KiraJewelry.models.Material;
import com.jewelry.KiraJewelry.models.MaterialPriceList;
import com.jewelry.KiraJewelry.models.Product;
import com.jewelry.KiraJewelry.models.ProductMaterial;
import com.jewelry.KiraJewelry.models.ProductMaterialId;
// import com.jewelry.KiraJewelry.models.MaterialPriceList;
import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.models.User;
import com.jewelry.KiraJewelry.service.CustomerService;
import com.jewelry.KiraJewelry.service.EmployeeService;
import com.jewelry.KiraJewelry.service.ImageService;
import com.jewelry.KiraJewelry.service.MaterialPriceListService;
// import com.jewelry.KiraJewelry.service.MaterialPriceListService;
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

    @GetMapping("/viewRequestsforSS")
    public String getAllRequests(Model model, HttpSession session) {
        String employeeID = (String) session.getAttribute("employeeId");
        List<ProductionOrder> productionOrders = productionOrderService.getProductionOrderByStatusAndName("Requested",
                employeeID);

        model.addAttribute("listRequests", productionOrders);
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
    public String getIngredients(@RequestParam("productionOrderId") String productionOrderId, Model model,
            HttpSession session) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Product product = productionOrder.getProduct();
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

        return "employee/sales_staff/findIngredientsPage";
    }

    @GetMapping("/searchMaterial")
    public String searchMaterial(@RequestParam("production_Order_Id") String productionOrderId,
            @RequestParam String materialName, Model model) {

        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        List<Material> material = materialService.findByName(materialName);
        List<MaterialPriceList> listPrice = new ArrayList<>();
        for (Material m : material) {
            MaterialPriceList mpl = materialPriceListService.getMaterialPriceListByMaterialId(m.getMaterial_Id());
            listPrice.add(mpl);
        }
        String message = "Search for material name : " + materialName;
        model.addAttribute("message", message);
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        model.addAttribute("productionOrderId", productionOrderId);
        model.addAttribute("materialName", materialName);
        model.addAttribute("materialPriceList", listPrice);
        model.addAttribute("material", material);
        return "employee/sales_staff/findIngredientsPage";
    }

    @GetMapping("/saveMaterial")
    public String saveMaterial(
            @RequestParam("production_Order_Id") String productionOrderId,
            @RequestParam("material_Id") int materialId,
            Model model) {

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
            MaterialPriceList mpl = materialPriceListService.getMaterialPriceListByMaterialId(materialId);
            message = "You just choose material :" + materialId + ", Material Code : " + material.getMaterial_Code()
                    + ", Material Name : " + material.getMaterial_Name() + ", Price 1 units - Day effective : "
                    + mpl.getPrice() + " - " + mpl.getEff_Date();

            model.addAttribute("productionOrder", productionOrder);
            model.addAttribute("product", productionOrder.getProduct());
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred while saving the material: " + e.getMessage();
        }

        model.addAttribute("message", message);

        return "employee/sales_staff/findIngredientsPage";
    }

    @GetMapping("/saveMaterialWeight")
    public String saveMaterialWeight(@RequestParam("production_Order_Id") String productionOrderId,
            @RequestParam(value = "materialWeight", required = false) Double materialWeight, Model model) {
        String message = "";
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        int productId = productionOrder.getProduct().getProduct_Id();
        ProductMaterial productMaterial = productMaterialService.getProductMaterialByProductId(productId);
        if (materialWeight == null) {
            message = "Material weight not found";
        } else {
            productMaterial.setMaterial_Weight(materialWeight);

            int materialId = productMaterial.getId().getMaterial_Id();
            MaterialPriceList mpl = materialPriceListService.getMaterialPriceListByMaterialId(materialId);
            double materialPrice = mpl.getPrice();
            double productionOrderMaterialPrice = materialPrice * materialWeight;
            productionOrder.setQ_Material_Amount(productionOrderMaterialPrice);
            productionOrderService.saveProductionOrder(productionOrder);

            productMaterial.setQ_Price(productionOrderMaterialPrice);
            productMaterialService.saveProductMaterial(productMaterial);
            message = "Save material weight successfully";
        }
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        model.addAttribute("message", message);
        return "employee/sales_staff/findIngredientsPage";
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
                model.addAttribute("diamond", diamond);

                List<DiamondPriceList> diamondPriceList = new ArrayList<>();
                for (Diamond d : diamond) {
                    List<DiamondPriceList> dplList = diamondPriceListService.findPriceListByCriteria(
                            d.getCarat_Weight(),
                            d.getColor(),
                            d.getClarity(),
                            d.getCut(),
                            d.getOrigin());
                    for (DiamondPriceList dpl : dplList) {
                        if (dpl != null && d.isStatus()) {
                            diamondPriceList.add(dpl);
                        }
                    }
                }
                model.addAttribute("diamondPriceList", diamondPriceList);

            }
        }
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        return "employee/sales_staff/findIngredientsPage";
    }

    @GetMapping("/viewInformationQuoteForSS")
    public String getQuotesForSS(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());
        User user = userService.getUsersByUserId(customer.getUser().getUser_Id());
        model.addAttribute("customer", customer);
        model.addAttribute("user", user);
        model.addAttribute("listQuotes", productionOrder);
        return "employee/sales_staff/viewInforQuote";
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

    @GetMapping("/viewRequestsforManager")
    public String getAllRequestsForManager(Model model, HttpSession session) {
        List<ProductionOrder> requestProductionOrders = productionOrderService.getProductionOrderByStatus("Request");
        List<ProductionOrder> createdProductionOrders = productionOrderService.getProductionOrderByStatus("Created");
        List<ProductionOrder> allProductionOrders = new ArrayList<>(requestProductionOrders);
        allProductionOrders.addAll(createdProductionOrders);
        model.addAttribute("listRequests", allProductionOrders);
        return "employee/manager/viewRequest";
    }

    @GetMapping("/viewQuotesforManager")
    public String getAllQuotesForManager(Model model, HttpSession session) {
        List<ProductionOrder> listQuotes = productionOrderService.getProductionOrderByStatus("Quote");
        List<ProductionOrder> listQuotes2 = productionOrderService.getProductionOrderByStatus("Quote(NA)");
        List<ProductionOrder> lists = new ArrayList<>();
        lists.addAll(listQuotes);
        lists.addAll(listQuotes2);
        model.addAttribute("listQuotes", lists);
        return "employee/manager/viewQuote";
    }

    // @GetMapping("/viewQuotesforSS")
    // public String getAllQuotes(Model model, HttpSession session) {
    // String employeeName = (String) session.getAttribute("employeeName");
    // List<ProductionOrder> productionOrders =
    // productionOrderService.getProductionOrderByStatusAndName("Quote",
    // employeeName);
    // List<ProductionOrder> productionOrders2 =
    // productionOrderService.getProductionOrderByStatusAndName("Quote(NA)",
    // employeeName);
    // List<ProductionOrder> listRequests = new ArrayList<>();
    // listRequests.addAll(productionOrders);
    // listRequests.addAll(productionOrders2);
    // model.addAttribute("listRequests", listRequests);
    // return "employee/sales_staff/viewQuote";
    // }

    @Autowired
    ImageService imageService;

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
        System.out.println(imagesByCustomerId);
        model.addAttribute("listRequests", productionOrder);
        model.addAttribute("employees", employees);
        return "employee/manager/viewInforRequest";
    }

    @GetMapping("/viewInformationQuoteForManager")
    public String getQuotesForManager(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());
        User user = userService.getUsersByUserId(customer.getUser().getUser_Id());
        model.addAttribute("customer", customer);
        model.addAttribute("user", user);
        model.addAttribute("listQuotes", productionOrder);
        return "employee/manager/viewInforQuote";
    }

    @GetMapping("/confirmDeleteProductionOrder")
    public String confirmDeleteProductionOrder(@RequestParam("productionOrderId") String productionOrderId,
            Model model) {
        model.addAttribute("productionOrderId", productionOrderId);
        return "employee/manager/delete";
    }

    @PostMapping("/deleteProductionOrder")
    public String deleteProductionOrder(@RequestParam("productionOrderId") String productionOrderId) {
        productionOrderService.deleteProductionOrderById(productionOrderId);

        return "redirect:/viewRequestsforManager";
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

    // @PostMapping("/saveEditedProductionOrder")
    // public String saveEditedProductionOrder(@RequestParam("productionOrderId")
    // String productionOrderId,
    // @RequestParam("materialId") int materialId, @RequestParam("materialName")
    // String materialName,
    // @RequestParam("materialWeight") double materialWeight,
    // @RequestParam("materialColor") String materialColor,
    // @RequestParam("materialAmount") double materialAmount,
    // @RequestParam("gemId") int gemId, @RequestParam("gemName") String gemName,
    // @RequestParam("gemWeight") double gemWeight,
    // @RequestParam("gemColor") String gemColor,
    // @RequestParam("diamondAmount") double diamondAmount,
    // @RequestParam("sideMaterialCost") double side_material_cost,
    // @RequestParam("sideGemCost") double side_gem_cost,
    // @RequestParam("productionAmount") double productionAmount,
    // @RequestParam("totalAmount") double total_amount,
    // Model model) {

    // ProductionOrder productionOrder =
    // productionOrderService.getProductionOrderById(productionOrderId);

    // productionOrder.setMaterial_Id(materialId);
    // productionOrder.setMaterial_Name(materialName);
    // productionOrder.setMaterial_Weight(materialWeight);
    // productionOrder.setMaterial_Color(materialColor);
    // productionOrder.setMaterial_Amount(materialAmount);
    // productionOrder.setGem_Id(gemId);
    // productionOrder.setGem_Name(gemName);
    // productionOrder.setGem_Color(gemColor);
    // productionOrder.setGem_Weight(gemWeight);
    // productionOrder.setDiamond_Amount(diamondAmount);
    // productionOrder.setSide_Material_Cost(side_material_cost);
    // productionOrder.setSide_Gem_Cost(side_gem_cost);
    // productionOrder.setProduction_Amount(productionAmount);
    // productionOrder.setTotal_Amount(total_amount);
    // productionOrder.setStatus("Quote(NA)");
    // productionOrderService.saveProductionOrder(productionOrder);
    // model.addAttribute("listRequests",
    // productionOrderService.getAllProductionOrders());

    // return "redirect:/viewQuotesforSS";
    // }

    // @PostMapping("/saveEditedRequest")
    // public String saveEditedRequest(@RequestParam("productionOrderId") String
    // productionOrderId,
    // @RequestParam("materialName") String materialName,
    // @RequestParam("materialColor") String materialColor,
    // @RequestParam("materialWeight") double materialWeight,
    // @RequestParam("gemName") String gemName,
    // @RequestParam("gemColor") String gemColor,
    // @RequestParam("gemWeight") double gemWeight,
    // @RequestParam("productSize") int productSize,
    // Model model) {

    // ProductionOrder productionOrder =
    // productionOrderService.getProductionOrderById(productionOrderId);

    // productionOrder.setMaterial_Name(materialName);
    // productionOrder.setMaterial_Color(materialColor);
    // productionOrder.setMaterial_Weight(materialWeight);
    // productionOrder.setGem_Name(gemName);
    // productionOrder.setGem_Color(gemColor);
    // productionOrder.setGem_Weight(gemWeight);
    // productionOrder.setProduct_Size(productSize);

    // productionOrderService.saveProductionOrder(productionOrder);
    // model.addAttribute("listRequests",
    // productionOrderService.getAllProductionOrders());

    // return "redirect:/viewRequestsforSS";
    // }

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

    @PostMapping("/acceptQuote")
    public String acceptQuote(@RequestParam("productionOrderId") String productionOrderId) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        productionOrder.setStatus("Quoted");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/viewOrdersforManager";
    }

    @GetMapping("/viewOrdersforManager")
    public String getAllOrdersForManager(Model model, HttpSession session) {
        List<ProductionOrder> listOrders = productionOrderService.getProductionOrderByStatus("Order");
        List<ProductionOrder> listOrder2 = productionOrderService.getProductionOrderByStatus("Order(NP)");
        List<ProductionOrder> lists = new ArrayList<>();
        lists.addAll(listOrders);
        lists.addAll(listOrder2);
        model.addAttribute("listOrders", lists);
        return "employee/manager/viewOrder";
    }

    @GetMapping("/viewInformationOrderForManager")
    public String getOrdersForManager(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());
        User user = userService.getUsersByUserId(customer.getUser().getUser_Id());
        model.addAttribute("customer", customer);
        model.addAttribute("user", user);
        model.addAttribute("listOrders", productionOrder);
        return "employee/manager/viewInforOrder";
    }

    @GetMapping("/viewProductionOrder")
    public String viewProductionOrder(HttpSession session, Model model) {
        String customerId = (String) session.getAttribute("customerId");
        if (customerId != null) {
            List<ProductionOrder> orders = productionOrderService.getProductionOrderByCustomerId(customerId);
            model.addAttribute("listPO", orders);
        }
        return "customer/viewProductionOrder";
    }

    @GetMapping("/viewInformationPOForCustomer")
    public String getPOForCustomer(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer().getCustomer_Id());
        User user = userService.getUsersByUserId(customer.getUser().getUser_Id());
        model.addAttribute("customer", customer);
        model.addAttribute("user", user);
        model.addAttribute("productionOrder", productionOrder);
        return "customer/viewInforProductionOrder";
    }

    @PostMapping("/payment")
    public String updateProductionOrderStatus(@RequestParam("productionOrderId") String productionOrderId) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        productionOrder.setStatus("Order");
        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/viewProductionOrder";
    }

    @GetMapping("/viewRequestsforDE")
    public String getAllRequestsForDE(Model model, HttpSession session) {
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
        // List<ProductionOrder> productionOrders =
        // productionOrderService.getProductionOrderByStatusAndDEId("Ordered",
        // employeeID);

        // model.addAttribute("listRequests", productionOrders);
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

    @GetMapping("/viewAllDesign")
    public String viewAllDesign(
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

    @PostMapping("/handleUserReject")
    public String handleUserReject(@RequestParam("productionOrderId") String productionOrderId, Model model,
            HttpSession session) {
        String employeeID = (String) session.getAttribute("employeeId");
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Ordered(NP)");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/userOrder?orderId=" + productionOrderId + "&reject";
    }

    @GetMapping("/handleUserApprove")
    public String handleUserapprove(@RequestParam("productionOrderId") String productionOrderId, Model model,
            HttpSession session) {
        String employeeID = (String) session.getAttribute("employeeId");
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Confirmed");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/userOrder?orderId=" + productionOrderId + "&approved";
    }

    @GetMapping("/viewRequestsforPR")
    public String getAllRequestsForPR(Model model, HttpSession session) {
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

    @GetMapping("/viewAllPhotos")
    public String viewAllPhotos(
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

    @PostMapping("/updateStatusByPR")
    public String updateStatusByPR(
            @RequestParam("productionOrderId") String productionOrderId,
            @RequestParam(value = "status") String status,
            Model model,
            HttpSession session) {
        String employeeID = (String) session.getAttribute("employeeId");
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus(status);

        productionOrderService.saveProductionOrder(productionOrder);
        String message = "Update Status Successfully";
        return "redirect:/viewInformationRequestForPR?productionOrderId=" + productionOrderId + "&update_success";
    }
}