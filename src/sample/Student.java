package sample;


public class Student {
    /**
     * 是否选中
     */
    private boolean selected;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Student(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }
}
