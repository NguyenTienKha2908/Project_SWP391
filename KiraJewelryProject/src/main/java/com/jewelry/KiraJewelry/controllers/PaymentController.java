package com.jewelry.KiraJewelry.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.service.ProductionOrderService;

@Controller
public class PaymentController {

    @Autowired
    private ProductionOrderService productionOrderService;

    @GetMapping("/bankTransferInfo")
    public String bankTransfer(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        double depositAmount = productionOrder.getO_Total_Amount() * 50 / 100;
        model.addAttribute("depositAmount", depositAmount);
        model.addAttribute("finalAmount", depositAmount);
        model.addAttribute("productionOrder", productionOrder);
        return "customer/payment/bankTransfer";
    }

    @PostMapping("/bankTransferHandle")
    public String bankTransferHandle(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Payment In Confirm");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/userCustomizedOrder?orderId=" + productionOrderId + "&await";
    }

    @PostMapping("/bankTransferHandleForDeposit")
    public String bankTransferHandleForDeposit(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Deposit In Confirm");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/userOrder?orderId=" + productionOrderId + "&await";
    }

    @PostMapping("/bankTransferHandleForLastPayment")
    public String bankTransferHandleForLastPayment(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Last Payment In Confirm");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/userOrder?orderId=" + productionOrderId + "&await";
    }

    @GetMapping("/CODInfo")
    public String CODInfo(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Delivering");

        productionOrderService.saveProductionOrder(productionOrder);

        model.addAttribute("productionOrder", productionOrder);
        return "redirect:/userCustomizedOrder?orderId=" + productionOrderId + "&CODsuccess";
    }

    @GetMapping("/CODInfoForLastPayment")
    public String CODInfoForLastPayment(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Delivering");

        productionOrderService.saveProductionOrder(productionOrder);

        model.addAttribute("productionOrder", productionOrder);
        return "redirect:/userOrder?orderId=" + productionOrderId + "&CODsuccess";
    }
}
