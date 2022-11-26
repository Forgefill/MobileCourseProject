package Models;

public class Chicken {

    int id;

    String breed;

    String type;

    int production;

    public Chicken(int id, String breedName, String chickenType, int production) {
        this.id = id;
        this.breed = breedName;
        this.type = chickenType;
        this.production = production;
    }

    public Chicken() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    @Override
    public String toString() {
        return "Chicken{" +
                "id=" + id +
                ", breedName='" + breed + '\'' +
                ", chickenType='" + type + '\'' +
                ", production=" + production +
                '}';
    }
}
