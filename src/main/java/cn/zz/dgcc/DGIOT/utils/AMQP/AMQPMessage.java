package cn.zz.dgcc.DGIOT.utils.AMQP;

import java.util.Objects;

/**
 * Created by: YYL
 * Date: 2020/4/20 14:33
 * ClassExplain :
 * ->
 */
public class AMQPMessage {

    private String content;
    private String topic;
    private String messageId;
    private byte[] body;

    public AMQPMessage(String topic, String messageId, String content) {
        this.content = content;
        this.messageId = messageId;
        this.topic = topic;
    }

    public AMQPMessage(String topic, String messageId, String content, byte[] body) {
        this.content = content;
        this.messageId = messageId;
        this.topic = topic;
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AMQPMessage that = (AMQPMessage) o;
        return Objects.equals(content, that.content) &&
                Objects.equals(topic, that.topic) &&
                Objects.equals(messageId, that.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, topic, messageId);
    }

    @Override
    public String toString() {
        return "{'msgContent':" + content +
                ", 'topic':'" + topic + '\'' +
                ", 'messageId':'" + messageId + '\'' +
                '}';
    }
}
