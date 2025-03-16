package br.com.angryapps.db.dao;

import br.com.angryapps.db.dto.ColumnDTO;
import org.jvnet.hk2.annotations.Contract;

import java.util.List;
import java.util.UUID;

@Contract
public interface ColumnDAO {

    List<ColumnDTO> findAllOrderByPositionAsc();

    List<ColumnDTO> findByPositionGreaterThanEqualOrderByPositionAsc(int position);

    List<ColumnDTO> findByPositionGreaterThanEqualAndIdNotOrderByPositionAsc(int position, UUID id);
}
