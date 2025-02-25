package br.com.angryapps.angry.api.resources;

import br.com.angryapps.angry.api.exceptions.NotFoundResponseException;
import br.com.angryapps.angry.api.mappers.ColumnMapper;
import br.com.angryapps.angry.api.responses.ApiResponses;
import br.com.angryapps.angry.api.responses.BaseResponse;
import br.com.angryapps.angry.api.responses.ListDataResponse;
import br.com.angryapps.angry.api.responses.SingleDataResponse;
import br.com.angryapps.angry.api.vm.ColumnVM;
import br.com.angryapps.angry.api.vm.requests.ChangeColumnPositionRequest;
import br.com.angryapps.angry.api.vm.requests.PatchColumn;
import br.com.angryapps.angry.db.ColumnRepository;
import br.com.angryapps.angry.models.Column;
import br.com.angryapps.angry.utils.ColumnUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/columns")
public class ColumnResource {

    private ColumnMapper columnMapper;
    private ColumnRepository columnRepository;

    @Autowired
    public ColumnResource(ColumnRepository columnRepository, ColumnMapper columnMapper) {
        this.columnRepository = columnRepository;
        this.columnMapper = columnMapper;
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    public SingleDataResponse<ColumnVM> upsertColumn(@Valid @RequestBody ColumnVM columnVM) {
        if (columnVM.getId() == null) {
            columnVM.setCreatedAt(LocalDateTime.now());
        }

        columnVM.setUpdatedAt(LocalDateTime.now());

        Column newColumn = columnMapper.mapToColumn(columnVM);

        columnRepository.save(newColumn);

        return ApiResponses.single("Column created", columnMapper.mapToColumnVM(newColumn));
    }

    @PatchMapping("{id}")
    public SingleDataResponse<ColumnVM> patchColumn(@PathVariable UUID id, @Valid @RequestBody PatchColumn patchColumn) {
        Column column = columnRepository.findById(id)
                                        .orElseThrow(() -> new NotFoundResponseException("Column not found"));

        columnMapper.patchColumn(patchColumn, column);

        columnRepository.save(column);

        // Remove the cards from the column to return a smaller json
        column.setCards(null);

        return ApiResponses.single("Column patched", columnMapper.mapToColumnVM(column));
    }

    @PatchMapping("changePosition/{id}")
    public SingleDataResponse<ColumnVM> changePosition(@PathVariable UUID id, @Valid @RequestBody ChangeColumnPositionRequest request) {
        Column currentColumn = columnRepository.findById(id)
                                               .orElseThrow(() -> new NotFoundResponseException("Column not found"));

        Column previousColumn = null, nextColumn = null;
        if (request.getPreviousColumnId() != null) {
            previousColumn = columnRepository.findById(request.getPreviousColumnId())
                                             .orElseThrow(() -> new NotFoundResponseException("Previous column not found"));
        }

        if (request.getNextColumnId() != null) {
            nextColumn = columnRepository.findById(request.getNextColumnId())
                                         .orElseThrow(() -> new NotFoundResponseException("Next column not found"));
        }

        double newPosition = ColumnUtils.calculateNewPosition(previousColumn, nextColumn);

        currentColumn.setPosition(newPosition);
        columnRepository.save(currentColumn);

        return ApiResponses.single("Column position changed", columnMapper.mapToColumnVM(currentColumn));
    }

    @GetMapping
    public ListDataResponse<ColumnVM> getAllColumns() {
        List<ColumnVM> columns = columnRepository.findAll(Sort.by(Sort.Direction.ASC, "position"))
                                                 .stream()
                                                 .map(columnMapper::mapToColumnVM)
                                                 .toList();

        return ApiResponses.list(columns);
    }

    @GetMapping(path = "/{id}")
    public SingleDataResponse<ColumnVM> getColumnById(@PathVariable UUID id) {
        Optional<Column> byId = columnRepository.findById(id);
        Column column = byId.orElseThrow(() -> new NotFoundResponseException("Column by id not found"));

        ColumnVM columnVM = columnMapper.mapToColumnVM(column);

        return ApiResponses.single(columnVM);
    }

    @DeleteMapping(path = "/{id}")
    public BaseResponse deleteColumn(@PathVariable UUID id) {
        columnRepository.deleteById(id);

        return ApiResponses.ok("Column deleted successfully");
    }
}
