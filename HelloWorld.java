/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package cn.zz.dgcc.DGIOT.aliyun.iot.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class HelloWorld {
    public static void main(String[] args) throws Exception {
        try {
            // The configuration for the Qpid InitialContextFactory has been supplied in
            // a jndi.properties file in the classpath, which results in it being picked
            // up automatically by the InitialContext constructor.
            //初始化一个上下文
            Context context = new InitialContext();
            //获取一个connectionFactory实例，配置从jndi.properties读取
            ConnectionFactory factory = (ConnectionFactory) context.lookup("myFactoryLookup");
            //获取队列（目的地—）
            Destination queue = (Destination) context.lookup("myQueueLookup");
            //通过factory获取一个连接
            Connection connection = factory.createConnection(System.getProperty("USER"), System.getProperty("PASSWORD"));
            //为connection设置一个监听
            connection.setExceptionListener(new MyExceptionListener());
            //启动连接
            connection.start();
            //创建一个session对话
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //通过对话获取一个MessageProducer信息生产者，参数为 Destination 类型
            MessageProducer messageProducer = session.createProducer(queue);
            //获取一个信息消费者
            MessageConsumer messageConsumer = session.createConsumer(queue);
            //文本信息message{helloworld}
            TextMessage message = session.createTextMessage("Hello world!");
            //通过信息生产者的send方法将文本信息发送，需要设置交付模式，信息的默认优先级，默认存活时间
            messageProducer.send(message, DeliveryMode.NON_PERSISTENT, Message.DEFAULT_PRIORITY, Message.DEFAULT_TIME_TO_LIVE);
            //通过消费者接收信息。并转换成TestMessage类型
            TextMessage receivedMessage = (TextMessage) messageConsumer.receive(2000L);
            //判断是否接收到信息
            if (receivedMessage != null) {
                System.out.println(receivedMessage.getText());
            } else {
                System.out.println("No message received within the given timeout!");
            }
            //关闭连接
            connection.close();
        } catch (Exception exp) {
            System.out.println("Caught exception, exiting.");
            exp.printStackTrace(System.out);
            System.exit(1);
        }
    }

    private static class MyExceptionListener implements ExceptionListener {
        @Override
        public void onException(JMSException exception) {
            System.out.println("Connection ExceptionListener fired, exiting.");
            exception.printStackTrace(System.out);
            System.exit(1);
        }
    }
}
