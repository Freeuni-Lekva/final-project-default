import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String userName;
    private boolean isAdmin;
    private List<User> friendList = new ArrayList<>();

    public User(int id , String userName , boolean isAdmin){
        this.id = id;
        this.userName = userName;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String newUsername) {
        userName = newUsername;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean bool) {
        isAdmin = bool;
    }

    public List<User> getFriendList() {
        return friendList;
    }

    public boolean addFriend(User friend) {
        if(friendList.contains(friend)) return false;
        friendList.add(friend);
        return true;
    }
}
