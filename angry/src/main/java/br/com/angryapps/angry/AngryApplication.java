package br.com.angryapps.angry;

import br.com.angryapps.angry.db.CardRepository;
import br.com.angryapps.angry.db.ColumnRepository;
import br.com.angryapps.angry.models.Card;
import br.com.angryapps.angry.models.Column;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AngryApplication {

    // Usar como base os projetos:
    // https://github.com/basarbk/tdd-spring-react
    public static void main(String[] args) {
        SpringApplication.run(AngryApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ColumnRepository columnRepository, CardRepository cardRepository) {
        return (args) -> {
            List<Column> columns = new ArrayList<>();
            List<Card> cards = new ArrayList<>();

            columns.add(newColumn("Backlog", "Where we define what we need to do", 0));
            columns.add(newColumn("In Progress", "Where we do the work", 1));
            columns.add(newColumn("Done", "Where we show what we did", 2));
            columns.add(newColumn("Archived", "Where we archive what we don't need to do anymore", 3));

            columnRepository.saveAll(columns);

            cards.add(newCard("Create card modal", "create the modal to edit cards", columns.get(0), 0));
            cards.add(newCard("Create websocket for Columns", "Make changes in real time with websocket", columns.get(0), 1));
            cards.add(newCard("Create the smartphone App", "Create the Android and Iphone apps", columns.get(0), 2));
            cards.add(newCard("Create the backend tests", "Create integration tests for the backend routes", columns.get(0), 3));
            cards.add(newCard("Create column modal", "create the modal to edit columns", columns.get(1), 0));
            cards.add(newCard("Create card ordering", "Auto position cards based on edits", columns.get(1), 1));
            cards.add(newCard("Create card CRUD", "create the main routes for the card entity", columns.get(2), 0));
            cards.add(newCard("Create column CRUD", "create the main routes for the column entity", columns.get(2), 1));
            cards.add(newCard("Create base response model", "Create a base model for API Responses that will be used for all routes", columns.get(2), 2));
            cards.add(newCard("Create GRPC on UI", "Create the GRPC protocol to communicate with the frontend", columns.get(3), 0));
            cards.add(newCard("Create super fast application", "Create the application to be faster than Trello and Monday", columns.get(3), 1));

            cardRepository.saveAll(cards);
        };
    }

    private Column newColumn(String title, String description, int position) {
        Column column = new Column();
        column.setTitle(title);
        column.setDescription(description);
        column.setPosition(position);
        column.setCreatedAt(LocalDateTime.now());
        column.setUpdatedAt(LocalDateTime.now());

        return column;
    }

    private Card newCard(String title, String description, Column column, int position) {
        Card card = new Card();
        card.setTitle(title);
        card.setDescription(description);
        card.setColumn(column);
        card.setPosition(position);
        card.setCreatedAt(LocalDateTime.now());
        card.setUpdatedAt(LocalDateTime.now());

        return card;
    }
}
