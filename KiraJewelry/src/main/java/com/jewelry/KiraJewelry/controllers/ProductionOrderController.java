package com.jewelry.KiraJewelry.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.Diamond;
import com.jewelry.KiraJewelry.models.Diamond_Price_List;
import com.jewelry.KiraJewelry.models.Employee;
import com.jewelry.KiraJewelry.models.Material;
import com.jewelry.KiraJewelry.models.MaterialPriceList;
import com.jewelry.KiraJewelry.models.Product;
import com.jewelry.KiraJewelry.models.ProductMaterial;
import com.jewelry.KiraJewelry.models.ProductMaterialId;
import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.models.User;
import com.jewelry.KiraJewelry.service.CustomerService;
import com.jewelry.KiraJewelry.service.DiamondPriceListService;
import com.jewelry.KiraJewelry.service.DiamondService;
import com.jewelry.KiraJewelry.service.EmployeeService;
import com.jewelry.KiraJewelry.service.MaterialPriceListService;
import com.jewelry.KiraJewelry.service.MaterialService;
import com.jewelry.KiraJewelry.service.ProductMaterialService;
import com.jewelry.KiraJewelry.service.ProductService;
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
        String employeeID = (String) session.getAttribute("employeeId");
        List<ProductionOrder> productionOrders = productionOrderService.getProductionOrderByStatusAndId("Request",
                employeeID);

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
    public String getIngredients(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Product product = productionOrder.getProduct();
        model.addAttribute("product", product);
        model.addAttribute("productionOrder", productionOrder);
        return "employee/sales_staff/findIngredientsPage";
    }

    @PostMapping("/searchMaterial")
    public String searchMaterial(@RequestParam("production_Order_Id") String productionOrderId,
            @RequestParam String materialName, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        List<Material> material = materialService.findByName(materialName);
        List<MaterialPriceList> listPrice = new ArrayList<>();
        for (Material m : material) {
            MaterialPriceList mpl = materialPriceListService.getMaterialPriceListByMaterialId(m.getMaterial_Id());
            listPrice.add(mpl);
        }
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        model.addAttribute("materialName", materialName);
        model.addAttribute("materialPriceList", listPrice);
        model.addAttribute("material", material);
        return "employee/sales_staff/findIngredientsPage";
    }

    @PostMapping("/saveMaterial")
    public String saveMaterial(@RequestParam("production_Order_Id") String productionOrderId,
            @RequestParam("material_Id") int materialId,
            @RequestParam("material_Name") String materialName,
            @RequestParam(value = "materialWeight", required = false) Double materialWeight,
            Model model) {
        String message = "";
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        int productId = productionOrder.getProduct().getProduct_Id();
        ProductMaterial productMaterials = productMaterialService.getProductMaterialByProductId(productId);
        if (productMaterials != null) {
            message = "This product has selected materials!";
        } else {
            ProductMaterial productMaterial = new ProductMaterial();
            ProductMaterialId productMaterialId = new ProductMaterialId();
            productMaterialId.setProduct_Id(productionOrder.getProduct().getProduct_Id());
            productMaterialId.setMaterial_Id(materialId);
            productMaterial.setId(productMaterialId);
            if (materialWeight != null) {
                productMaterial.setMaterial_Weight(materialWeight);
                MaterialPriceList mpl = materialPriceListService.getMaterialPriceListByMaterialId(materialId);
                if (mpl != null) {
                    double q_Price = materialWeight * mpl.getPrice();
                    productMaterial.setQ_Price(q_Price);
                    productMaterial.setO_Price(q_Price);
                }
                message = "Save material successfully!";
            } else {
                productMaterial.setMaterial_Weight(0);
                productMaterial.setQ_Price(0);
                productMaterial.setO_Price(0);
                message = "Save material but you didn't choose material before!";
            }

            productMaterialService.saveProductMaterial(productMaterial);
        }

        model.addAttribute("message", message);
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        return "employee/sales_staff/findIngredientsPage";
    }

    @PostMapping("/searchDiamond")
    public String searchDiamond(@RequestParam("production_Order_Id") String productionOrderId,
            @RequestParam(required = false) String diamondName,
            @RequestParam(required = false) Double caratWeight,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String clarity,
            @RequestParam(required = false) String cut,
            @RequestParam(required = false) String origin,
            Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        List<Diamond> diamond = diamondService.findByCriteria(diamondName, caratWeight, color, clarity, cut, origin);

        model.addAttribute("diamondName", diamondName);
        model.addAttribute("caratWeight", caratWeight);
        model.addAttribute("color", color);
        model.addAttribute("clarity", clarity);
        model.addAttribute("cut", cut);
        model.addAttribute("origin", origin);
        model.addAttribute("diamond", diamond);

        List<Diamond_Price_List> diamondPriceList = new ArrayList<>();
        for (Diamond d : diamond) {
            Diamond_Price_List dpl = diamondPriceListService.findPriceListByCriteria(d.getCarat_Weight(),
                    d.getColor(),
                    d.getClarity(),
                    d.getCut(),
                    d.getOrigin());
            if (dpl != null && d.isStatus()) {
                diamondPriceList.add(dpl);
            }
        }
        System.out.println("DPL : " + diamondPriceList);
        model.addAttribute("diamondPriceList", diamondPriceList);
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("product", productionOrder.getProduct());
        return "employee/sales_staff/findIngredientsPage";
    }

    @GetMapping("/viewQuotesforSS")
    public String getAllQuotes(Model model, HttpSession session) {
        String employeeName = (String) session.getAttribute("employeeId");
        List<ProductionOrder> productionOrders = productionOrderService.getProductionOrderByStatusAndId("Quote",
                employeeName);
        List<ProductionOrder> productionOrders2 = productionOrderService.getProductionOrderByStatusAndId("Quote(NA)",
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
        Product product = productionOrder.getProduct();
        ProductMaterial productMaterial = productMaterialService.getProductMaterialByProductId(product.getProduct_Id());
        Material material = materialService.getMaterialById(productMaterial.getId().getMaterial_Id());
        Diamond diamond = diamondService.getDiamondByProductId(product.getProduct_Id());
        Diamond_Price_List diamondPriceList = diamondPriceListService.getDiamondPriceList(diamond.getColor(),
                diamond.getClarity(), diamond.getCut(), diamond.getOrigin());

        model.addAttribute("productMaterial", productMaterial);
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("customer", customer);
        model.addAttribute("product", product);
        model.addAttribute("material", material);
        model.addAttribute("diamond", diamond);
        model.addAttribute("diamondPriceList", diamondPriceList);
        return "employee/sales_staff/viewInforQuote";
    }

    @GetMapping("/viewEditPage")
    public String viewEditPage(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        List<Material> materials = materialService.getAllMaterials();
        List<Diamond> diamonds = diamondService.getAllDiamonds();
        // List<MaterialPriceList> mpl = materialPriceListService.getAllPriceLists();
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("materials", materials);
        model.addAttribute("diamonds", diamonds);
        // model.addAttribute("materialPriceLists", mpl);
        return "employee/sales_staff/edit";
    }

    @PostMapping("/saveEditedProductionOrder")
    public String saveEditedProductionOrder(@RequestParam("productionOrderId") String productionOrderId,
            @RequestParam("customerId") String customerId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam("productSize") int productSize,
            @RequestParam("gender") String gender,
            Model model) {

        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Product product = productionOrder.getProduct();
        product.setSize(productSize);
        product.setGender(gender);

        productionOrder.setProduct_Size(productSize);
        productionOrder.setStatus("Quote(NA)");
        productService.saveProduct(product);
        productionOrderService.saveProductionOrder(productionOrder);
        model.addAttribute("listRequests",
                productionOrderService.getAllProductionOrders());

        return "redirect:/viewQuotesforSS";
    }

    @PostMapping("/saveEditedRequest")
    public String saveEditedRequest(@RequestParam("productionOrderId") String productionOrderId,
            @RequestParam("customerId") String customerId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam("material") int materialId,
            @RequestParam("materialWeight") double materialWeight,
            @RequestParam("diamond") int diamondId,
            @RequestParam("diamondWeight") double diamondWeight,
            @RequestParam("productSize") int productSize,
            @RequestParam("gender") String gender,
            Model model) {

        Material material = materialService.getMaterialById(materialId);
        MaterialPriceList mpl = materialPriceListService.getMaterialPriceListByMaterialId(materialId);
        Diamond diamond = diamondService.getDiamondById(diamondId);
        Diamond_Price_List dpl = diamondPriceListService.getDiamondPrice(diamond.getColor(), diamond.getClarity(),
                diamond.getCut(), diamond.getOrigin(), diamondWeight);

        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Product product = productionOrder.getProduct();
        product.setSize(productSize);
        product.setGender(gender);

        ProductMaterial productMaterial = new ProductMaterial();
        ProductMaterialId productMaterialId = new ProductMaterialId();

        productMaterialId.setMaterial_Id(materialId);
        productMaterialId.setProduct_Id(product.getProduct_Id());
        productMaterial.setId(productMaterialId);
        productMaterial.setMaterial_Weight(materialWeight);
        productMaterial.setQ_Price(productMaterial.getMaterial_Weight() * mpl.getPrice());

        diamond.setProduct(product);
        diamond.setStatus(false);
        diamond.setCarat_Weight(diamondWeight);

        productionOrder.setProduct_Size(productSize);
        productionOrder.setQ_Material_Amount(productMaterial.getQ_Price());
        productionOrder.setQ_Diamond_Amount(dpl.getPrice());

        // productionOrder.setQ_Diamond_Amount(diamondWeight*dia);

        productService.saveProduct(product);
        diamondService.saveDiamond(diamond);
        productMaterialService.saveProductMaterial(productMaterial);
        productionOrderService.saveProductionOrder(productionOrder);
        model.addAttribute("listRequests",
                productionOrderService.getAllProductionOrders());

        return "redirect:/viewRequestsforSS";
    }

    @GetMapping("/prepareQuotePage")
    public String prepareQuotePage(@RequestParam("productionOrderId") String productionOrderId, Model model) {

        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer_Id());
        Product product = productionOrder.getProduct();
        ProductMaterial productMaterial = productMaterialService.getProductMaterialByProductId(product.getProduct_Id());
        Material material = materialService.getMaterialById(productMaterial.getId().getMaterial_Id());
        Diamond diamond = diamondService.getDiamondByProductId(product.getProduct_Id());
        Diamond_Price_List diamondPriceList = diamondPriceListService.getDiamondPriceList(diamond.getColor(),
                diamond.getClarity(), diamond.getCut(), diamond.getOrigin());

        model.addAttribute("productMaterial", productMaterial);
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("customer", customer);
        model.addAttribute("product", product);
        model.addAttribute("material", material);
        model.addAttribute("diamond", diamond);
        model.addAttribute("diamondPriceList", diamondPriceList);
        return "employee/sales_staff/prepareQuotePage";
    }

    @GetMapping("/viewMaterialAndGem")
    public String getViewAssets(Model model) {
        List<Material> material = materialService.getAllMaterials();
        model.addAttribute("material", material);
        return "employee/sales_staff/materialAndGem";
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