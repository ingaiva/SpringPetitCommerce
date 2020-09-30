package app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import app.models.Client;
import app.models.Commande;


@CrossOrigin("*")
@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	
	
//	@Query("select cmd from Commande cmd where cmd.client.id_client=:x and cmd.produit like:y")
//	public List<Commande> getCommandesClientByKeyWord(@Param("x") Long idClient, @Param("y") String mc);
//	
//	@Query("select cmd from Commande cmd where cmd.client.id_client!=:x")
//	public List<Commande> getCommandesAutresClients(@Param("x") Long idClient);
//	
//	@Query("select c from Client c where c.id_client !=:x")
//	public List<Client> getAutresClients(@Param("x") Long idClient);
	
	@Query("select c from Client c where c.nom =:x")
	public List<Client> getClientByName(@Param("x") String nomClient);
	
	@Query("select c from Client c where c.nom =:x and c.id !=:y")
	public List<Client> getClientByName(@Param("x") String nomClient,@Param("y") Long idClientToExclude);
	
	//public static boolean isClientExist(String nom , String idToExclude) {
	@Query("select c from Client c where c.nom =:x and c.id !=:y")
	public List<Client> getClientByNameExcludeId(@Param("x") String nomClient, @Param("y") Long idClientToExclude);
}
