package app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Serializable {	
	private static final long serialVersionUID = 3423324827715957749L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_client")
	private Long id;
	private String nom;
	private String prenom;
	private Integer age;
	private String password;
	
	@OneToMany (mappedBy = "client")
	List<Commande> commandes = new ArrayList<Commande>();	
	
	public Client(String nom, String prenom, String password) {		
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
	}
	
	public Client(String nom, String prenom,Integer age, String password) {	
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Client [" + (id != null ? "id=" + id + ", " : "") + (nom != null ? "nom=" + nom + ", " : "")
				+ (prenom != null ? "prenom=" + prenom + ", " : "") + (age != null ? "age=" + age + ", " : "")
				+ (password != null ? "password=" + password + ", " : "")
				+ (commandes != null ? "commandes=" + commandes.size() : "") + "]";
	}		
	
}
