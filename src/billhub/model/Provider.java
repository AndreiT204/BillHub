package billhub.model;

public class Provider {
    private final String name; // PPC, Vodafone etc
    private final String type; // Internet, electricity, water etc

    public Provider(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {return name;}
    public String getType() {return type;}

    @Override
    public String toString() {return name + " (" +  type + " )";}
}
