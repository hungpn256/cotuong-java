package view;


public class Navigator{
	public static Login JLogin;
	public static Register JRegister;
	public static GameUI JGameUI;
	public static void main(String[] args) {
		JLogin = new Login();
		JLogin.setVisible(true);
		JRegister = new Register();
		JRegister.setVisible(false);
		JGameUI = new GameUI();
	}
	public static void navigateRegister() {
		JLogin.setVisible(false);
		JRegister.setVisible(true);
		JGameUI.setVisible(false);
	}
	public static void navigateLogin() {
		JLogin.setVisible(true);
		JRegister.setVisible(false);
		JGameUI.setVisible(false);
	}
	public static void navigateGameUI() {
		JLogin.setVisible(false);
		JRegister.setVisible(false);
		JGameUI.setVisible(true);
	}
}
