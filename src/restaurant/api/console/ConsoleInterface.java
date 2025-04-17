package restaurant.api.console;

import restaurant.api.facade.RestaurantFacade;
import restaurant.command.model.OrderStatus;
import restaurant.query.dto.OrderDTO;
import restaurant.query.dto.OrderStatisticsDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ConsoleInterface {
    private final RestaurantFacade facade;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public ConsoleInterface(RestaurantFacade facade) {
        this.facade = facade;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            showMainMenu();
            choice = readIntInput();
            scanner.nextLine(); // очистка буфера
            handleMainMenuChoice(choice);
        } while (choice != 0);
    }

    private void showMainMenu() {
        System.out.println("\n===== Система управления заказами ресторана =====");
        System.out.println("1. Создать новый заказ");
        System.out.println("2. Добавить блюдо в заказ");
        System.out.println("3. Изменить состав заказа (количество блюда)");
        System.out.println("4. Изменить статус заказа");
        System.out.println("5. Завершить заказ");
        System.out.println("6. Получить детали заказа");
        System.out.println("7. Получить статистику заказа");
        System.out.println("8. Показать все заказы");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void handleMainMenuChoice(int choice) {
        try {
            switch (choice) {
                case 0:
                    System.out.println("Выход из программы...");
                    break;
                case 1:
                    createNewOrder();
                    break;
                case 2:
                    addDish();
                    break;
                case 3:
                    updateDish();
                    break;
                case 4:
                    changeStatus();
                    break;
                case 5:
                    completeOrder();
                    break;
                case 6:
                    showOrderDetails();
                    break;
                case 7:
                    showOrderStatistics();
                    break;
                case 8:
                    showAllOrders();
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void createNewOrder() {
        System.out.print("Введите имя клиента: ");
        String customerName = scanner.nextLine().trim();
        facade.createOrder(customerName);
        System.out.println("Заказ создан для " + customerName);
    }

    private void addDish() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();
        System.out.print("Введите название блюда: ");
        String dishName = scanner.nextLine().trim();
        System.out.print("Введите количество: ");
        int quantity = readIntInput();
        facade.addDish(orderId, dishName, quantity);
        System.out.println("Блюдо добавлено в заказ.");
    }

    private void updateDish() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();
        System.out.print("Введите название блюда: ");
        String dishName = scanner.nextLine().trim();
        System.out.print("Введите новое количество: ");
        int newQuantity = readIntInput();
        facade.updateDish(orderId, dishName, newQuantity);
        System.out.println("Количество блюда обновлено.");
    }

    private void changeStatus() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();
        System.out.println("Выберите новый статус:");
        for (OrderStatus status : OrderStatus.values()) {
            System.out.println(status.ordinal() + 1 + ". " + status);
        }
        int choice = readIntInput();
        if (choice < 1 || choice > OrderStatus.values().length) {
            System.out.println("Некорректный выбор.");
            return;
        }
        OrderStatus newStatus = OrderStatus.values()[choice - 1];
        facade.changeOrderStatus(orderId, newStatus);
        System.out.println("Статус заказа изменён на " + newStatus);
    }

    private void completeOrder() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();
        facade.completeOrder(orderId);
        System.out.println("Заказ завершён.");
    }

    private void showOrderDetails() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();
        OrderDTO order = facade.getOrder(orderId);
        if (order == null) {
            System.out.println("Заказ не найден.");
            return;
        }
        System.out.println("\n=== Детали заказа ===");
        System.out.println("ID: " + order.getId());
        System.out.println("Клиент: " + order.getCustomerName());
        System.out.println("Статус: " + order.getStatus());
        System.out.println("Создан: " + order.getCreatedAt().format(dateFormatter));
        System.out.println("Блюда:");
        order.getItems().forEach(item -> System.out.println(" - " + item));
    }

    private void showOrderStatistics() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();
        OrderStatisticsDTO stats = facade.getOrderStatistics(orderId);
        System.out.println("\n=== Статистика заказа ===");
        System.out.println("ID заказа: " + stats.getOrderId());
        System.out.println("Общее количество блюд: " + stats.getTotalDishes());
        System.out.println("Общая стоимость: " + stats.getTotalPrice() + " руб.");
    }

    private void showAllOrders() {
        List<OrderDTO> orders = facade.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("Нет доступных заказов.");
            return;
        }
        System.out.println("\n=== Все заказы ===");
        orders.forEach(order -> {
            System.out.println("ID: " + order.getId() + " | Клиент: " + order.getCustomerName() +
                    " | Статус: " + order.getStatus());
        });
    }

    private int readIntInput() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            return -1;
        }
    }
}
