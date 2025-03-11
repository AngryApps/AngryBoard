package br.com.angryapps.angry;

import br.com.angryapps.angry.db.CardRepository;
import br.com.angryapps.angry.db.ColumnRepository;
import br.com.angryapps.angry.models.Card;
import br.com.angryapps.angry.models.Column;
import br.com.angryapps.angry.utils.CardUtils;
import br.com.angryapps.angry.utils.ColumnUtils;
import io.github.cdimascio.dotenv.Dotenv;
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
        configureEnvVariables();

        SpringApplication.run(AngryApplication.class, args);
    }

    private static void configureEnvVariables() {
        Dotenv dotenv = Dotenv.configure()
                              .filename("secrets.env")
                              .ignoreIfMalformed()
                              .ignoreIfMissing()
                              .load();

        String githubClientId = dotenv.get("GITHUB_CLIENT_ID");
        if (githubClientId != null) {
            System.setProperty("GITHUB_CLIENT_ID", githubClientId);
        }

        String githubClientSecret = dotenv.get("GITHUB_CLIENT_SECRET");
        if (githubClientSecret != null) {
            System.setProperty("GITHUB_CLIENT_SECRET", githubClientSecret);
        }
    }

    @Bean
    CommandLineRunner run(ColumnRepository columnRepository, CardRepository cardRepository) {
        return (args) -> {
            List<Column> columns = new ArrayList<>();
            List<Card> cards = new ArrayList<>();

            columns.add(newColumn("Backlog", "Where we define what we need to do", ColumnUtils.DEFAULT_POSITION));
            columns.add(newColumn("In Progress", "Where we do the work", 2 * ColumnUtils.DEFAULT_POSITION));
            columns.add(newColumn("Done", "Where we show what we did", 3 * ColumnUtils.DEFAULT_POSITION));
            columns.add(newColumn("Archived", "Where we archive what we don't need to do anymore", 4 * ColumnUtils.DEFAULT_POSITION));

            columnRepository.saveAll(columns);

            cards.add(newCard("Create card modal", "create the modal to edit cards", columns.get(0), CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create websocket for Columns", "Make changes in real time with websocket", columns.get(0), 2 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create the smartphone App", "Create the Android and Iphone apps", columns.get(0), 3 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create the backend tests", "Create integration tests for the backend routes", columns.get(0), 4 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Implement dark mode", "Add dark mode support across all components", columns.get(0), 5 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Add drag and drop animations", "Smooth animations when moving cards between columns", columns.get(0), 6 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create user authentication", "Implement JWT authentication system", columns.get(0), 7 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Add card attachments", "Allow users to attach files to cards", columns.get(0), 8 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Implement card labels", "Add color-coded labels for better organization", columns.get(0), 9 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Implement card checklists", "Add checklist functionality within cards", columns.get(0), 10 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create board statistics", "Show metrics and usage statistics for boards", columns.get(0), 11 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Add markdown support", "Enable markdown formatting in card descriptions", columns.get(0), 12 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Implement card dependencies", "Allow cards to be linked as dependencies", columns.get(0), 13 * CardUtils.DEFAULT_POSITION));

            cards.add(newCard("Create column modal", "create the modal to edit columns", columns.get(1), CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create card ordering", "Auto position cards based on edits", columns.get(1), 2 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Setup CI/CD pipeline", "Configure GitHub Actions for automated deployment", columns.get(1), 3 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Optimize database queries", "Improve performance of database operations", columns.get(1), 4 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Add card comments feature", "Allow users to comment on cards", columns.get(1), 5 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Setup Redis caching", "Implement caching layer for better performance", columns.get(1), 6 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create API documentation", "Generate Swagger/OpenAPI documentation", columns.get(1), 7 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Implement rate limiting", "Add API rate limiting for security", columns.get(1), 8 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Add board backup feature", "Automatic board state backup system", columns.get(1), 9 * CardUtils.DEFAULT_POSITION));

            cards.add(newCard("Create card CRUD", "create the main routes for the card entity", columns.get(2), CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create column CRUD", "create the main routes for the column entity", columns.get(2), 2 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create base response model", "Create a base model for API Responses that will be used for all routes", columns.get(2), 3 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create board sharing", "Implement board sharing between users", columns.get(2), 4 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Add export functionality", "Allow exporting boards to PDF/Excel", columns.get(2), 5 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Implement search feature", "Global search across all cards and columns", columns.get(2), 6 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Setup logging system", "Implement centralized logging with ELK stack", columns.get(2), 7 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create user preferences", "Allow users to customize their experience", columns.get(2), 8 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Implement board filters", "Add filtering options for cards and columns", columns.get(2), 9 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Add keyboard shortcuts", "Implement keyboard navigation shortcuts", columns.get(2), 10 * CardUtils.DEFAULT_POSITION));

            cards.add(newCard("Create GRPC on UI", "Create the GRPC protocol to communicate with the frontend", columns.get(3), CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create super fast application", "Create the application to be faster than Trello and Monday", columns.get(3), 2 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create activity log", "Track all changes made to cards and columns", columns.get(3), 3 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Add email notifications", "Send notifications for card assignments and updates", columns.get(3), 4 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Implement board templates", "Create reusable board templates", columns.get(3), 5 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Add card due dates", "Implement calendar and due date features", columns.get(3), 6 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create mobile responsive design", "Ensure UI works well on all screen sizes", columns.get(3), 7 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Setup monitoring system", "Implement health checks and monitoring", columns.get(3), 8 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Create public API", "Develop public API for third-party integrations", columns.get(3), 9 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Implement data encryption", "Add end-to-end encryption for sensitive data", columns.get(3), 10 * CardUtils.DEFAULT_POSITION));
            cards.add(newCard("Add multi-language support", "Implement i18n across the application", columns.get(3), 11 * CardUtils.DEFAULT_POSITION));

            cardRepository.saveAll(cards);
        };
    }

    private Column newColumn(String title, String description, double position) {
        Column column = new Column();
        column.setTitle(title);
        column.setDescription(description);
        column.setPosition(position);
        column.setCreatedAt(LocalDateTime.now());
        column.setUpdatedAt(LocalDateTime.now());

        return column;
    }

    private Card newCard(String title, String description, Column column, double position) {
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
