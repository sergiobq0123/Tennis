package models;

public class Referee {

    private int id;
    private String name;
    private String password;

    public Referee(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
    public Referee(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public Referee () {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Referee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
