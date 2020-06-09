package webmarket.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "storage")
public class StorageItem implements Serializable {

    private static final long serialVersionUID = -7315699360965172415L;

    @ManyToOne()
    @JoinColumn(name="item_id", referencedColumnName="id")
    private Item item;

    @Id
    @GeneratedValue
    private int pair_id;
    private double size;
    private int volume;


    public StorageItem(int size, int volume, Item item) {
        this.size = size;
        this.volume = volume;
        this.item = item;
    }

    public StorageItem(){}

    public double getSize() {
        return size;
    }

    public int getVolume() {
        return volume;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public int getPair_id() {
        return pair_id;
    }
}
