package view;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.PaticipantDAO;
import model.Paticipant;

public class Register extends JFrame implements ActionListener {
	private JTextField username;
	private JTextField nickName;
	private JPasswordField password;
	private JLabel LUsername;
	private JLabel LNickName;
	private JLabel LPassword;
	private JButton BRegister;
	private JButton BNavigateLogin;
	private Navigator na;
	private JLabel JlabelLogin;
	Register(){
		username = new JTextField(15);
		nickName = new JTextField(15);
		password = new JPasswordField(15);
		LUsername = new JLabel("username");
		LNickName = new JLabel("nickname");
		LPassword = new JLabel("password");
		BNavigateLogin = new JButton("Login");
		BRegister = new JButton("Register");
		JlabelLogin = new JLabel("Login");
		JPanel pnMain = new JPanel();
		this.setSize(400,200);
		pnMain.setLayout(new BoxLayout(pnMain,BoxLayout.Y_AXIS));
		pnMain.setSize(this.getSize().width-5, this.getSize().height-20);
		pnMain.add(Box.createRigidArea(new Dimension(0,20)));
//		this.LEmail.setBounds(10, 20, 100, 30);
//		this.email.setBounds(110, 20, 200, 30);
//		this.LPassword.setBounds(10, 60, 100, 30);
//		this.password.setBounds(110, 60, 200, 30);
//		this.BNavigateLogin.setBounds(200, 100, 100, 30);
//		this.BRegister.setBounds(50, 100, 100, 30);
//		this.JlabelLogin.setBounds(40, 100, 200, 50);
		pnMain.add(JlabelLogin);
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(4, 2));
		content.add(LUsername);
		content.add(username);
		content.add(LNickName);
		content.add(nickName);
		content.add(LPassword);
		content.add(password);
		content.add(BRegister);
		content.add(BNavigateLogin);
		pnMain.add(content);
		this.add(pnMain);
		pnMain.add(Box.createRigidArea(new Dimension(0,20)));
		this.BNavigateLogin.addActionListener(this);
		this.BRegister.addActionListener(this);;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.BNavigateLogin) {
			this.login();
		}
		else if(e.getSource()== this.BRegister) {
			this.register();
		}
		
	}
	
	public void login() {
		System.out.println("Login");
		na.navigateLogin();
	}
	public void register() {
		try {
			String nName = this.nickName.getText();
			String uName = this.username.getText();
			String pWord = this.password.getText();
			Paticipant p = new Paticipant(uName,pWord,nName);
			p.setStatus("offline");
			(new PaticipantDAO()).register(p);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
