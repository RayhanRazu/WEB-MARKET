package webmarket.Entities;

import javax.persistence.*;

@Entity
@Table(name = "item")
public  class Item {

    @Id
    @GeneratedValue
    private int id;
    private double price;
    private String name;
    private String pic;
    private String type;
    private String description;
    private String gender;

    public Item( double price, String name, String pic, String type, String description, String gender) {
        this.price = price;
        this.name = name;
        this.pic = pic;
        this.type = type;
        this.description = description;
        this.gender = gender;
    }

    public Item(){}

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getPic() {
        return pic;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getGender() {
        return gender;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(int id) {
        this.id = id;
    }
}
