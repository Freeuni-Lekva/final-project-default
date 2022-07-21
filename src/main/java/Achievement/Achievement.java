public class Achievement implements IAchievement {

    private String name;
    private String description;

    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
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
        builder.append("{name = ");
        builder.append(this.name);
        builder.append(", description = ");
        builder.append(this.description);
        builder.append("}");
        return builder.toString();
    }
}
