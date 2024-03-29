package org.lsqt.content.model.mtm;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Entity;
import org.hibernate.annotations.GenericGenerator;


@SuppressWarnings("serial")
@Entity(dynamicInsert=true,dynamicUpdate=true)  
@Table(name="TEST_PRODUCT")  
public class Product implements Serializable{  
    private int id;  
    private String name;  
    private double price;  
    private Set<OrderItem> orderItems;  
  
    @Id
    @GenericGenerator(name="product_id",strategy="increment")  
    @GeneratedValue(generator="product_id")  
    public int getId(){  
         return id;  
    }
    public void setId(int id){  
         this.id=id;  
    }  
    public String getName(){  
         return name;  
    }  
    public void setName(String name){  
         this.name=name;  
    }  
    public double getPrice(){  
         return price;  
    }  
    public void setPrice(double price){  
         this.price=price;  
    }  
      
    @OneToMany(mappedBy="product")  
    public Set<OrderItem> getOrderItems(){  
         return orderItems;  
    }  
    public void setOrderItems(Set<OrderItem> orderItems){  
         this.orderItems=orderItems;  
    }  
} 
