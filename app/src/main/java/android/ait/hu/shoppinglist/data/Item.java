package android.ait.hu.shoppinglist.data;

import android.ait.hu.shoppinglist.R;
import android.widget.ImageView;
import com.orm.SugarRecord;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Wanchen on 10/30/2015.
 */
public class Item extends SugarRecord<Item> implements Serializable {

    public enum ItemType {
        FOOD(0, R.drawable.food),
        ELECTRONICS(1, R.drawable.electronics), HEALTH(2, R.drawable.health), OTHER(3, R.drawable.other);

        private int value;
        private int iconId;

        private ItemType(int value, int iconId) {
            this.value = value;
            this.iconId = iconId;
        }

        public int getValue() {
            return value;
        }

        public int getIconId() {
            return iconId;
        }

        public static ItemType fromInt(int value) {
            for (ItemType p : ItemType.values()) {
                if (p.value == value) {
                    return p;
                }
            }
            return FOOD;
        }
    }

    private String item;
    private String description;
    private float price;
    private boolean done;
    private ItemType itemType;

    public Item(){}

    public Item(String item, String description, double price, ItemType itemType, boolean done) {
        this.item = item;
        this.done = done;
        this.description = description;
        this.price = (float)price;
        this.itemType = itemType;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getItem() { return item; }

    public boolean isDone() {
        return done;
    }

    public float getPrice() { return price; }

    public String getDescription() { return description;}

    public void setDescription(String description) { this.description = description;   }

    public void setPrice(float price) { this.price = price; }

    public void setItemType(ItemType itemType){ this.itemType = itemType; }

    public void setItem(String item) {
        this.item = item;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
