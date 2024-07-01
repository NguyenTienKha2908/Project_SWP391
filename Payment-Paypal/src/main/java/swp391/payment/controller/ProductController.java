package swp391.payment.controller;


import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp391.payment.entity.Product;
import swp391.payment.repository.ProductRepository;
import swp391.payment.service.PayPalService;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PayPalService payPalService;

    @Value("${paypal.success.url}")
    private String successUrl;

    @Value("${paypal.cancel.url}")
    private String cancelUrl;
    

    @GetMapping("/")
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/product/{id}")
    public String viewProduct(@PathVariable("id") String id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "product-detail";
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam("id") String id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "checkout";
    }

    @PostMapping("/pay")
    public String pay(@RequestParam("id") String id, @RequestParam("method") String method, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return "redirect:/";
        }

        if (method.equals("paypal")) {
            try {
                Payment payment = payPalService.createPayment(
                        (double) product.getPrice(),
                        "USD",
                        "paypal",
                        "sale",
                        "Payment description",
                        cancelUrl,
                        successUrl
                );
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

    @GetMapping("/pay/cancel")
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping("/pay/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

}






