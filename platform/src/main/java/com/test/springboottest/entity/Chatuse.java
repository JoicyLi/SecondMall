package com.test.springboottest.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Chatuse {
    private int ChatID;//chatid
    private String getter;//接收者
    private String sender;//发送者
    private String content;//内容
    public int getChatID() {
        return ChatID;
    }

    public void setChatID(int chatID) {
        ChatID = chatID;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
/*package com.test.springboottest.entity;

        import lombok.Data;
        import lombok.NoArgsConstructor;

        import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatID;
    private int getterID;
    private int senderID;
    private String content;
    private int isshow;
}*/

