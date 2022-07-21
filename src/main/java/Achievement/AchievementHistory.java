import java.util.Date;

public class AchievementHistory implements IAchievement {

    private Achievement achievement;
    private Date date;

    public AchievementHistory(Achievement achievement, Date date) {
        this.achievement = achievement;
        this.date = date;
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
