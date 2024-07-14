package com.jewelry.KiraJewelry.controllers;

import com.jewelry.KiraJewelry.service.PayPalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.service.ProductionOrderService;

@Controller
public class PaymentController {

    @Autowired
    private ProductionOrderService productionOrderService;

    @Autowired
    private PayPalService payPalService;

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
    public String bankTransferHandleForDeposit(@RequestParam("productionOrderId") String productionOrderId,
            Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Deposit In Confirm");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/userOrder?orderId=" + productionOrderId + "&await";
    }

    @PostMapping("/bankTransferHandleForCustomizeDeposit")
    public String bankTransferHandleForCustomizeDeposit(@RequestParam("productionOrderId") String productionOrderId,
            Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Deposit In Confirm For Customized Order");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/userOrder?orderId=" + productionOrderId + "&await";
    }

    @PostMapping("/bankTransferHandleForLastPayment")
    public String bankTransferHandleForLastPayment(@RequestParam("productionOrderId") String productionOrderId,
            Model model) {
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

    @GetMapping("/paypal")
    public String paypal(@RequestParam("productionOrderId") String productionOrderId, Model model,
            RedirectAttributes RedirectAttributes) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        String method = "paypal";
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String cancelUrl = baseUrl + "/paypal/cancel?productionOrderId=" + productionOrderId;
        String successUrl = baseUrl + "/paypal/success?productionOrderId=" + productionOrderId;
        if (productionOrder == null) {
            return "redirect:/userOrder?productionOrderId=" + productionOrderId;
        }
        if (method.equals("paypal")) {
            try {
                Payment payment = payPalService.createPayment(
                        (double) productionOrder.getO_Total_Amount() * 0.5,
                        "USD",
                        "paypal",
                        "sale",
                        "Payment description",
                        cancelUrl,
                        successUrl);
                for (Links links : payment.getLinks()) {
                    if (links.getRel().equals("approval_url")) {
                        return "redirect:" + links.getHref();
                    }
                }
            } catch (PayPalRESTException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/";
    }

    @GetMapping("/paypal/cancel")
    public String cancelPay(@RequestParam("productionOrderId") String productionOrderId) {
        return "redirect:/userOrder?orderId=" + productionOrderId +
        "&cancelPaypal";
    }

    @GetMapping("/paypal/success")
    public String successPay(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("productionOrderId") String productionOrderId,
            @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

                if (productionOrder.getStatus().equals("Customized")) {
                    productionOrder.setStatus("Confirmed");
                    productionOrderService.saveProductionOrder(productionOrder);
                } else if (productionOrder.getStatus().equals("Ordered(WP)")) {
                    productionOrder.setStatus("Ordered");
                    productionOrderService.saveProductionOrder(productionOrder);
                } else if (productionOrder.getStatus().equals("Completed")) {
                    productionOrder.setStatus("Delivering");
                    productionOrderService.saveProductionOrder(productionOrder);
                }
                return "redirect:/userOrder?orderId=" + productionOrderId;
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/userOrder?orderId=" + productionOrderId;
    }

}
