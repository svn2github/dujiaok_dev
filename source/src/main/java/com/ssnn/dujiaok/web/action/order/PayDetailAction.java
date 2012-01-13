package com.ssnn.dujiaok.web.action.order;

import com.ssnn.dujiaok.biz.service.product.ProductService;
import com.ssnn.dujiaok.model.product.Product;
import com.ssnn.dujiaok.web.action.BasicAction;

public class PayDetailAction extends BasicAction {
	private String orderId;
	private String productId;
	private String amount;
	
	private ProductService productService;
	
	@Override
	public String execute() {
		Product product = this.productService.getProductById(new Integer(productId));
        if(product != null){
            this.getHttpSession().setAttribute("post", product.getPost());
        }
        this.getHttpSession().setAttribute("amount", amount);
        this.getHttpSession().setAttribute("orderId", orderId);
        this.getHttpSession().setAttribute("product", product);
        return SUCCESS;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
}
