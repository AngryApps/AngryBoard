package br.com.angryapps.db.dao;

import br.com.angryapps.db.dto.CardDTO;
import org.jvnet.hk2.annotations.Contract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Contract
public interface CardDAO {

    Optional<CardDTO> findById(UUID id);

    List<CardDTO> findAll();

    List<CardDTO> findByColumnId(UUID columnId);

    CardDTO save(CardDTO cardDTO);

    void deleteById(UUID id);

    void deleteByColumnId(UUID columnId);

    boolean existsById(UUID id);

    long count();

    List<CardDTO> findAllOrderByPositionAsc();

    List<CardDTO> findByColumnIdAndPositionGreaterThanEqualAndIdNotOrderByPositionAsc(UUID id, int positionIsGreaterThan, UUID id1);

    List<CardDTO> findByColumnIdAndPositionGreaterThanEqualOrderByPositionAsc(UUID columnId, int position);

    List<CardDTO> findAllByColumnId(List<UUID> ids);
}
