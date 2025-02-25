package br.com.angryapps.angry.api.resources;

import br.com.angryapps.angry.api.exceptions.NotFoundResponseException;
import br.com.angryapps.angry.api.mappers.CardMapper;
import br.com.angryapps.angry.api.responses.ApiResponses;
import br.com.angryapps.angry.api.responses.BaseResponse;
import br.com.angryapps.angry.api.responses.ListDataResponse;
import br.com.angryapps.angry.api.responses.SingleDataResponse;
import br.com.angryapps.angry.api.vm.CardVM;
import br.com.angryapps.angry.api.vm.requests.ChangeCardPositionRequest;
import br.com.angryapps.angry.api.vm.requests.PatchCard;
import br.com.angryapps.angry.db.CardRepository;
import br.com.angryapps.angry.db.ColumnRepository;
import br.com.angryapps.angry.models.Card;
import br.com.angryapps.angry.models.Column;
import br.com.angryapps.angry.utils.CardUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/cards")
public class CardResource {

    private CardRepository cardRepository;
    private ColumnRepository columnRepository;
    private CardMapper cardMapper;

    @Autowired
    public CardResource(CardRepository cardRepository, ColumnRepository columnRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.columnRepository = columnRepository;
        this.cardMapper = cardMapper;
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    public SingleDataResponse<CardVM> upsertCard(@Valid @RequestBody CardVM cardVM) {
        Column column = columnRepository.findById(cardVM.getColumnId())
                                        .orElseThrow(() -> new NotFoundResponseException("Column not found"));

        if (cardVM.getId() == null) {
            cardVM.setCreatedAt(LocalDateTime.now());
        }

        cardVM.setUpdatedAt(LocalDateTime.now());

        Card newCard = cardMapper.mapToCard(cardVM, column);
        cardRepository.save(newCard);

        return ApiResponses.single("Card created", cardMapper.mapToCardVM(newCard));
    }

    @PatchMapping("{id}")
    public SingleDataResponse<CardVM> patchCard(@PathVariable UUID id, @Valid @RequestBody PatchCard patchCard) {
        Card card = cardRepository.findById(id)
                                  .orElseThrow(() -> new NotFoundResponseException("Card not found"));

        cardMapper.patchCard(patchCard, card);

        cardRepository.save(card);

        return ApiResponses.single("Card patched", cardMapper.mapToCardVM(card));
    }

    @PatchMapping("changePosition/{id}")
    public SingleDataResponse<CardVM> changePosition(@PathVariable UUID id, @Valid @RequestBody ChangeCardPositionRequest request) {
        Card currentCard = cardRepository.findById(id)
                                         .orElseThrow(() -> new NotFoundResponseException("Current card not found"));

        Column targetColumn = columnRepository.findById(request.getTargetColumnId())
                                              .orElseThrow(() -> new NotFoundResponseException("Column not found"));

        Card previousCard = null, nextCard = null;
        if (request.getPreviousCardId() != null) {
            previousCard = cardRepository.findById(request.getPreviousCardId())
                                         .orElseThrow(() -> new NotFoundResponseException("Previous card not found"));
        }

        if (request.getNextCardId() != null) {
            nextCard = cardRepository.findById(request.getNextCardId())
                                     .orElseThrow(() -> new NotFoundResponseException("Next card not found"));
        }

        double newPosition = CardUtils.calculateNewPosition(previousCard, nextCard);

        currentCard.setPosition(newPosition);
        currentCard.setColumn(targetColumn);
        currentCard.setUpdatedAt(LocalDateTime.now());
        cardRepository.save(currentCard);

        return ApiResponses.single("Card position changed", cardMapper.mapToCardVM(currentCard));
    }

    @GetMapping
    public ListDataResponse<CardVM> getAllCards() {
        List<CardVM> cards = cardRepository.findAll(Sort.by(Sort.Direction.ASC, "position"))
                                           .stream()
                                           .map(cardMapper::mapToCardVM)
                                           .toList();

        return ApiResponses.list(cards);
    }

    @GetMapping(path = "/{id}")
    public SingleDataResponse<CardVM> getCardById(@PathVariable("id") UUID id) {
        Optional<Card> cardById = cardRepository.findById(id);
        Card card = cardById.orElseThrow(() -> new NotFoundResponseException("Card not found"));

        CardVM cardVM = cardMapper.mapToCardVM(card);
        return ApiResponses.single(cardVM);
    }

    @DeleteMapping(path = "/{id}")
    public BaseResponse deleteCardById(@PathVariable("id") UUID id) {
        cardRepository.deleteById(id);

        return ApiResponses.ok("Card deleted successfully");
    }
}
