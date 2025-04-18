package interfaces;
import java.io.IOException;

/**
 * Интерфейс за командите, които могат да се изпълняват в контекста на управление на базата данни.
 * Всеки клас, който имплементира този интерфейс, трябва да реализира метода {@code execute},
 * който изпълнява конкретната команда.
 */
public interface Command{

    /**
     * Изпълнява конкретната команда с предоставените аргументи.
     *
     * @param command параметрите на командата
     * @throws IOException ако възникне грешка при изпълнение на командата (например проблеми с файловата система)
     */
    void execute(String... command) throws IOException;
}
