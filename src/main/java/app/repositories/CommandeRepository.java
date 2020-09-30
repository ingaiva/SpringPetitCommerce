package app.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import app.models.Commande;

@CrossOrigin("*")
@RepositoryRestResource
public interface CommandeRepository extends JpaRepository<Commande, Long> {
	@Query("select cmd from Commande cmd where cmd.client.id=:x")
	public List<Commande> getCommandesClient(@Param("x") Long idClient);
	
	@Transactional
	@Modifying
	@Query("delete from Commande cmd where cmd.client.id=:x")
	public void deleteCommandesClient(@Param("x") Long idClient);
}
