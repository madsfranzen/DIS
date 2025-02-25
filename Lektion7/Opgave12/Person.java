package Opgave12;

public class Person {
    private int id;
    private String name;
    private String city;

    public Person(int id, String name, String city){
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public String getId() {
        return Integer.toString(id);
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String toString(){
        return "ID: " + id + " Name: " + name + " City: " + city;
    }

}
