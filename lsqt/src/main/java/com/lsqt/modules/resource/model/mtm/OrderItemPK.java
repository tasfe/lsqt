package com.lsqt.modules.resource.model.mtm;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable  
public class OrderItemPK implements Serializable{  
    private Product product;  
    private Order order;  
  
    @ManyToOne  
    @JoinColumn(name="product_id",referencedColumnName="id")  
    public Product getProduct(){  
        return product;  
    }  
    public void setProduct(Product product){  
        this.product=product;  
    }  
  
    @ManyToOne  
    @JoinColumn(name="order_id",referencedColumnName="id")  
    public Order getOrder(){  
        return order;  
    }  
    public void setOrder(Order order){  
        this.order=order;  
    }  
   
}  