package br.com.angryapps.api.resources;

import br.com.angryapps.api.mappers.ColumnMapper;
import br.com.angryapps.api.responses.ApiResponses;
import br.com.angryapps.api.responses.ListDataResponse;
import br.com.angryapps.api.vm.ColumnVM;
import br.com.angryapps.db.dao.ColumnDAO;
import br.com.angryapps.db.dto.ColumnDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/v1/columns")
public class ColumnResource {

    private final ColumnDAO columnDAO;
    private final ColumnMapper columnMapper;

    @Inject
    public ColumnResource(ColumnDAO columnDAO, ColumnMapper columnMapper) {
        this.columnDAO = columnDAO;
        this.columnMapper = columnMapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ListDataResponse<ColumnVM> getAllColumns() {
        List<ColumnDTO> columns = columnDAO.findAllOrderByPositionAsc();

        return ApiResponses.list(null);
    }
}