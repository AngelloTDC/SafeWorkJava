package br.com.safework.repository;

import br.com.safework.model.AnaliseEpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnaliseEpiRepository extends JpaRepository<AnaliseEpi, Long> {
}
