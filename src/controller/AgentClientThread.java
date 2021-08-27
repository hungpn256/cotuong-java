package controller;

import javax.swing.*;

import view.GameUI;

import java.util.*;
import java.io.*;

public class AgentClientThread extends Thread {

	public GameUI client;
	public boolean connected = true;
	public DataInputStream input;
	public DataOutputStream output;
	public String challenger = null; // ko gioi han so nguoi tham gia vao server

	// Cấu trúc có thế xảy ra một ngoại lệ
	public AgentClientThread(GameUI client) {
		this.client = client;
		try {
			input = new DataInputStream(client.socket.getInputStream());
			output = new DataOutputStream(client.socket.getOutputStream());
			String name = client.nickNameT.getText().trim(); //xoá khoảng trắng đầu cuối
			output.writeUTF("<#NEW__USER#>" + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (connected) {
			try {
				String msg = input.readUTF().trim();
				if (msg.startsWith("<#NAMEEXISTALREA#>")) {
					this.nameExists();
				} else if (msg.startsWith("<#NICK_LIST#>")) {
					this.nickList(msg);
				} else if (msg.startsWith("<#SERVER_DOWN#>")) {
					this.serverDown();
				} else if (msg.startsWith("<#CHALLENGE#>")) {
					this.challenge(msg);
				} else if (msg.startsWith("<#CHALACC#>")) {
					this.accept();
				} else if (msg.startsWith("<#CHAREJECT#>")) {
					this.decline();
				} else if (msg.startsWith("<#BUSY#>")) {
					this.busy();
				} else if (msg.startsWith("<#MOVE#>")) {
					this.movePiece(msg);
				} else if (msg.startsWith("<#GIVEUP#>")) {
					this.tie();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void nameExists() {
		try {
			JOptionPane.showMessageDialog(this.client, "Tên đã tồn tại!", "THÔNG BÁO", JOptionPane.ERROR_MESSAGE);
			input.close();
			output.close();
			this.client.hostT.setEnabled(!false);
			this.client.portT.setEnabled(!false);
			this.client.nickNameT.setEnabled(!false);
			this.client.connect.setEnabled(!false);
			this.client.disconnect.setEnabled(!true);
			this.client.challenge.setEnabled(!true);
			this.client.acceptChallenge.setEnabled(false);
			this.client.declineChallenge.setEnabled(false);
			this.client.tie.setEnabled(false);
			client.socket.close();
			client.socket = null;
			client.act = null;
			connected = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Xử lý danh sách người chơi
	public void nickList(String msg) {
		String s = msg.substring(13);
		String[] na = s.split("\\|");
		Vector v = new Vector();
		for (int i = 0; i < na.length; ++i) {
			if ((na[i].trim().length() != 0) && (!na[i].trim().equals(client.nickNameT.getText().trim()))) {
				v.add(na[i]);
			}
		}
		client.otherUsersList.setModel(new DefaultComboBoxModel(v));
	}
	
	//Server bị ngắt
	public void serverDown() {
		this.client.hostT.setEnabled(!false);
		this.client.portT.setEnabled(!false);
		this.client.nickNameT.setEnabled(!false);
		this.client.connect.setEnabled(!false);
		this.client.disconnect.setEnabled(!true);
		this.client.challenge.setEnabled(!true);
		this.client.acceptChallenge.setEnabled(false);
		this.client.declineChallenge.setEnabled(false);
		this.client.tie.setEnabled(false);
		this.connected = false;
		client.act = null;
		JOptionPane.showMessageDialog(this.client, "Mất kết nối tới server!", "THÔNG BÁO", JOptionPane.INFORMATION_MESSAGE);
	}

	public void challenge(String msg) {
		try {
			String name = msg.substring(13);
			if (this.challenger == null) {// kiểm tra xem người chơi hiện tại không chơi với ai
				challenger = msg.substring(13);
				this.client.hostT.setEnabled(false);
				this.client.portT.setEnabled(false);
				this.client.nickNameT.setEnabled(false);
				this.client.connect.setEnabled(false);
				this.client.disconnect.setEnabled(!true);
				this.client.challenge.setEnabled(!true);
				this.client.acceptChallenge.setEnabled(!false);
				this.client.declineChallenge.setEnabled(!false);
				this.client.tie.setEnabled(false);
				JOptionPane.showMessageDialog(this.client, challenger + " thách đấu với bạn!", "THÔNG BÁO",
						JOptionPane.INFORMATION_MESSAGE);
			} else {	//Nếu người chơi bận
				this.output.writeUTF("<#BUSY#>" + name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void accept() {
		this.client.hostT.setEnabled(false);
		this.client.portT.setEnabled(false);
		this.client.nickNameT.setEnabled(false);
		this.client.connect.setEnabled(false);
		this.client.disconnect.setEnabled(!true);
		this.client.challenge.setEnabled(!true);
		this.client.acceptChallenge.setEnabled(false);
		this.client.declineChallenge.setEnabled(false);
		this.client.tie.setEnabled(!false);
		JOptionPane.showMessageDialog(this.client, "Thách đấu được chấp nhận! Bạn là quân đỏ, bạn đi trước!", "THÔNG BÁO",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void decline() {
		this.client.myTurn = false;
		this.client.myColor = 0;
		this.client.hostT.setEnabled(false);
		this.client.portT.setEnabled(false);
		this.client.nickNameT.setEnabled(false);
		this.client.connect.setEnabled(false);
		this.client.disconnect.setEnabled(true);
		this.client.challenge.setEnabled(true);
		this.client.acceptChallenge.setEnabled(false);
		this.client.declineChallenge.setEnabled(false);
		this.client.tie.setEnabled(false);
		JOptionPane.showMessageDialog(this.client, "Lời thách đấu bị từ chối!", "THÔNG BÁO", JOptionPane.INFORMATION_MESSAGE);
		this.challenger = null;
	}

	public void busy() {
		this.client.myTurn = false;
		this.client.myColor = 0;
		this.client.hostT.setEnabled(false);
		this.client.portT.setEnabled(false);
		this.client.nickNameT.setEnabled(false);
		this.client.connect.setEnabled(false);
		this.client.disconnect.setEnabled(true);
		this.client.challenge.setEnabled(true);
		this.client.acceptChallenge.setEnabled(false);
		this.client.declineChallenge.setEnabled(false);
		this.client.tie.setEnabled(false);
		JOptionPane.showMessageDialog(this.client, "Người chơi đang trong ván chơi khác!", "THÔNG BÁO", JOptionPane.INFORMATION_MESSAGE);
		this.challenger = null;
	}

	public void movePiece(String msg) {
		int length = msg.length();
		int startI = Integer.parseInt(msg.substring(length - 4, length - 3));
		int startJ = Integer.parseInt(msg.substring(length - 3, length - 2));
		int endI = Integer.parseInt(msg.substring(length - 2, length - 1));
		int endJ = Integer.parseInt(msg.substring(length - 1));
		this.client.board.move(startI, startJ, endI, endJ);
		this.client.myTurn = true;
	}

	public void tie() {
		JOptionPane.showMessageDialog(this.client, "Chúc mừng! Đối thủ của bạn đã xin dừng cuộc chơi.", "THÔNG BÁO",JOptionPane.INFORMATION_MESSAGE);
		this.challenger = null;
		this.client.myColor = 0;
		this.client.myTurn = false;
		this.client.next();
		this.client.hostT.setEnabled(false);
		this.client.portT.setEnabled(false);
		this.client.nickNameT.setEnabled(false);
		this.client.connect.setEnabled(false);
		this.client.disconnect.setEnabled(true);
		this.client.challenge.setEnabled(true);
		this.client.acceptChallenge.setEnabled(false);
		this.client.declineChallenge.setEnabled(false);
		this.client.tie.setEnabled(false);
	}

}
