package br.com.angryapps.db.dao.impl;

import br.com.angryapps.db.dao.ColumnDAO;
import br.com.angryapps.db.dto.ColumnDTO;
import jakarta.inject.Inject;
import org.jdbi.v3.core.Jdbi;
import org.jvnet.hk2.annotations.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ColumnDAOImpl implements ColumnDAO {

    private final Jdbi jdbi;

    @Inject
    public ColumnDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
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