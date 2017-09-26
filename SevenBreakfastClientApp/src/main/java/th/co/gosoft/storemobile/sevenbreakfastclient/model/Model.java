package th.co.gosoft.storemobile.sevenbreakfastclient.model;

/**
* Created by print on 4/24/2017.
*/

   public  class  Model {

    private  String text_name,text_price;
    private  String ImageId;
    private  int productId;

    public int getProductId() {
        return productId;
    }

    public  void setProductId(int productId) {
        this.productId = productId;
    }

    public  void setText_name(String text_Name) {this.text_name = text_Name; }

    public  void setText_price(String text_Price) {
            this.text_price = text_Price;
        }

    public  void setImageId(String imageId) {
            this.ImageId = imageId;
        }

    public String getText_name() {
            return text_name;
        }

    public String getText_price() {
            return text_price;
        }

    public String getImageId() {
            return ImageId;
        }

    }


