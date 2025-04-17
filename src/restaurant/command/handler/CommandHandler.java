package restaurant.command.handler;

import restaurant.command.command.Command;

/**
 * Интерфейс обработчика команд.
 */
public interface CommandHandler<T extends Command> {
    void handle(T command);
}
