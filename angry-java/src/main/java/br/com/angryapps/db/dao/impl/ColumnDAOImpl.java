package br.com.angryapps.db.dao.impl;

import br.com.angryapps.db.dao.ColumnDAO;
import br.com.angryapps.db.dto.ColumnDTO;
import jakarta.inject.Inject;
import org.jdbi.v3.core.Jdbi;
import org.jvnet.hk2.annotations.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ColumnDAOImpl implements ColumnDAO {

    private final Jdbi jdbi;

    @Inject
    public ColumnDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Optional<ColumnDTO> findById(UUID id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM columns WHERE id = :id")
                                               .bind("id", id)
                                               .mapToBean(ColumnDTO.class)
                                               .findOne()
        );
    }

    @Override
    public List<ColumnDTO> findAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM columns")
                                               .mapToBean(ColumnDTO.class)
                                               .list()
        );
    }

    @Override
    public ColumnDTO save(ColumnDTO columnDTO) {
        if (columnDTO.getId() == null) {
            return insert(columnDTO);
        } else {
            return update(columnDTO);
        }
    }

    private ColumnDTO insert(ColumnDTO columnDTO) {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO columns (id, title, description, position, created_at, updated_at) VALUES (:id, :title, :description, :position, :createdAt, :updatedAt)")
                                       .bind("id", id)
                                       .bind("title", columnDTO.getTitle())
                                       .bind("description", columnDTO.getDescription())
                                       .bind("position", columnDTO.getPosition())
                                       .bind("createdAt", now)
                                       .bind("updatedAt", now)
                                       .execute()
        );

        columnDTO.setId(id);
        columnDTO.setCreatedAt(now);
        columnDTO.setUpdatedAt(now);
        return columnDTO;
    }

    private ColumnDTO update(ColumnDTO columnDTO) {
        LocalDateTime now = LocalDateTime.now();

        jdbi.useHandle(handle -> handle.createUpdate("UPDATE columns SET title = :title, description = :description, position = :position, updated_at = :updatedAt WHERE id = :id")
                                       .bind("id", columnDTO.getId())
                                       .bind("title", columnDTO.getTitle())
                                       .bind("description", columnDTO.getDescription())
                                       .bind("position", columnDTO.getPosition())
                                       .bind("updatedAt", now)
                                       .execute()
        );

        columnDTO.setUpdatedAt(now);
        return columnDTO;
    }

    @Override
    public void deleteById(UUID id) {
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM columns WHERE id = :id")
                                       .bind("id", id)
                                       .execute()
        );
    }

    @Override
    public boolean existsById(UUID id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT COUNT(*) FROM columns WHERE id = :id")
                                               .bind("id", id)
                                               .mapTo(Integer.class)
                                               .one() > 0
        );
    }

    @Override
    public long count() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT COUNT(*) FROM columns")
                                               .mapTo(Long.class)
                                               .one()
        );
    }

    @Override
    public List<ColumnDTO> findAllOrderByPositionAsc() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM columns ORDER BY position ASC")
                                               .mapToBean(ColumnDTO.class)
                                               .list()
        );
    }

    @Override
    public List<ColumnDTO> findByPositionGreaterThanEqualOrderByPositionAsc(int position) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM columns WHERE position >= :position ORDER BY position ASC")
                                               .bind("position", position)
                                               .mapToBean(ColumnDTO.class)
                                               .list()
        );
    }

    @Override
    public List<ColumnDTO> findByPositionGreaterThanEqualAndIdNotOrderByPositionAsc(int position, UUID id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM columns WHERE position >= :position AND id != :id ORDER BY position ASC")
                                               .bind("position", position)
                                               .bind("id", id)
                                               .mapToBean(ColumnDTO.class)
                                               .list()
        );
    }
}