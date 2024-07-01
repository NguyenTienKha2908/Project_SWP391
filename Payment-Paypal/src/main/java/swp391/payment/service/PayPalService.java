package swp391.payment.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PayPalService {

    @Autowired
    private APIContext apiContext;

    public Payment createPayment(Double total, String currency, String method, String intent,
                                 String description, String cancelUrl, String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecution);
    }


//    @Autowired
//    private APIContext apiContext;
//
//    public Payment createPayment(
//            Double total,
//            String currency,
//            String method,
//            String intent,
//            String description,
//            String cancelUrl,
//            String successUrl) throws PayPalRESTException {
//
//        // Tạo các mục
//        Item item = new Item();
//        item.setName("Item Name")
//                .setQuantity("1")
//                .setCurrency(currency)
//                .setPrice(total.toString());
//
//        ItemList itemList = new ItemList();
//        itemList.setItems(Collections.singletonList(item));
//
//        // Tạo chi tiết giao dịch
//        Details details = new Details();
//        details.setSubtotal(total.toString());
//
//        // Tạo số tiền giao dịch
//        Amount amount = new Amount();
//        amount.setCurrency(currency);
//        amount.setTotal(total.toString());
//        amount.setDetails(details);
//
//        // Tạo giao dịch
//        Transaction transaction = new Transaction();
//        transaction.setAmount(amount);
//        transaction.setDescription(description);
//        transaction.setItemList(itemList);
//
//        // Tạo người thanh toán
//        Payer payer = new Payer();
//        payer.setPaymentMethod(method.toString());
//
//        // Tạo thanh toán
//        Payment payment = new Payment();
//        payment.setIntent(intent.toString());
//        payment.setPayer(payer);
//        payment.setTransactions(Collections.singletonList(transaction));
//
//        // Đặt URL chuyển hướng
//        RedirectUrls redirectUrls = new RedirectUrls();
//        redirectUrls.setCancelUrl(cancelUrl);
//        redirectUrls.setReturnUrl(successUrl);
//        payment.setRedirectUrls(redirectUrls);
//
//        // Thực hiện thanh toán
//        APIContext apiContext = new APIContext("<CLIENT-ID>", "<CLIENT-SECRET>", "sandbox");
//        return payment.create(apiContext);
//    }
//    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
//        Payment payment = new Payment();
//        payment.setId(paymentId);
//        PaymentExecution paymentExecution = new PaymentExecution();
//        paymentExecution.setPayerId(payerId);
//        return payment.execute(apiContext, paymentExecution);
//    }
}