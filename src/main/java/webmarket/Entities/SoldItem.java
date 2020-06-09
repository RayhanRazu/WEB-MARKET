package webmarket.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sold_items")
public class SoldItem {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name="id_user", referencedColumnName="id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumns( {
            @JoinColumn(name = "storage_item_id", referencedColumnName = "item_id"),
            @JoinColumn(name = "storage_pair_id", referencedColumnName = "pair_id")
    } )
    private StorageItem storageItem;

    private Date date;

    private double price;

    public SoldItem() {
    }

    public SoldItem(User user, StorageItem storageItem, double price) {
        this.user = user;
        this.storageItem = storageItem;
        date = new Date(System.currentTimeMillis());
        this.price = price;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStorageItem(StorageItem storageItem) {
        this.storageItem = storageItem;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public StorageItem getStorageItem() {
        return storageItem;
    }

    public Date getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
