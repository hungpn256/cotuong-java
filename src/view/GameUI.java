package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.AgentClientThread;
import model.ChessPiece;

import java.net.*;

public class GameUI extends JFrame implements ActionListener {

	public static final Color backGround = new Color(28, 242, 242); // 245,250,160
	public static final Color selectedBackground = new Color(242, 242, 242);
	public static final Color selectedTextBackground = new Color(96, 95, 91);
	public static final Color redColor = new Color(249, 183, 172);
	public static final Color whiteColor = Color.white;
	//Giao diện đồ hoạ người dùng
	JLabel gameLabel = new JLabel("BÁO CÁO");
	JLabel hostLabel = new JLabel("HOSTIP");
	JLabel portLabel = new JLabel("PORT ID");
	JLabel nickName = new JLabel("NAME");

	public JTextField hostT = new JTextField("127.0.0.1");
	public JTextField portT = new JTextField("1024");
	public JTextField nickNameT = new JTextField("N1");
	
	public JButton connect = new JButton("Connect (Kết nối)");
	public JButton disconnect = new JButton("Disconnect (Ngắt)");
	public JButton tie = new JButton("Tie (Xin dừng)");
	public JButton challenge = new JButton("Challenge (Thách đấu)");
	public JComboBox otherUsersList = new JComboBox();
	public JButton acceptChallenge = new JButton("Accept (Đồng ý)");
	public JButton declineChallenge = new JButton("Decline (Từ chối) ");
	
