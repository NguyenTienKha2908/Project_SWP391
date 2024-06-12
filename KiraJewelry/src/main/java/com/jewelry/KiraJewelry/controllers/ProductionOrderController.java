package com.jewelry.KiraJewelry.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.Employee;
import com.jewelry.KiraJewelry.models.Material;
// import com.jewelry.KiraJewelry.models.MaterialPriceList;
import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.models.User;
import com.jewelry.KiraJewelry.service.CustomerService;
import com.jewelry.KiraJewelry.service.EmployeeService;
// import com.jewelry.KiraJewelry.service.MaterialPriceListService;
import com.jewelry.KiraJewelry.service.MaterialService;
import com.jewelry.KiraJewelry.service.ProductionOrderService;
import com.jewelry.KiraJewelry.service.UserService;

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

    // @Autowireda
    // private MaterialPriceListService materialPriceListService;

    @GetMapping("/viewInformationRequestForSS")
    public String getRequestsForSS(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        model.addAttribute("listRequests", productionOrder);
        return "employee/sales_staff/viewInforRequest";
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

    // @PostMapping("/saveProductionOrder")
    // public String saveProductionOrder(@RequestParam("productionOrderId") String productionOrderId,
    //         @RequestParam(value = "staff", required = false) String employeeId,
    //         Model model) {
    //     ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

    //     if (employeeId != null && !employeeId.isEmpty()) {
    //         Employee employee = employeeService.getEmployeeById(employeeId);
    //         productionOrder.setSales_Staff_Name(employee.getFull_Name());
    //         productionOrder.setStatus("Request");
    //     }

    //     productionOrderService.saveProductionOrder(productionOrder);
    //     return "redirect:/viewRequestsforManager";
    // }

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

    // @GetMapping("/viewRequestsforSS")
    // public String getAllRequests(Model model, HttpSession session) {
    //     String employeeName = (String) session.getAttribute("employeeName");
    //     List<ProductionOrder> productionOrders = productionOrderService.getProductionOrderByStatusAndName("Request",
    //             employeeName);

    //     model.addAttribute("listRequests", productionOrders);
    //     return "employee/sales_staff/viewRequest";
    // }

    // @GetMapping("/viewQuotesforSS")
    // public String getAllQuotes(Model model, HttpSession session) {
    //     String employeeName = (String) session.getAttribute("employeeName");
    //     List<ProductionOrder> productionOrders = productionOrderService.getProductionOrderByStatusAndName("Quote",
    //             employeeName);
    //     List<ProductionOrder> productionOrders2 = productionOrderService.getProductionOrderByStatusAndName("Quote(NA)",
    //             employeeName);
    //     List<ProductionOrder> listRequests = new ArrayList<>();
    //     listRequests.addAll(productionOrders);
    //     listRequests.addAll(productionOrders2);
    //     model.addAttribute("listRequests", listRequests);
    //     return "employee/sales_staff/viewQuote";
    // }

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
    // public String saveEditedProductionOrder(@RequestParam("productionOrderId") String productionOrderId,
    //         @RequestParam("materialId") int materialId, @RequestParam("materialName") String materialName,
    //         @RequestParam("materialWeight") double materialWeight,
    //         @RequestParam("materialColor") String materialColor,
    //         @RequestParam("materialAmount") double materialAmount,
    //         @RequestParam("gemId") int gemId, @RequestParam("gemName") String gemName,
    //         @RequestParam("gemWeight") double gemWeight,
    //         @RequestParam("gemColor") String gemColor,
    //         @RequestParam("diamondAmount") double diamondAmount,
    //         @RequestParam("sideMaterialCost") double side_material_cost,
    //         @RequestParam("sideGemCost") double side_gem_cost,
    //         @RequestParam("productionAmount") double productionAmount,
    //         @RequestParam("totalAmount") double total_amount,
    //         Model model) {

    //     ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

    //     productionOrder.setMaterial_Id(materialId);
    //     productionOrder.setMaterial_Name(materialName);
    //     productionOrder.setMaterial_Weight(materialWeight);
    //     productionOrder.setMaterial_Color(materialColor);
    //     productionOrder.setMaterial_Amount(materialAmount);
    //     productionOrder.setGem_Id(gemId);
    //     productionOrder.setGem_Name(gemName);
    //     productionOrder.setGem_Color(gemColor);
    //     productionOrder.setGem_Weight(gemWeight);
    //     productionOrder.setDiamond_Amount(diamondAmount);
    //     productionOrder.setSide_Material_Cost(side_material_cost);
    //     productionOrder.setSide_Gem_Cost(side_gem_cost);
    //     productionOrder.setProduction_Amount(productionAmount);
    //     productionOrder.setTotal_Amount(total_amount);
    //     productionOrder.setStatus("Quote(NA)");
    //     productionOrderService.saveProductionOrder(productionOrder);
    //     model.addAttribute("listRequests", productionOrderService.getAllProductionOrders());

    //     return "redirect:/viewQuotesforSS";
    // }

    // @PostMapping("/saveEditedRequest")
    // public String saveEditedRequest(@RequestParam("productionOrderId") String productionOrderId,
    //         @RequestParam("materialName") String materialName,
    //         @RequestParam("materialColor") String materialColor,
    //         @RequestParam("materialWeight") double materialWeight,
    //         @RequestParam("gemName") String gemName,
    //         @RequestParam("gemColor") String gemColor,
    //         @RequestParam("gemWeight") double gemWeight,
    //         @RequestParam("productSize") int productSize,
    //         Model model) {

    //     ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

    //     productionOrder.setMaterial_Name(materialName);
    //     productionOrder.setMaterial_Color(materialColor);
    //     productionOrder.setMaterial_Weight(materialWeight);
    //     productionOrder.setGem_Name(gemName);
    //     productionOrder.setGem_Color(gemColor);
    //     productionOrder.setGem_Weight(gemWeight);
    //     productionOrder.setProduct_Size(productSize);

    //     productionOrderService.saveProductionOrder(productionOrder);
    //     model.addAttribute("listRequests", productionOrderService.getAllProductionOrders());

    //     return "redirect:/viewRequestsforSS";
    // }

    @GetMapping("/prepareQuotePage")
    public String prepareQuotePage(@RequestParam("productionOrderId") String productionOrderId, Model model) {

        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer_Id());
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
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer_Id());
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