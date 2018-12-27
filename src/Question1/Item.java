package Question1;

class Item {

    private String name;
    double price;
    double totalPrice;
    double tax;
    int quantity;
    String type;

    Item() {
        this.name = "";
        this.price = -1.0;
        this.quantity = -1;
        this.type = "";
        this.totalPrice = -1.0;
        this.tax = -1.0;
    }

    void scanInput(String[] args) {
        /* Scans the name, price, quantity and type */
        if (args.length < 2) {
            return;
        }
        String property = args[0];
        String value = args[1];

        if (!property.equalsIgnoreCase("-name")) {
            return;
        }

        this.name = value;
        int i = 2;
        while (i < args.length) {
            property = args[i++];
            value = args[i++];
            switch (property) {
                case "-price": this.price = Float.parseFloat(value);
                    break;
                case "-quantity": this.quantity = Integer.parseInt(value);
                    break;
                case "-type": this.type = value;
                    break;
                default: System.err.println("Invalid input");
                    break;
            }
        }
    }

    boolean verifyInput() {
        if (this.type.equals("")) {
            System.err.println("item type is mandatory");
            return false;
        }
        if (!(this.type.equals("raw") || this.type.equals("manufactured") || this.type.equals("imported"))) {
            System.err.println("Item type can have 3 possible values raw, manufactured and imported");
            return false;
        }
        return true;
    }

    void computeTax() {
        if (this.verifyInput() && this.price != -1 && this.quantity != -1) {

            if (this.type.equals("raw")) {
                /*raw: 12.5% of the item cost*/
                this.tax = 0.125*this.price;
            }

            else if (this.type.equals("manufactured")) {
                /*manufactured: 12.5% of the item cost + 2% of (item cost + 12.5% of item cost)*/
                this.tax = 0.125*this.price + 0.02*(this.price + 0.125*this.price);
            }

            else {
                /*imported: 10% import duty on item cost + a surcharge
                (surcharge is: Rs. 5 if the final cost after applying tax & import duty is up to Rs. 100,
                Rs. 10 if the cost exceeds 100 and up to 200 and
                5% of the final cost if it exceeds 200).*/

                double importDuty = 0.1*this.price;
                double surcharge;
                if (this.price + importDuty <= 100.0)
                    surcharge = 5.0;
                else if (this.price + importDuty > 100.0 && this.price + importDuty <= 200)
                    surcharge = 10.0;
                else
                    surcharge = 0.05*(this.price + importDuty);
                this.tax = importDuty + surcharge;
            }

            this.totalPrice = (this.price + this.tax)*quantity;
        }
    }

    void printOutput() {
        /*item name, item price, sales tax liability per item, final prize (sales tax + item prize)*/
        System.out.println("Name: " + this.name);
        if (this.price != -1)
            System.out.println("Price: " + this.price);
        if (this.price != -1 && this.quantity != -1) {
            System.out.println("Tax per item: " + this.tax);
            System.out.println("Total Price: " + this.totalPrice);
        }
    }
}
