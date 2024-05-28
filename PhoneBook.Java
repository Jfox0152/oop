class HotDrink {
    private String name;
    private int volume;

    public HotDrink(String name, int volume) {
        this.name = name;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public int getVolume() {
        return volume;
    }
}

class HotBeverage extends HotDrink {
    private int temperature;

    public HotBeverage(String name, int volume, int temperature) {
        super(name, volume);
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }
}

interface VendingMachine {
    HotDrink getProduct(String name, int volume);
    HotBeverage getProduct(String name, int volume, int temperature);
}

class HotBeverageMachine implements VendingMachine {
    private Map<String, HotDrink> inventory;

    public HotBeverageMachine() {
        inventory = new HashMap<>();
        initializeInventory(); 
    }
   
    private void initializeInventory() {
        
        inventory.put("Tea", new HotBeverage("Tea", 200, 80));
        inventory.put("Coffee", new HotBeverage("Coffee", 250, 85));
        inventory.put("Hot Chocolate", new HotBeverage("Hot Chocolate", 300, 75));
    }

    @Override
    public HotDrink getProduct(String name, int volume) {
        if (inventory.containsKey(name)) {
            HotDrink drink = inventory.get(name);
            if (drink.getVolume() >= volume) {
                return drink;
            }
        }
        return null; 
    }

    @Override
    public HotBeverage getProduct(String name, int volume, int temperature) {
        if (inventory.containsKey(name)) {
            HotBeverage beverage = (HotBeverage) inventory.get(name);
            if (beverage.getVolume() >= volume && beverage.getTemperature() == temperature) {
                return beverage;
            }
        }
        return null; 
    }
}

public class Main {
    public static void main(String[] args) {
        HotBeverageMachine vendingMachine = new HotBeverageMachine();

       
        HotDrink tea = vendingMachine.getProduct("Tea", 200);
        if (tea != null) {
            System.out.println("Enjoy your " + tea.getName());
        } else {
            System.out.println("Sorry, Tea is unavailable or the volume is too high.");
        }

        HotBeverage coffee = vendingMachine.getProduct("Coffee", 250, 85);
        if (coffee != null) {
            System.out.println("Enjoy your " + coffee.getName() + " at " + coffee.getTemperature() + "°C");
        } else {
            System.out.println("Sorry, Coffee at specific temperature is not available.");
        }
    }
}