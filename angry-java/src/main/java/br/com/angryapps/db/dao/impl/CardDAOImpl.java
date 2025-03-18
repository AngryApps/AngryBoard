package br.com.angryapps.db.dao.impl;

import br.com.angryapps.db.dao.CardDAO;
import br.com.angryapps.db.dto.CardDTO;
import jakarta.inject.Inject;
import org.jdbi.v3.core.Jdbi;
import org.jvnet.hk2.annotations.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardDAOImpl implements CardDAO {

    private final Jdbi jdbi;

    @Inject
    public CardDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Optional<CardDTO> findById(UUID id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM cards WHERE id = :id")
                                               .bind("id", id)
                                               .mapToBean(CardDTO.class)
                                               .findOne()
        );
    }

    @Override
    public List<CardDTO> findAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM cards")
                                               .mapToBean(CardDTO.class)
                                               .list()
        );
    }

    @Override
    public List<CardDTO> findByColumnId(UUID columnId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM cards WHERE column_id = :columnId ORDER BY position ASC")
                                               .bind("columnId", columnId)
                                               .mapToBean(CardDTO.class)
                                               .list()
        );
    }

    @Override
    public CardDTO save(CardDTO cardDTO) {
        if (cardDTO.getId() == null) {
            return insert(cardDTO);
        } else {
            return update(cardDTO);
        }
    }

    private CardDTO insert(CardDTO cardDTO) {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO cards (id, title, description, position, column_id, created_at, updated_at) VALUES (:id, :title, :description, :position, :columnId, :createdAt, :updatedAt)")
                                       .bind("id", id)
                                       .bind("title", cardDTO.getTitle())
                                       .bind("description", cardDTO.getDescription())
                                       .bind("position", cardDTO.getPosition())
                                       .bind("columnId", cardDTO.getColumnId())
                                       .bind("createdAt", now)
                                       .bind("updatedAt", now)
                                       .execute()
        );

        cardDTO.setId(id);
        cardDTO.setCreatedAt(now);
        cardDTO.setUpdatedAt(now);
        return cardDTO;
    }

    private CardDTO update(CardDTO cardDTO) {
        LocalDateTime now = LocalDateTime.now();

        jdbi.useHandle(handle -> handle.createUpdate("UPDATE cards SET title = :title, description = :description, position = :position, column_id = :columnId, updated_at = :updatedAt WHERE id = :id")
                                       .bind("id", cardDTO.getId())
                                       .bind("title", cardDTO.getTitle())
                                       .bind("description", cardDTO.getDescription())
                                       .bind("position", cardDTO.getPosition())
                                       .bind("columnId", cardDTO.getColumnId())
                                       .bind("updatedAt", now)
                                       .execute()
        );

        cardDTO.setUpdatedAt(now);
        return cardDTO;
    }

    @Override
    public void deleteById(UUID id) {
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM cards WHERE id = :id")
                                       .bind("id", id)
                                       .execute()
        );
    }

    @Override
    public void deleteByColumnId(UUID columnId) {
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM cards WHERE column_id = :columnId")
                                       .bind("columnId", columnId)
                                       .execute()
        );
    }

    @Override
    public boolean existsById(UUID id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT COUNT(*) FROM cards WHERE id = :id")
                                               .bind("id", id)
                                               .mapTo(Integer.class)
                                               .one() > 0
        );
    }

    @Override
    public long count() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT COUNT(*) FROM cards")
                                               .mapTo(Long.class)
                                               .one()
        );
    }

    @Override
    public List<CardDTO> findAllOrderByPositionAsc() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM cards ORDER BY position ASC")
                                               .mapToBean(CardDTO.class)
                                               .list()
        );
    }

    @Override
    public List<CardDTO> findAllByColumnId(List<UUID> ids) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM cards WHERE column_id IN (<ids>) ORDER BY position ASC")
                                               .bindList("ids", ids)
                                               .mapToBean(CardDTO.class)
                                               .list()
        );
    }
}
