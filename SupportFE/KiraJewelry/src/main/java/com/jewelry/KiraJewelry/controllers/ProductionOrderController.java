package com.jewelry.KiraJewelry.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.jewelry.KiraJewelry.service.MaterialPriceListService;
// import com.jewelry.KiraJewelry.service.MaterialPriceListService;
import java.util.Set;
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
    private DiamondService diamondService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMaterialService productMaterialService;

    @Autowired
    private MaterialPriceListService materialPriceListService;

    @Autowired
    private DiamondPriceListService diamondPriceListService;

    // Sales Staff
    @GetMapping("/viewRequestsForSS")
    public String getAllRequests(Model model, HttpSession session) {
        String employeeName = (String) session.getAttribute("employeeName");
        List<ProductionOrder> productionOrders = productionOrderService.getProductionOrderByStatusAndName("Request",
                employeeName);

        model.addAttribute("listRequests", productionOrders);
        return "employee/sales_staff/viewRequest";
    }

    @GetMapping("/viewInformationRequestForSS")
    public String getRequestsForSS(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        model.addAttribute("listRequests", productionOrder);
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
            MaterialPriceList mpl = materialPriceListService.getMaterialPriceListById(m.getMaterial_Id());
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
            MaterialPriceList mpl = materialPriceListService.getMaterialPriceListById(materialId);
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
            @RequestParam(value = "materialWeight", required = false) Float materialWeight, Model model) {
        String message = "";
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        int productId = productionOrder.getProduct().getProduct_Id();
        ProductMaterial productMaterial = productMaterialService.getProductMaterialByProductId(productId);
        if (materialWeight == null) {
            message = "Material weight not found";
        } else {
            productMaterial.setMaterial_Weight(materialWeight);
            productMaterialService.saveProductMaterial(productMaterial);

            int materialId = productMaterial.getId().getMaterial_Id();
            MaterialPriceList mpl = materialPriceListService.getMaterialPriceListById(materialId);
            double materialPrice = mpl.getPrice();
            double productionOrderMaterialPrice = materialPrice * materialWeight;
            productionOrder.setQ_Material_Amount(productionOrderMaterialPrice);
            productionOrderService.saveProductionOrder(productionOrder);
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
                        if (dpl != null && d.getStatus()) {
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

    @GetMapping("/viewQuotesforSS")
    public String getAllQuotes(Model model, HttpSession session) {
        String employeeName = (String) session.getAttribute("employeeId");
        List<ProductionOrder> productionOrders = productionOrderService.getProductionOrderByStatusAndName("Quote",
                employeeName);
        List<ProductionOrder> productionOrders2 = productionOrderService.getProductionOrderByStatusAndName("Quote(NA)",
                employeeName);
        List<ProductionOrder> listQuotes = new ArrayList<>();
        listQuotes.addAll(productionOrders);
        listQuotes.addAll(productionOrders2);
        model.addAttribute("listQuotes", listQuotes);
        return "employee/sales_staff/viewQuote";
    }

    @GetMapping("/viewInformationQuoteForSS")
    public String getQuotesForSS(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer_Id());
        User user = userService.getUsersByUserId(customer.getUser().getUser_Id());
        model.addAttribute("customer", customer);
        model.addAttribute("user", user);
        model.addAttribute("listQuotes", productionOrder);
        return "employee/sales_staff/viewInforQuote";
    }

    // MANAGER
    @PostMapping("/saveProductionOrder")
    public String saveProductionOrder(@RequestParam("productionOrderId") String productionOrderId,
            @RequestParam(value = "staff", required = false) String employeeId,
            Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        if (employeeId != null && !employeeId.isEmpty()) {
            Employee employee = employeeService.getEmployeeById(employeeId);
            productionOrder.setSales_Staff_Id(employeeId);
            productionOrder.setStatus("Request");
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
        model.addAttribute("listRequests", productionOrder);
        model.addAttribute("employees", employees);
        return "employee/manager/viewInforRequest";
    }

    @GetMapping("/viewInformationQuoteForManager")
    public String getQuotesForManager(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer_Id());
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
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Product product = productionOrder.getProduct();
        productionOrderService.deleteProductionOrderById(productionOrderId);
        // Diamond diamond =
        // diamondService.getDiamondByProductId(product.getProduct_Id());
        // diamond.setStatus(false);
        productService.deleteProductById(product.getProduct_Id());

        return "redirect:/viewRequestsforManager";
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
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer_Id());
        User user = userService.getUsersByUserId(customer.getUser().getUser_Id());
        model.addAttribute("customer", customer);
        model.addAttribute("user", user);
        model.addAttribute("listOrders", productionOrder);
        return "employee/manager/viewInforOrder";
    }

    // CUSTOMER
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
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer_Id());
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
}