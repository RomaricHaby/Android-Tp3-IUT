package Tp4.TP;

public class Contact
{
    private String firstName;
    private String lastName;

    private String birthday;
    private String phone;
    private String email;
    private String cap;
    private String adressPostal;
    private String genre;
    private String pathImg;

    private int image;
    private int id;

    //Avatar in res folder
    public Contact(String firstName, String lastName, String birthday, String phone, String email, String cap, String adressPostal, String genre, int image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.cap = cap;
        this.adressPostal = adressPostal;
        this.genre = genre;
        this.image = image;
        this.pathImg = null;
    }

    //Load by SQL
    public Contact(int id, String firstName, String lastName, String birthday, String phone, String email, String cap, String adressPostal, String genre, int image, String pathImg) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.cap = cap;
        this.adressPostal = adressPostal;
        this.genre = genre;
        this.image = image;
        this.id = id;
        this.pathImg = pathImg;
    }

    //Avatar from gallery and take picture
    public Contact(String firstName, String lastName, String birthday, String phone, String email, String cap, String adressPostal, String genre,String pathImg) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.cap = cap;
        this.adressPostal = adressPostal;
        this.genre = genre;
        this.pathImg = pathImg;
        this.image = -1;
    }

    public String getPathImg() {
        return pathImg;
    }

    public int getId() {
        return id;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCap() {
        return cap;
    }

    public String getAdressPostal() {
        return adressPostal;
    }

    public String getGenre() {
        return genre;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getImage() {
        return image;
    }
}
