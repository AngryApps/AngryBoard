package br.com.angryapps.api.resources;

import br.com.angryapps.api.exceptions.NotFoundResponseException;
import br.com.angryapps.api.mappers.CardMapper;
import br.com.angryapps.api.responses.ApiResponses;
import br.com.angryapps.api.responses.BaseResponse;
import br.com.angryapps.api.responses.ListDataResponse;
import br.com.angryapps.api.responses.SingleDataResponse;
import br.com.angryapps.api.vm.CardVM;
import br.com.angryapps.api.vm.requests.ChangeCardPositionRequest;
import br.com.angryapps.api.vm.requests.PatchCard;
import br.com.angryapps.db.dao.CardDAO;
import br.com.angryapps.db.dao.ColumnDAO;
import br.com.angryapps.db.dto.CardDTO;
import br.com.angryapps.db.dto.ColumnDTO;
import br.com.angryapps.utils.CardUtils;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/v1/cards")
public class CardResource {

    private final CardDAO cardDAO;
    private final ColumnDAO columnDAO;
    private final CardMapper cardMapper;

    @Inject
    public CardResource(CardDAO cardDAO, ColumnDAO columnDAO, CardMapper cardMapper) {
        this.cardDAO = cardDAO;
        this.columnDAO = columnDAO;
        this.cardMapper = cardMapper;
    }

    @POST
    public SingleDataResponse<CardVM> upsertCard(@Valid CardVM cardVM) {
        ColumnDTO column = columnDAO.findById(cardVM.getColumnId())
                                    .orElseThrow(() -> new NotFoundResponseException("Column not found"));

        if (cardVM.getId() == null) {
            cardVM.setCreatedAt(LocalDateTime.now());
        }

        cardVM.setUpdatedAt(LocalDateTime.now());

        CardDTO newCard = cardMapper.mapToCard(cardVM, column);
        cardDAO.save(newCard);

        return ApiResponses.single("Card created", cardMapper.mapToCardVM(newCard));
    }

    @PATCH
    @Path("/{id}")
    public SingleDataResponse<CardVM> patchCard(@PathParam("id") UUID id, @Valid PatchCard patchCard) {
        CardDTO card = cardDAO.findById(id)
                              .orElseThrow(() -> new NotFoundResponseException("Card not found"));

        cardMapper.patchCard(patchCard, card);

        cardDAO.save(card);

        return ApiResponses.single("Card patched", cardMapper.mapToCardVM(card));
    }

    @PATCH
    @Path("/changePosition/{id}")
    public SingleDataResponse<CardVM> changePosition(@PathParam("id") UUID id, @Valid ChangeCardPositionRequest request) {
        CardDTO currentCard = cardDAO.findById(id)
                                     .orElseThrow(() -> new NotFoundResponseException("Current card not found"));

        ColumnDTO targetColumn = columnDAO.findById(request.getTargetColumnId())
                                          .orElseThrow(() -> new NotFoundResponseException("Column not found"));

        CardDTO previousCard = null, nextCard = null;
        if (request.getPreviousCardId() != null) {
            previousCard = cardDAO.findById(request.getPreviousCardId())
                                  .orElseThrow(() -> new NotFoundResponseException("Previous card not found"));
        }

        if (request.getNextCardId() != null) {
            nextCard = cardDAO.findById(request.getNextCardId())
                              .orElseThrow(() -> new NotFoundResponseException("Next card not found"));
        }

        double newPosition = CardUtils.calculateNewPosition(previousCard, nextCard);

        currentCard.setPosition(newPosition);
        currentCard.setColumnId(targetColumn.getId());
        currentCard.setUpdatedAt(LocalDateTime.now());
        cardDAO.save(currentCard);

        return ApiResponses.single("Card position changed", cardMapper.mapToCardVM(currentCard));
    }

    @GET
    public ListDataResponse<CardVM> getAllCards() {
        List<CardDTO> cards = cardDAO.findAllOrderByPositionAsc();

        List<CardVM> cardVMs = cards.stream()
                                    .map(cardMapper::mapToCardVM)
                                    .toList();

        return ApiResponses.list(cardVMs);
    }

    @GET
    @Path("/{id}")
    public SingleDataResponse<CardVM> getCardById(@PathParam("id") UUID id) {
        Optional<CardDTO> byId = cardDAO.findById(id);
        CardDTO card = byId.orElseThrow(() -> new NotFoundResponseException("Card not found"));

        CardVM cardVM = cardMapper.mapToCardVM(card);

        return ApiResponses.single(cardVM);
    }

    @DELETE
    @Path("/{id}")
    public BaseResponse deleteCard(@PathParam("id") UUID id) {
        cardDAO.deleteById(id);

        return ApiResponses.ok("Card deleted successfully");
    }
}

