package Achievement;

import java.util.Date;

public class AchievementHistory implements IAchievement {

    private int id;
    private Achievement achievement;
    private Date date;

    public AchievementHistory(int id,  Achievement achievement, Date date) {
        this.id = id;
        this.achievement = achievement;
        this.date = date;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return achievement.getName();
    }

    @Override
    public String getDescription() {
        return achievement.getDescription();
    }

    public Date getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{date = ");
        builder.append(this.date.toString());
        builder.append(", name = ");
        builder.append(getName());
        builder.append(", description = ");
        builder.append(getDescription());
        builder.append("}");
        return builder.toString();
    }
}
