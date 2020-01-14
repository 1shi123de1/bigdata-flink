package ylqdh.bigdata.flink.test;

/**
 * @ClassName PersonPOJO
 * @Description TODO
 * @Author ylqdh
 * @Date 2020/1/14 15:46
 */
public class PersonPOJO {
    private String name;
    private int age;
    private String level;

    @Override
    public String toString() {
        return "PersonPOJO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", level='" + level + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
