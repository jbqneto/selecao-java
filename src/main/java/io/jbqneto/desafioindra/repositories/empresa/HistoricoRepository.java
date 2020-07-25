package io.jbqneto.desafioindra.repositories.empresa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.jbqneto.desafioindra.database.dao.query.IHistoricoMediaPorUf;
import io.jbqneto.desafioindra.models.empresa.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
	
	public Historico findById(long id);
	public List<Historico> findByRevendaMunicipioEstadoUf(String uf);
	public List<Historico> findByRevendaMunicipioEstadoRegiao(String regiao);
	public List<Historico> findByRevendaMunicipioNome(String nome);
	
	/**
	 * TODO Corrigir (nao deu tempo)
	@Query(value = "select e.id AS uf_id, e.uf AS uf, AVG(valor_compra) AS compra, "
			+ "AVG(valor_venda) as venda from Historico h "
			+ "join Municipio m on h.municipio_id=m.id "
			+ "join Estado e on m.estado_id=e.id where uf=:uf "
			+ "group by e.id, e.uf")
	public List<IHistoricoMediaPorUf> findMediaPrecoCombustiveisPorUf(@Param("uf") String uf); */
}
