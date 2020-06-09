package webmarket.Entities;

import sun.util.calendar.LocalGregorianCalendar;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reserved_items")
public class ReservedItem {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne()
    @JoinColumn(name="id_user", referencedColumnName="id")
    private User user;

    @ManyToOne()
    @JoinColumns( {
            @JoinColumn(name = "storage_item_id", referencedColumnName = "item_id"),
            @JoinColumn(name = "storage_pair_id", referencedColumnName = "pair_id")
    } )
    private StorageItem storageItem;

    private Date date;


    public ReservedItem(User user, StorageItem storageItem) {
        this.user = user;
        this.storageItem = storageItem;
        date = new Date(System.currentTimeMillis());
    }

    public ReservedItem() {
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
}
