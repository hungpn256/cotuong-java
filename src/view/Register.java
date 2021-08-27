package view;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JFrame implements ActionListener {
	private JTextField email;
	private JPasswordField password;
	private JLabel LEmail;
	private JLabel LPassword;
	private JButton BRegister;
	private JButton BNavigateLogin;
	private Navigator na;
	private JLabel JlabelLogin;
	Register(){
		email = new JTextField("");
		password = new JPasswordField("");
		LEmail = new JLabel("username");
		LPassword = new JLabel("password");
		BNavigateLogin = new JButton("Login");
		BRegister = new JButton("Register");
		JlabelLogin = new JLabel("Login");
		this.setSize(400,300);
		this.setLayout(null);
		this.LEmail.setBounds(10, 20, 100, 30);
		this.email.setBounds(110, 20, 200, 30);
		this.LPassword.setBounds(10, 60, 100, 30);
		this.password.setBounds(110, 60, 200, 30);
		this.BNavigateLogin.setBounds(200, 100, 100, 30);
		this.BRegister.setBounds(50, 100, 100, 30);
		this.JlabelLogin.setBounds(40, 100, 200, 50);
		this.add(email);
		this.add(password);
		this.add(LEmail);
		this.add(LPassword);
		this.add(BNavigateLogin);
		this.add(BRegister);
		this.add(JlabelLogin);
		this.BNavigateLogin.addActionListener(this);
		this.BRegister.addActionListener(this);;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.BNavigateLogin) {
			this.login();
		}
		
	}
	
	public void login() {
		System.out.println("Login");
		System.out.println(this.email.getText());
		na.navigateLogin();
	}

}
