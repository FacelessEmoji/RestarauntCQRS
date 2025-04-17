package restaurant;

import restaurant.api.console.ConsoleInterface;
import restaurant.api.facade.RestaurantFacade;
import restaurant.command.handler.*;
import restaurant.command.repository.OrderRepository;
import restaurant.common.event.EventBus;
import restaurant.query.repository.OrderViewRepository;
import restaurant.query.service.EventHandler;
import restaurant.query.service.OrderQueryService;

public class RestaurantApplication {
    public static void main(String[] args) {
        // Инициализация репозитория для командной части (in-memory)
        OrderRepository commandRepository = new OrderRepository();

        // Инициализация репозитория для представлений (query)
        OrderViewRepository orderViewRepository = new OrderViewRepository();

        // Инициализация обработчика событий для синхронизации query-модели
        EventHandler eventHandler = new EventHandler(orderViewRepository);
        EventBus.getInstance().register(eventHandler);

        // Инициализация шины команд и регистрация обработчиков команд
        CommandBus commandBus = new CommandBus();
        commandBus.register(restaurant.command.command.CreateCustomerOrderCommand.class, new CreateCustomerOrderHandler(commandRepository));
        commandBus.register(restaurant.command.command.AddDishToOrderCommand.class, new AddDishToOrderHandler(commandRepository));
        commandBus.register(restaurant.command.command.UpdateOrderItemCommand.class, new UpdateOrderItemHandler(commandRepository));
        commandBus.register(restaurant.command.command.ChangeOrderStatusCommand.class, new ChangeOrderStatusHandler(commandRepository));
        commandBus.register(restaurant.command.command.CompleteOrderCommand.class, new CompleteOrderHandler(commandRepository));

        // Инициализация сервиса запросов
        OrderQueryService queryService = new OrderQueryService(orderViewRepository);

        // Инициализация фасада, объединяющего командную и запросную части
        RestaurantFacade facade = new RestaurantFacade(commandBus, queryService);

        // Запуск консольного интерфейса
        ConsoleInterface consoleInterface = new ConsoleInterface(facade);
        consoleInterface.start();
    }
}
