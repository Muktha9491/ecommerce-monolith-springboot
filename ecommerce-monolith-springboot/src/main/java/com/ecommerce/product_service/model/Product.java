package com.ecommerce.product_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
    
    private Double price;

    private Integer stock;

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public Integer getStock() { return stock; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(Double price) { this.price = price; }
    public void setStock(Integer stock) { this.stock = stock; }
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	public void setId(long l) {
		// TODO Auto-generated method stub
		id=l;
		
	}


}
