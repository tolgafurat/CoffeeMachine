
import java.util.Scanner;

public class CoffeeMachine {
    private static final int ESPRESSO_WATER_PER_CUP = 250;
    private static final int ESPRESSO_MILK_PER_CUP = 0;
    private static final int ESPRESSO_COFFEE_PER_CUP = 16;
    private static final int ESPRESSO_PRICE = 4;

    private static final int LATTE_WATER_PER_CUP = 350;
    private static final int LATTE_MILK_PER_CUP = 75;
    private static final int LATTE_COFFEE_PER_CUP = 20;
    private static final int LATTE_PRICE = 7;

    private static final int CAPPUCCINO_WATER_PER_CUP = 200;
    private static final int CAPPUCCINO_MILK_PER_CUP = 100;
    private static final int CAPPUCCINO_COFFEE_PER_CUP = 12;
    private static final int CAPPUCCINO_PRICE = 6;

    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        boolean end;
        do {
            if (Machine.state == Machine.State.WAITING) {
                System.out.print("Write action (buy, fill, take, remaining, exit):\n> ");
            }
            end = Machine.handleAction(scanner.next());
        } while (!end);
    }

    public static class Machine {
        static boolean end;
        private static int money = 550;
        private static int water = 400;
        private static int milk = 540;
        private static int coffee = 120;
        private static int cups = 9;

        enum State {
            WAITING, BUY, FILL, FILL_WATER, FILL_MILK, FILL_COFFEE
        }

        static State state = State.WAITING;
        static boolean handleAction(String action) {
            end = false;
            switch (state) {
                case WAITING:
                    if ("buy".equals(action)) {
                        System.out.print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:\n> ");
                        state = State.BUY;
                    } else if ("fill".equals(action)) {
                        System.out.print("Write how many ml of water do you want to add:\n> ");
                        state = State.FILL;
                    } else if ("take".equals(action)) {
                        handleTakeAction();
                    } else if ("remaining".equals(action)) {
                        printState();
                    } else if ("exit".equals(action)) {
                        end = true;
                    }
                    break;
                case BUY:
                    handleBuyAction(action);
                    state = State.WAITING;
                    break;
                case FILL:
                    Machine.water += Integer.parseInt(action);
                    System.out.print("Write how many ml of milk do you want to add:\n> ");
                    state = State.FILL_WATER;
                    break;
                case FILL_WATER:
                    Machine.milk += Integer.parseInt(action);
                    System.out.print("Write how many grams of coffee beans do you want to add:\n> ");
                    state = State.FILL_MILK;
                    break;
                case FILL_MILK:
                    Machine.coffee += Integer.parseInt(action);
                    System.out.print("Write how many disposable cups of coffee do you want to add:\n> ");
                    state = State.FILL_COFFEE;
                    break;
                case FILL_COFFEE:
                    Machine.cups += Integer.parseInt(action);
                    state = State.WAITING;
                    break;
                default:
                    break;
            }
            return end;
        }

        static void printState() {
            System.out.printf("The coffee machine has:\n%d ml of water\n%d ml of milk\n", water, milk);
            System.out.printf("%d g of coffee beans\n%d of disposable cups\n$%d of money\n", coffee, cups, money);
        }

        static void handleBuyAction(String choice) {
            switch (choice) {
                case "back" -> {
                    state = State.WAITING;
                }
                case "1" -> {
                    makeCoffee(ESPRESSO_WATER_PER_CUP, ESPRESSO_MILK_PER_CUP, ESPRESSO_COFFEE_PER_CUP);
                    processPayment(ESPRESSO_PRICE);
                }
                case "2" -> {
                    makeCoffee(LATTE_WATER_PER_CUP, LATTE_MILK_PER_CUP, LATTE_COFFEE_PER_CUP);
                    processPayment(LATTE_PRICE);
                }
                case "3" -> {
                    makeCoffee(CAPPUCCINO_WATER_PER_CUP, CAPPUCCINO_MILK_PER_CUP, CAPPUCCINO_COFFEE_PER_CUP);
                    processPayment(CAPPUCCINO_PRICE);
                }
                default -> {
                    System.out.println("Unexpected option.");
                    state = State.BUY;
                }
            }
        }

        static void makeCoffee(int water, int milk, int coffee) {
            if (Machine.water < water) {
                System.out.println("Sorry, not enough water!");
                return;
            }

            if (Machine.milk < milk) {
                System.out.println("Sorry, not enough milk!");
                return;
            }

            if (Machine.coffee < coffee) {
                System.out.println("Sorry, not enough coffee bean!");
                return;
            }

            if (Machine.coffee < 1) {
                System.out.println("Sorry, not enough disposable cups!");
                return;
            }

            Machine.water -= water;
            Machine.milk -= milk;
            Machine.coffee -= coffee;
            Machine.cups--;

            System.out.println("I have enough resources, making you a coffee!");
        }

        static void processPayment(int price) {
            Machine.money += price;
        }

        static void handleTakeAction() {
            System.out.printf("I gave you $%d\n", money);
            Machine.money = 0;
        }
    }
}