package com.lsqt.content.model.demo.mtm;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Entity;


@SuppressWarnings("serial")
@Entity(dynamicInsert=true,dynamicUpdate=true)  
@Table(name="ORDER_ITEM")  
@IdClass(OrderItemPK.class)  
public class OrderItem implements Serializable{  
      
    private Product product;  
    private Order order;  
    private int quantity;  
  
    @Id  
    public Product getProduct(){
        return product;  
    }  
    public void setProduct(Product product){  
        this.product=product;  
    }  
    @Id
    public Order getOrder(){  
        return order;  
    }  
    public void setOrder(Order order){  
        this.order=order;  
    }  
  
    @Column(name="quantity")  
    public int getQuantity(){  
        return quantity;  
    }  
    public void setQuantity(int quantity){  
        this.quantity=quantity;  
    }  
} 
