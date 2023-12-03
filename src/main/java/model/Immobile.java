package model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Willhaben") // Specify the collection name here
public class Immobile
{

    //   @Id
    // private String id;
    private String category;
    private String title;
    private String price;
    private String address;
    private String size;
    private String imageUrl;
    private String room;

    private String preisProQdrMeter;

    private String viewLink;
    // Constructors

    public Immobile()  {    }

    public Immobile(String category, String title, String price, String address, String size, String imageUrl,String room)
    {
        this.category = category;
        this.title = title;
        this.price = price;
        this.address = address;
        this.size = size;
        this.imageUrl = imageUrl;
        this.room = room;
    }


   /* public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    } */

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getSize() { return size; }
    public void setSize(String size) {
        this.size = size;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getRoom() {  return room;   }
    public void setRoom(String room) {  this.room = room;  }

    public void setPreisProSqMeter() {
        // Check if price or size is null or empty
        if (this.price == null || this.price.isEmpty() || this.size == null || this.size.isEmpty()) {
            this.preisProQdrMeter = "NA";
            return;
        }

        try {
            String numericPrice = this.price.replaceAll("[^\\d.]", "");
            String numericSize = this.size.replaceAll("[^\\d.]", "");

            double priceValue = Double.parseDouble(numericPrice);
            double sizeValue = Double.parseDouble(numericSize);

            if (sizeValue == 0) {
                this.preisProQdrMeter = "NA";
                return;
            }

            // Calculate and set price per square meter
            this.preisProQdrMeter = "€ " + String.format("%.2f", priceValue / sizeValue);
        } catch (NumberFormatException e) {
            this.preisProQdrMeter = "NA";
        }
    }


    public String getPreisProSqMeter()
    {
        setPreisProSqMeter();
        return this.preisProQdrMeter;
    }



    public void setViewLink(String viewLink)
    {
        this.viewLink = viewLink;
    }

    public String getViewLink()
    {
        return this.viewLink;
    }
    @Override
    public String toString() {
        return "Immobile{" +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", address='" + address + '\'' +
                ", size='" + size + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", room='" + room + '\'' +
                ", preisProSqMeter='" + preisProQdrMeter + '\'' +
                ", viewLink='" + viewLink + '\'' +
                '}';
    }



}
