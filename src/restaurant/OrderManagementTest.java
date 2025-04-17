package restaurant;

import restaurant.api.facade.RestaurantFacade;
import restaurant.command.handler.CommandBus;
import restaurant.command.handler.CreateCustomerOrderHandler;
import restaurant.command.handler.AddDishToOrderHandler;
import restaurant.command.handler.UpdateOrderItemHandler;
import restaurant.command.handler.ChangeOrderStatusHandler;
import restaurant.command.handler.CompleteOrderHandler;
import restaurant.command.repository.OrderRepository;
import restaurant.common.event.EventBus;
import restaurant.query.repository.OrderViewRepository;
import restaurant.query.service.EventHandler;
import restaurant.query.service.OrderQueryService;
import restaurant.command.model.OrderStatus;

import java.util.List;

public class OrderManagementTest {
    public static void main(String[] args) {
        // Инициализация репозитория командной части
        OrderRepository commandRepository = new OrderRepository();

        // Инициализация репозитория представлений
        OrderViewRepository orderViewRepository = new OrderViewRepository();

        // Инициализация обработчика событий и регистрация в шине
        EventHandler eventHandler = new EventHandler(orderViewRepository);
        EventBus.getInstance().register(eventHandler);

        // Инициализация шины команд и регистрация обработчиков
        CommandBus commandBus = new CommandBus();
        commandBus.register(restaurant.command.command.CreateCustomerOrderCommand.class, new CreateCustomerOrderHandler(commandRepository));
        commandBus.register(restaurant.command.command.AddDishToOrderCommand.class, new AddDishToOrderHandler(commandRepository));
        commandBus.register(restaurant.command.command.UpdateOrderItemCommand.class, new UpdateOrderItemHandler(commandRepository));
        commandBus.register(restaurant.command.command.ChangeOrderStatusCommand.class, new ChangeOrderStatusHandler(commandRepository));
        commandBus.register(restaurant.command.command.CompleteOrderCommand.class, new CompleteOrderHandler(commandRepository));

        // Инициализация запроса
        OrderQueryService queryService = new OrderQueryService(orderViewRepository);

        // Инициализация фасада
        RestaurantFacade facade = new RestaurantFacade(commandBus, queryService);

        // --- Тестирование основных операций ---

        // 1. Создание нового заказа
        facade.createOrder("Иванов Иван");
        // Предполагаем, что ID заказа можно получить из запроса к фасаду (например, получить список заказов)
        List<restaurant.query.dto.OrderDTO> orders = facade.getAllOrders();
        String orderId = orders.get(0).getId();
        System.out.println("Создан заказ с ID: " + orderId);

        // 2. Добавление блюда в заказ
        facade.addDish(orderId, "Бургер", 2);
        facade.addDish(orderId, "Картофель Фри", 1);

        // 3. Изменение количества блюда
        facade.updateDish(orderId, "Бургер", 3);

        // 4. Изменение статуса заказа (например, перевод в IN_PROGRESS)
        facade.changeOrderStatus(orderId, OrderStatus.IN_PROGRESS);

        // 5. Завершение заказа
        facade.completeOrder(orderId);

        // 6. Получение деталей заказа
        restaurant.query.dto.OrderDTO orderDTO = facade.getOrder(orderId);
        System.out.println("\n=== Детали заказа ===");
        System.out.println("ID: " + orderDTO.getId());
        System.out.println("Клиент: " + orderDTO.getCustomerName());
        System.out.println("Статус: " + orderDTO.getStatus());
        System.out.println("Создан: " + orderDTO.getCreatedAt());
        System.out.println("Блюда: " + orderDTO.getItems());

        // 7. Получение статистики заказа
        restaurant.query.dto.OrderStatisticsDTO stats = facade.getOrderStatistics(orderId);
        System.out.println("\n=== Статистика заказа ===");
        System.out.println("Общее количество блюд: " + stats.getTotalDishes());
        System.out.println("Общая стоимость: " + stats.getTotalPrice() + " руб.");

        // 8. Вывод всех заказов
        System.out.println("\n=== Все заказы ===");
        facade.getAllOrders().forEach(o ->
                System.out.println("ID: " + o.getId() + " | Клиент: " + o.getCustomerName() + " | Статус: " + o.getStatus()));
    }
}
