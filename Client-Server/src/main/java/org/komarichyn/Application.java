package org.komarichyn;

import org.apache.xmlrpc.XmlRpcException;
import org.quartz.SchedulerException;

import javax.swing.*;
import java.io.IOException;


public class Application {
    public static void main(String[] args)  {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewTest view = null;
                try {
                    view = new ViewTest();
                } catch (XmlRpcException e) {
                    e.printStackTrace();
                } catch (SchedulerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                view.setVisible(true);
            }
        });
    }
}
