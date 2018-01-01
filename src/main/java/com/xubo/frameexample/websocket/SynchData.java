package com.xubo.frameexample.websocket;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SynchData implements Runnable {


    private final Session session;

    public SynchData(Session session) {
        this.session = session;
    }

    public void run() {

        int total = 5;
        int current = 0;

        try {
            session.getBasicRemote().sendText("数据同步开始......");

            for (int i = 0; i < total; i++){
                session.getBasicRemote().sendText("数据同步已完成 "+(current*100)/total+"% ......");
                TimeUnit.SECONDS.sleep(2L);
                current++;
            }

            session.getBasicRemote().sendText("数据同步结束......");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
