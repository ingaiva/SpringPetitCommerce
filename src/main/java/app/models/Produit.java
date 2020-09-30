package app.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @ToString(doNotUseGetters = true)
@NoArgsConstructor
@Entity
@Table(name = "produits")
public class Produit  implements Serializable{	
	private static final long serialVersionUID = 8700104442409776464L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_produit")
	private Long id_produit;
	
	private String libelle;
	private String description;
	private Integer stock;
	private Float prix;
	
	
	public Produit(String libelle, String description, Integer stock, Float prix) {		
		this.libelle = libelle;
		this.description = description;
		this.stock = stock;
		this.prix = prix;
	}	
		
}
