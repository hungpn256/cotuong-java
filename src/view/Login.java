package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {
	private JTextField email;
	private JPasswordField password;
	private JLabel LEmail;
	private JLabel LPassword;
	private JButton BLogin;
	private JButton NavigateRegister;
	public Navigator na;
	
	Login(){
		this.setSize(400,300);
		this.setLayout(null);
		initComponent();
	}
	public void initComponent() {
		email = new JTextField("");
		password = new JPasswordField("");
		LEmail = new JLabel("username");
		LPassword = new JLabel("password");
		BLogin = new JButton("Login");
		NavigateRegister = new JButton("Register");
		this.LEmail.setBounds(10, 20, 100, 30);
		this.email.setBounds(110, 20, 200, 30);
		this.LPassword.setBounds(10, 60, 100, 30);
		this.password.setBounds(110, 60, 200, 30);
		this.BLogin.setBounds(50, 100, 100, 30);
		this.NavigateRegister.setBounds(200, 100, 100, 30);
		this.add(email);
		this.add(password);
		this.add(LEmail);
		this.add(LPassword);
		this.add(BLogin);
		this.add(NavigateRegister);
		this.BLogin.addActionListener(this);
		this.NavigateRegister.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.BLogin) {
			this.login();
		}
		if(e.getSource()== this.NavigateRegister) {
			this.navigateRegister();
		}
		
	}
	
	private void navigateRegister() {
		System.out.println("register");
		na.navigateRegister();
		
	}
	public void login() {
		System.out.println("Login");
		this.na.navigateGameUI();
		
	}

}
