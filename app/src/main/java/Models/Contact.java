package Models;

public class Contact {

    String Id;
    String Name;
    String Number;
    String Address;


    public Contact(String id, String name, String number, String address) {
        Id = id;
        Name = name;
        Number = number;
        Address = address;
    }

    public Contact() {
    }

    @Override
    public String toString() {
        return "Contact{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Number='" + Number + '\'' +
                '}';
    }

    public String toStringAll() {
        return "Contact{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Number='" + Number + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
