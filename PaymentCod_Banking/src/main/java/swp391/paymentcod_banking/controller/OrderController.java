package swp391.paymentcod_banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swp391.paymentcod_banking.entity.Order;
import swp391.paymentcod_banking.service.OrderService;

import java.util.List;


@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/{productId}/cod")
    public String createOrderCOD(@PathVariable String productId) {
        Order order = new Order();
        order.setPaymentStatus("COD");
        order.setProductId(productId);
        order.setShippingStatus("Dang Van Chuyen");
        orderService.saveOrder(order);
        return "redirect:/order-success";
    }

    @PostMapping("/order/{productId}/transfer")
    public String createOrderTransfer(@PathVariable String productId) {
        Order order = new Order();
        order.setPaymentStatus("Waiting");
        order.setProductId(productId);
        order.setShippingStatus("Checking");
        orderService.saveOrder(order);
        return "redirect:/bank-info";
    }

    @GetMapping("/bank-info")
    public String getBankInfo() {
        return "bank-info";
    }

    @GetMapping("/order-success")
    public String getOrderSuccess() {
        return "order-success";
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order-list";
    }
}


