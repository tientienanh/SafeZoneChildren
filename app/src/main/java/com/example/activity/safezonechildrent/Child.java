package com.example.activity.safezonechildrent;

/**
 * Created by Tien on 24-Mar-16.
 */
public class Child {
    public static final String TABLE_CHILD = "Children";
    public static final String CHILD_FULLNAME = "Fullname";
    public static final String CHILD_NICKNAME = "Nickname";
    public static final String CHILD_AGE = "Age";
    public static final String CHILD_GRADE = "Grade";
    public static final String ID = "id";
    public static final String IMAGE = "image";
    public static final String GENDER = "gender";
    public static final String PARENT_USER = "parent_user";

    private String child_fullname;
    private String child_nickname;
    private int age;
    private int grade;

    public String getParent_user() {
        return parent_user;
    }

    public void setParent_user(String parent_user) {
        this.parent_user = parent_user;
    }

    private String parent_user;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    private int gender;

    // edit
    public Child(String child_fullname, String child_nickname, int age, int grade, int gender, byte[] image) {
        this.child_fullname = child_fullname;
        this.child_nickname = child_nickname;
        this.age = age;
        this.grade = grade;
        this.gender = gender;
        this.image = image;
    }

    public Child(int id, String child_fullname, String child_nickname, int age, int grade, int gender, byte[] image) {
        this.id = id;
        this.child_fullname = child_fullname;
        this.child_nickname = child_nickname;
        this.age = age;
        this.grade = grade;
        this.gender = gender;
        this.image = image;
    }

    public Child() {
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    private byte[] image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getChild_fullname() {
        return child_fullname;
    }

    public void setChild_fullname(String child_fullname) {
        this.child_fullname = child_fullname;
    }

    public String getChild_nickname() {
        return child_nickname;
    }

    public void setChild_nickname(String child_nickname) {
        this.child_nickname = child_nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
