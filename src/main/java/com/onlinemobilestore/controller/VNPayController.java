package com.onlinemobilestore.controller;

import com.onlinemobilestore.configuration.VNPayConfig;
import com.onlinemobilestore.entity.Order;
import com.onlinemobilestore.entity.Payment;
import com.onlinemobilestore.repository.PaymentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@Controller
@RequestMapping("/payment")
public class VNPayController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    @Autowired
    PaymentRepository paymentRepository;

    @RequestMapping("/vnpay_return")
    public String returnRenderView(Model model) throws IOException {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
            String fieldValue = URLEncoder.encode(request.getParameter(fieldName),
                    StandardCharsets.US_ASCII.toString());
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = VNPayConfig.hashAllFields(fields);
        model.addAttribute("vnp_TxnRef", request.getParameter("vnp_TxnRef"));
        model.addAttribute("vnp_Amount", request.getParameter("vnp_Amount"));
        model.addAttribute("vnp_OrderInfo", request.getParameter("vnp_OrderInfo"));
        model.addAttribute("vnp_ResponseCode", request.getParameter("vnp_ResponseCode"));
        model.addAttribute("vnp_TransactionNo", request.getParameter("vnp_TransactionNo"));
        model.addAttribute("vnp_BankCode", request.getParameter("vnp_BankCode"));
        model.addAttribute("vnp_PayDate", request.getParameter("vnp_PayDate"));
        String vnp_txnRef = request.getParameter("vnp_TxnRef");
        Payment paymentCheck = paymentRepository.findByTxnRef(vnp_txnRef);
        String status = "";
        if (signValue.equals(vnp_SecureHash)) {
            if (VNPayConfig.codeReturn.equals(request.getParameter("vnp_TransactionStatus"))) {
                paymentCheck.setState(true);
                status = "Success";
            } else {
                status = "Unsuccessful";
                paymentCheck.setState(false);
            }
        } else {
            status = "Invalid signature";
            paymentCheck.setState(false);
        }
        paymentRepository.save(paymentCheck);
        model.addAttribute("vnp_status", status);
        return "vnpay/vnpay_return";
    }
}
