package br.com.angryapps.api.resources;

import br.com.angryapps.api.exceptions.NotFoundResponseException;
import br.com.angryapps.api.mappers.ColumnMapper;
import br.com.angryapps.api.responses.ApiResponses;
import br.com.angryapps.api.responses.BaseResponse;
import br.com.angryapps.api.responses.ListDataResponse;
import br.com.angryapps.api.responses.SingleDataResponse;
import br.com.angryapps.api.vm.ColumnVM;
import br.com.angryapps.api.vm.requests.ChangeColumnPositionRequest;
import br.com.angryapps.api.vm.requests.PatchColumn;
import br.com.angryapps.db.dao.ColumnDAO;
import br.com.angryapps.db.dto.ColumnDTO;
import br.com.angryapps.utils.ColumnUtils;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/v1/columns")
public class ColumnResource {

    private final ColumnDAO columnDAO;
    private final ColumnMapper columnMapper;

    @Inject
    public ColumnResource(ColumnDAO columnDAO, ColumnMapper columnMapper) {
        this.columnDAO = columnDAO;
        this.columnMapper = columnMapper;
    }

    @POST
    public SingleDataResponse<ColumnVM> upsertColumn(@Valid ColumnVM columnVM) {
        if (columnVM.getId() == null) {
            columnVM.setCreatedAt(LocalDateTime.now());
        }

        columnVM.setUpdatedAt(LocalDateTime.now());

        ColumnDTO newColumn = columnMapper.mapToColumn(columnVM);

        columnDAO.save(newColumn);

        return ApiResponses.single("Column created", columnMapper.mapToColumnVM(newColumn));
    }

    @PATCH
    @Path("/{id}")
    public SingleDataResponse<ColumnVM> patchColumn(@PathParam("id") UUID id, @Valid PatchColumn patchColumn) {
        ColumnDTO column = columnDAO.findById(id)
                                    .orElseThrow(() -> new NotFoundResponseException("Column not found"));

        columnMapper.patchColumn(patchColumn, column);

        columnDAO.save(column);

        return ApiResponses.single("Column patched", columnMapper.mapToColumnVM(column));
    }

    @PATCH
    @Path("/changePosition/{id}")
    public SingleDataResponse<ColumnVM> changePosition(@PathParam("id") UUID id, @Valid ChangeColumnPositionRequest request) {
        ColumnDTO currentColumn = columnDAO.findById(id)
                                           .orElseThrow(() -> new NotFoundResponseException("Column not found"));

        ColumnDTO previousColumn = null, nextColumn = null;
        if (request.getPreviousColumnId() != null) {
            previousColumn = columnDAO.findById(request.getPreviousColumnId())
                                      .orElseThrow(() -> new NotFoundResponseException("Previous column not found"));
        }

        if (request.getNextColumnId() != null) {
            nextColumn = columnDAO.findById(request.getNextColumnId())
                                  .orElseThrow(() -> new NotFoundResponseException("Next column not found"));
        }

        double newPosition = ColumnUtils.calculateNewPosition(previousColumn, nextColumn);

        currentColumn.setPosition(newPosition);
        columnDAO.save(currentColumn);

        return ApiResponses.single("Column position changed", columnMapper.mapToColumnVM(currentColumn));
    }

    @GET
    public ListDataResponse<ColumnVM> getAllColumns() {
        List<ColumnDTO> columns = columnDAO.findAllOrderByPositionAsc();

        List<ColumnVM> columnVMs = columns.stream()
                                          .map(columnMapper::mapToColumnVM)
                                          .toList();

        return ApiResponses.list(columnVMs);
    }

    @GET
    @Path("/{id}")
    public SingleDataResponse<ColumnVM> getColumnById(@PathParam("id") UUID id) {
        Optional<ColumnDTO> byId = columnDAO.findById(id);
        ColumnDTO column = byId.orElseThrow(() -> new NotFoundResponseException("Column by id not found"));

        ColumnVM columnVM = columnMapper.mapToColumnVM(column);

        return ApiResponses.single(columnVM);
    }

    @DELETE
    @Path("/{id}")
    public BaseResponse deleteColumn(@PathParam("id") UUID id) {
        columnDAO.deleteById(id);

        return ApiResponses.ok("Column deleted successfully");
    }
}