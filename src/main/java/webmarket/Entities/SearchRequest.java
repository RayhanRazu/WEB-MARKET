package webmarket.Entities;

public class SearchRequest {

    private int size;
    private String type;
    private  String gender;

    public SearchRequest(int size, String type, String gender) {
        this.size = size;
        this.type = type;
        this.gender = gender;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }
}
