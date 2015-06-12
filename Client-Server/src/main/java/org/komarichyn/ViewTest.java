package org.komarichyn;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.xmlrpc.XmlRpcException;
import org.komarichyn.client.ClientModule;
import org.komarichyn.client.Service;
import org.komarichyn.server.Server;
import org.komarichyn.server.ServerModule;
import org.quartz.SchedulerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


public class ViewTest extends JFrame  {

    private JButton btn;
    private  Service clientService;


    public ViewTest() throws HeadlessException, XmlRpcException, SchedulerException, IOException {
        Injector injector = Guice.createInjector(new ClientModule(), new ServerModule());
        Server server = injector.getInstance(Server.class);
        server.start();
        clientService = injector.getInstance(Service.class);
        clientService.initClient();

        initUI();
        initListeners();

    }

    private void initListeners(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("window closing");
                super.windowClosing(e);
            }
        });
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               clientService.playLogic();
            }
        });
    }

    private void initUI() {

        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(null);
        panel.setToolTipText("A Panel container");

        btn = new JButton("Button");
        btn.setBounds(100, 60, 100, 30);
        btn.setToolTipText("Start test");
        btn.setText("Start");

        panel.add(btn);

        setTitle("Test Application");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


    }


}
