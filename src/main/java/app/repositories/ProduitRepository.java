package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.models.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

}
