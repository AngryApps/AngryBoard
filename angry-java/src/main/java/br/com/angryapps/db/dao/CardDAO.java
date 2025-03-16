package br.com.angryapps.db.dao;

import br.com.angryapps.db.dto.CardDTO;
import org.jvnet.hk2.annotations.Contract;

import java.util.List;
import java.util.UUID;

@Contract
public interface CardDAO {

    List<CardDTO> findByColumnIdAndPositionGreaterThanEqualAndIdNotOrderByPositionAsc(UUID id, int positionIsGreaterThan, UUID id1);

    List<CardDTO> findByColumnIdAndPositionGreaterThanEqualOrderByPositionAsc(UUID columnId, int position);
}