	int width = 100;// distance between lines
	ChessPiece[][] chessPieces = new ChessPiece[9][10];
	public Board board = new Board(chessPieces, width, this);
	JPanel jpRight = new JPanel();
	JSplitPane spane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, board, jpRight);

	public boolean myTurn = false;
	public int myColor = 0;		// 0 = MÀU ĐỎ, 1 = MÀU TRẮNG
	public Socket socket;
	public AgentClientThread act;

	public GameUI() {
		this.initialComponent();	// thêm thành phần khởi tạo
		this.addComponentListener();
		this.intialState();
		this.initialPieces();
		this.initialFrame();
	}

	public void initialComponent() {
		jpRight.setLayout(null);
		
		this.hostLabel.setBounds(10, 10, 50, 20);
		jpRight.add(this.hostLabel);
		
		this.hostT.setBounds(70, 10, 80, 20);
		jpRight.add(this.hostT);
		
		this.portLabel.setBounds(10, 40, 50, 20);
		jpRight.add(this.portLabel);
		
		this.portT.setBounds(70, 40, 80, 20);
		jpRight.add(this.portT);
		
		this.nickName.setBounds(10, 70, 50, 20);
		jpRight.add(this.nickName);
		
		this.nickNameT.setBounds(70, 70, 80, 20);
		jpRight.add(this.nickNameT);
		
		this.connect.setBounds(10, 145, 160, 20);
		jpRight.add(this.connect);
		
		this.disconnect.setBounds(10, 170, 160, 20);
		jpRight.add(this.disconnect);
		
		this.otherUsersList.setBounds(10, 100, 160, 20);
		jpRight.add(this.otherUsersList);
		
		this.challenge.setBounds(10, 195, 160, 20);
		jpRight.add(this.challenge);
		
		this.tie.setBounds(10, 220, 160, 20);
		jpRight.add(this.tie);
		
		this.acceptChallenge.setBounds(10, 245, 160, 20);
		jpRight.add(this.acceptChallenge);
		
		this.declineChallenge.setBounds(10, 270, 160, 20);
		jpRight.add(this.declineChallenge);
		
		this.gameLabel.setBounds(10, 350, 160, 20);
		jpRight.add(this.gameLabel);
		
		board.setLayout(null);
		board.setBounds(0, 0, 700, 700);
	}

	public void addComponentListener() {
		this.connect.addActionListener(this);
		this.disconnect.addActionListener(this);
		this.challenge.addActionListener(this);
		this.tie.addActionListener(this);
		this.acceptChallenge.addActionListener(this);
		this.declineChallenge.addActionListener(this);
	}

	public void intialState() {
		this.disconnect.setEnabled(false);
		this.challenge.setEnabled(false);
		this.acceptChallenge.setEnabled(false);
		this.declineChallenge.setEnabled(false);
		this.tie.setEnabled(false);
	}

	public void initialPieces() {
		//Các quân cờ bên ĐỎ
		chessPieces[0][0] = new ChessPiece(redColor, "車", 0, 0);
		chessPieces[1][0] = new ChessPiece(redColor, "馬", 1, 0);
		chessPieces[2][0] = new ChessPiece(redColor, "相", 2, 0);
		chessPieces[3][0] = new ChessPiece(redColor, "仕", 3, 0);
		chessPieces[4][0] = new ChessPiece(redColor, "帥", 4, 0);
		chessPieces[5][0] = new ChessPiece(redColor, "仕", 5, 0);
		chessPieces[6][0] = new ChessPiece(redColor, "相", 6, 0);
		chessPieces[7][0] = new ChessPiece(redColor, "馬", 7, 0);
		chessPieces[8][0] = new ChessPiece(redColor, "車", 8, 0);
		chessPieces[1][2] = new ChessPiece(redColor, "炮", 1, 2);
		chessPieces[7][2] = new ChessPiece(redColor, "炮", 7, 2);
		chessPieces[0][3] = new ChessPiece(redColor, "兵", 0, 3);
		chessPieces[2][3] = new ChessPiece(redColor, "兵", 2, 3);
		chessPieces[4][3] = new ChessPiece(redColor, "兵", 4, 3);
		chessPieces[6][3] = new ChessPiece(redColor, "兵", 6, 3);
		chessPieces[8][3] = new ChessPiece(redColor, "兵", 8, 3);
		
		//Các quân cờ bên TRẮNG
		chessPieces[0][9] = new ChessPiece(whiteColor, "車", 0, 9);
		chessPieces[1][9] = new ChessPiece(whiteColor, "馬", 1, 9);
		chessPieces[2][9] = new ChessPiece(whiteColor, "象", 2, 9);
		chessPieces[3][9] = new ChessPiece(whiteColor, "士", 3, 9);
		chessPieces[4][9] = new ChessPiece(whiteColor, "將", 4, 9);
		chessPieces[5][9] = new ChessPiece(whiteColor, "士", 5, 9);
		chessPieces[6][9] = new ChessPiece(whiteColor, "象", 6, 9);
		chessPieces[7][9] = new ChessPiece(whiteColor, "馬", 7, 9);
		chessPieces[8][9] = new ChessPiece(whiteColor, "車", 8, 9);
		chessPieces[1][7] = new ChessPiece(whiteColor, "砲", 1, 7);
		chessPieces[7][7] = new ChessPiece(whiteColor, "砲", 7, 7);
		chessPieces[0][6] = new ChessPiece(whiteColor, "卒", 0, 6);
		chessPieces[2][6] = new ChessPiece(whiteColor, "卒", 2, 6);
		chessPieces[4][6] = new ChessPiece(whiteColor, "卒", 4, 6);
		chessPieces[6][6] = new ChessPiece(whiteColor, "卒", 6, 6);
		chessPieces[8][6] = new ChessPiece(whiteColor, "卒", 8, 6);
	}

	public void initialFrame() {
		this.setTitle("GAME CỜ TƯỚNG");
		this.add(this.spane);
		spane.setDividerLocation(730);
		spane.setDividerSize(4);
		this.setBounds(30, 30, 930, 730);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (act == null) {
					System.exit(0);
					return;
				}
				try {
					if (act.challenger != null) {// nếu người chơi đang chơi
						try {
							act.output.writeUTF("<#GIVEUP#>" + act.challenger); //thông báo xin dừng cuộc chơi
						} catch (Exception ee) {
							ee.printStackTrace();
						}
					}
					act.output.writeUTF("<#CLIENT_LEAVE#>");
					act.connected = false;
					act = null;
				} catch (Exception ee) {
					ee.printStackTrace();
				}
				System.exit(0);
			}

		});
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.connect) {
			this.connectEvent();
		} else if (e.getSource() == this.disconnect) {
			this.disconnectEvent();
		} else if (e.getSource() == this.challenge) {
			this.challengeEvent();
		} else if (e.getSource() == this.acceptChallenge) {
			this.acceptChallengeEvent();
		} else if (e.getSource() == this.declineChallenge) {
			this.declineChallengeEvent();
		} else if (e.getSource() == this.tie) {
			this.tieEvent();
		}
	}

	public void connectEvent() {
		int port = 0;
		try {// lấy thông tin PORT ID
			port = Integer.parseInt(this.portT.getText().trim());
		} catch (Exception ee) {
			JOptionPane.showMessageDialog(this, "Chỉ nhập dạng số!", "THÔNG BÁO", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (port > 65535 || port < 0) {
			JOptionPane.showMessageDialog(this, "Số cổng kết nối chỉ từ 0 đến 65535", "THÔNG BÁO",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String name = this.nickNameT.getText().trim(); //Lấy thông tin nickname
		if (name.length() == 0) { //nếu nickname rỗng
			JOptionPane.showMessageDialog(this, "Tên không thể để trống!", "THÔNG BÁO", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			// thiết lập game
			socket = new Socket(this.hostT.getText().trim(), port);
			act = new AgentClientThread(this);
			act.start();
			this.hostT.setEnabled(false);
			this.portT.setEnabled(false);
			this.nickNameT.setEnabled(false);
			this.connect.setEnabled(false);
			this.disconnect.setEnabled(true);
			this.challenge.setEnabled(true);
			this.acceptChallenge.setEnabled(false);
			this.declineChallenge.setEnabled(false);
			this.tie.setEnabled(false);
			JOptionPane.showMessageDialog(this, "Đã kết nối tới server!", "THÔNG BÁO", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ee) {
			JOptionPane.showMessageDialog(this, "Kết nối tới server thất bại!", "THÔNG BÁO", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	public void disconnectEvent() {
		try {
			this.act.output.writeUTF("<#CLIENT_LEAVE#>");
			this.act.connected = false;
			this.act = null;
			this.hostT.setEnabled(!false);
			this.portT.setEnabled(!false);
			this.nickNameT.setEnabled(!false);
			this.connect.setEnabled(!false);
			this.disconnect.setEnabled(!true);
			this.challenge.setEnabled(!true);
			this.acceptChallenge.setEnabled(false);
			this.declineChallenge.setEnabled(false);
			this.tie.setEnabled(false);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	public void challengeEvent() {
		// thông báo chọn đối thủ
		Object o = this.otherUsersList.getSelectedItem();
		if (o == null || ((String) o).equals("")) {
			JOptionPane.showMessageDialog(this, "Hãy chọn đối thủ!", "THÔNG BÁO",
					JOptionPane.ERROR_MESSAGE);
		} else {
			String opponent = (String) this.otherUsersList.getSelectedItem();
			try {
				this.hostT.setEnabled(false);
				this.portT.setEnabled(false);
				this.nickNameT.setEnabled(false);
				this.connect.setEnabled(false);
				this.disconnect.setEnabled(!true);
				this.challenge.setEnabled(!true);
				this.acceptChallenge.setEnabled(false);
				this.declineChallenge.setEnabled(false);
				this.tie.setEnabled(false);
				this.act.challenger = opponent;
				this.myTurn = true;
				this.myColor = 0;
				this.act.output.writeUTF("<#CHALLENGE#>" + opponent);
				JOptionPane.showMessageDialog(this, "Đã gửi lời thách đấu", "THÔNG BÁO", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
	}

	public void acceptChallengeEvent() {
		try { // nhận lời thách đấu
			this.act.output.writeUTF("<#CHALACC#>" + this.act.challenger);
			this.myTurn = false;
			this.myColor = 1;
			this.hostT.setEnabled(false);
			this.portT.setEnabled(false);
			this.nickNameT.setEnabled(false);
			this.connect.setEnabled(false);
			this.disconnect.setEnabled(!true);
			this.challenge.setEnabled(!true);
			this.acceptChallenge.setEnabled(false);
			this.declineChallenge.setEnabled(false);
			this.tie.setEnabled(!false);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	public void declineChallengeEvent() {
		try {// nhận lời từ chối thách đối
			this.act.output.writeUTF("<#CHAREJECT#>" + this.act.challenger);
			this.act.challenger = null;
			this.hostT.setEnabled(false);
			this.portT.setEnabled(false);
			this.nickNameT.setEnabled(false);
			this.connect.setEnabled(false);
			this.disconnect.setEnabled(true);
			this.challenge.setEnabled(true);
			this.acceptChallenge.setEnabled(false);
			this.declineChallenge.setEnabled(false);
			this.tie.setEnabled(false);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	public void tieEvent() {
		try { // nhận lời xin dừng cuộc chơi
			this.act.output.writeUTF("<#GIVEUP#>" + this.act.challenger);
			this.act.challenger = null;
			this.myColor = 0;
			this.myTurn = false;
			this.next();// prepare for next round
			this.hostT.setEnabled(false);
			this.portT.setEnabled(false);
			this.nickNameT.setEnabled(false);
			this.connect.setEnabled(false);
			this.disconnect.setEnabled(true);
			this.challenge.setEnabled(true);
			this.acceptChallenge.setEnabled(false);
			this.declineChallenge.setEnabled(false);
			this.tie.setEnabled(false);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	public void next() {
		for (int i = 0; i < 9; ++i) {// hết cờ trên bàn cờ
			for (int j = 0; j < 10; ++j) {
				this.chessPieces[i][j] = null;
			}
		}
		this.myTurn = false;
		this.initialPieces();
		this.repaint();
	}

	public static void main(String args[]) {
		new GameUI();
	}
}
