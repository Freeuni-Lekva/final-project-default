package Achievement;

public class Achievement implements IAchievement {

    private int id;
    private String name;
    private String description;

    public Achievement(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{id = ");
        builder.append(this.id);
        builder.append("{, name = ");
        builder.append(this.name);
        builder.append(", description = ");
        builder.append(this.description);
        builder.append("}");
        return builder.toString();
    }
}
