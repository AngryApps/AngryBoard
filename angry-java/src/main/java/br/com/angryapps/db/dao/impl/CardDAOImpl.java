package br.com.angryapps.db.dao.impl;

import br.com.angryapps.db.dao.CardDAO;
import br.com.angryapps.db.dto.CardDTO;
import jakarta.inject.Inject;
import org.jdbi.v3.core.Jdbi;
import org.jvnet.hk2.annotations.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CardDAOImpl implements CardDAO {

    private final Jdbi jdbi;

    @Inject
    public CardDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public List<CardDTO> findByColumnIdAndPositionGreaterThanEqualAndIdNotOrderByPositionAsc(UUID columnId, int position, UUID id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM cards WHERE column_id = :columnId AND position >= :position AND id != :id ORDER BY position ASC")
                                               .bind("columnId", columnId)
                                               .bind("position", position)
                                               .bind("id", id)
                                               .mapToBean(CardDTO.class)
                                               .list()
        );
    }

    @Override
    public List<CardDTO> findByColumnIdAndPositionGreaterThanEqualOrderByPositionAsc(UUID columnId, int position) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM cards WHERE column_id = :columnId AND position >= :position ORDER BY position ASC")
                                               .bind("columnId", columnId)
                                               .bind("position", position)
                                               .mapToBean(CardDTO.class)
                                               .list()
        );
    }
}
