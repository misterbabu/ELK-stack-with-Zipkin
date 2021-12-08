package com.student.os.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.os.api.common.Payment;
import com.student.os.api.common.TransactionRequest;
import com.student.os.api.common.TransactionResponse;
import com.student.os.api.entity.Order;
import com.student.os.api.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private RestTemplate template;
	
	private Logger log = LoggerFactory.getLogger(OrderService.class); 
	private String url;
	public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException
	{
		String response = "";
		Order order = request.getOrder();
		Payment payment = request.getPayment();
		payment.setOrderId(order.getId());
		payment.setAmount(order.getPrice());
		
		log.info("OrderService request : {}",new ObjectMapper().writeValueAsString(request));
		
		Payment paymentResponse = template.postForObject(url= "http://PAYMENT-SERVICE/payment/doPayment", payment, Payment.class);
		
		log.info("Payment-Service response from OrderService Rest call : {}",new ObjectMapper().writeValueAsString(paymentResponse));
		response = paymentResponse.getPaymentStatus().equals("success")?"payment processing successful and order place":"there is failure in apyment api, order added to card";
		
		repository.save(order);
		return new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(),response);
	}

}
