package app.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cmd")
public class Commande  implements Serializable {	
	private static final long serialVersionUID = -2287123466001297371L;


	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_commande")
	private Long id;
	
	
	private String produit;
	private Integer nombre;
	private Float prix;
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "id_client")
	private Client client;

	@Override
	public String toString() {
		return "Commande [" + (id != null ? "id=" + id + ", " : "")
				+ (produit != null ? "produit=" + produit + ", " : "")
				+ (nombre != null ? "nombre=" + nombre + ", " : "") + (prix != null ? "prix=" + prix + ", " : "")
				+ (date != null ? "date=" + date + ", " : "") + (client != null ? "client=" + client.getId() : "") + "]";
	}	
	

}
