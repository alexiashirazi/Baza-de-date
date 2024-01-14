// Message.java
package com.example.proiectbdfx1;

public class Message {
    private int id;
    private String text;
    private int groupId;
    private int studentId;

    public Message(int id, String text, int groupId, int studentId) {
        this.id = id;
        this.text = text;
        this.groupId = groupId;
        this.studentId = studentId;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        return  text + '\'' +
                ", grupa:" + groupId +
                '}';
    }
}
