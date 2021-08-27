package view;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.GameRules;
import model.ChessPiece;

public class Board extends JPanel implements MouseListener {

	private int width;					// khoảng cách giữa các dòng
	boolean selected = false;
	int king1x = 4;
	int king1y = 0;
	int king2x = 4;
	int king2y = 9;
	int startX = -1;	// vị trí băt đầu
	int startY = -1;
	int endX = -1;		// vị trí kết thúc
	int endY = -1;
	public ChessPiece pieces[][];
	GameUI cchess = null;
	GameRules rules;

	// Cấu trúc bàn cờ
	public Board(ChessPiece pieces[][], int width, GameUI chess) {
		this.cchess = chess;
		this.pieces = pieces;
		this.width = width;
		rules = new GameRules(pieces);
		this.addMouseListener(this);
		this.setBounds(0, 0, 700, 700);
		this.setLayout(null);
	}

	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
//		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color c = g.getColor();	
		g.setColor(GameUI.backGround);
		g.fill3DRect(70, 30, 580, 630, false);
		g.setColor(Color.black);
		
		//BÀN CỜ
		for (int i = 80; i <= 620; i = i + 60) {
			g.drawLine(110, i, 590, i);
		}
		g.drawLine(110, 80, 110, 620);
		g.drawLine(590, 80, 590, 620);
		for (int i = 170; i <= 530; i = i + 60) {
			g.drawLine(i, 80, i, 320);
			g.drawLine(i, 380, i, 620);
		}
		g.drawLine(290, 80, 410, 200);
		g.drawLine(290, 200, 410, 80);
		g.drawLine(290, 500, 410, 620);
		g.drawLine(290, 620, 410, 500);
		
		g.setColor(Color.black);
		Font font1 = new Font(Font.DIALOG, Font.BOLD,50);
		g.setFont(font1);
		g.drawString("LT.MẠNG 2021", 170, 365);
		Font font2 = new Font(Font.DIALOG, Font.BOLD,30);
		g.setFont(font2);
		
		//QUÂN CỜ
		for (int i = 0; i < 9; ++i) {
			for (int j = 0; j < 10; ++j) {
				if (pieces[i][j] != null) {
					if (this.pieces[i][j].getSelected() != false) {
						g.setColor(GameUI.selectedBackground);
						g.fillOval(110 + i * 60 - 25, 80 + j * 60 - 25, 50, 50);
						g.setColor(GameUI.selectedTextBackground);
					} else {
						g.fillOval(110 + i * 60 - 25, 80 + j * 60 - 25, 50, 50);
						g.setColor(pieces[i][j].getColor());
					}
					g.drawString(pieces[i][j].getName(), 110 + i * 60 - 15, 80 + j * 60 + 10);
					g.setColor(Color.black);
				}
			}
		}
		g.setColor(c); //reset về màu gốc
	}

	public void mouseClicked(MouseEvent e) {
		if (this.cchess.myTurn == true) {	// nếu đúng đến lượt người chơi
			int i = -1;
			int j = -1;
			int[] pos = getPos(e);
			i = pos[0];
			j = pos[1];
			if (i >= 0 && i <= 8 && j >= 0 && j <= 9) {	// nếu đang ở trong bàn cờ
				if (selected == false) {				// không có quân cờ nào được chọn
					this.noFocus(i, j);
				} else {	// nếu quân cờ đã được chọn trước
					if (pieces[i][j] != null) {	// quân cờ chọn ở hiện tại
						if (pieces[i][j].getColor() == pieces[startX][startY].getColor()) {// my piece
							// lựa chọn
							pieces[startX][startY].setSelected(false);
							pieces[i][j].setSelected(true);
							startX = i;
							startY = j;
						} else {	// quân cờ của đối thủ
							endX = i;
							endY = j;
							String name = pieces[startX][startY].getName();
							// nếu nước đi hợp lệ
							boolean canMove = rules.canMove(startX, startY, endX, endY, name);
							if (canMove) {
								try {
									this.cchess.act.output.writeUTF(
											"<#MOVE#>" + this.cchess.act.challenger + startX + startY + endX + endY);
									this.cchess.myTurn = false;
									if (pieces[endX][endY].getName().equals("帥")
											|| pieces[endX][endY].getName().equals("將")) {
										this.win();
									} else {
										this.gameNotEnd();
									}
								} catch (Exception ee) {
									ee.printStackTrace();
								}
							}
						}
					} else {	// nếu ko có quân cờ nào ở vị trí này
						endX = i;
						endY = j;
						String name = pieces[startX][startY].getName();
						boolean canMove = rules.canMove(startX, startY, endX, endY, name);
						if (canMove) {
							this.noPieces();
						}
					}
				}
			}
			this.cchess.repaint();
		}
	}

	public int[] getPos(MouseEvent e) {
		int[] pos = new int[2];
		pos[0] = -1;
		pos[1] = -1;
		Point p = e.getPoint();
		double x = p.getX();
		double y = p.getY();
		if (Math.abs((x - 110) / 1 % 60) <= 25) {
			pos[0] = Math.round((float) (x - 110)) / 60;
		} else if (Math.abs((x - 110) / 1 % 60) >= 35) {
			pos[0] = Math.round((float) (x - 110)) / 60 + 1;
		}
		if (Math.abs((y - 80) / 1 % 60) <= 25) {
			pos[1] = Math.round((float) (y - 80)) / 60;
		} else if (Math.abs((y - 80) / 1 % 60) >= 35) {
			pos[1] = Math.round((float) (y - 80)) / 60 + 1;
		}
		return pos;
	}

	public void noFocus(int i, int j) {
		if (this.pieces[i][j] != null) {
			if (this.cchess.myColor == 0) {
				if (this.pieces[i][j].getColor().equals(GameUI.redColor)) {
					this.pieces[i][j].setSelected(true);
					selected = true;
					startX = i;
					startY = j;
				}
			} else {
				if (this.pieces[i][j].getColor().equals(GameUI.whiteColor)) {
					this.pieces[i][j].setSelected(true);
					selected = true;
					startX = i;
					startY = j;
				}
			}
		}
	}

	public void win() {
		pieces[endX][endY] = pieces[startX][startY];	//tướng bị ăn
		pieces[startX][startY] = null;
		this.cchess.repaint();// paint the final move
		JOptionPane.showMessageDialog(this.cchess, "Chúc mừng, bạn thắng!", "THÔNG BÁO", JOptionPane.INFORMATION_MESSAGE);
		
		// thiết lập cho ván chơi mới
		this.cchess.act.challenger = null;
		this.cchess.myColor = 0;
		this.cchess.myTurn = false;
		this.cchess.next();
		this.cchess.hostT.setEnabled(false);
		this.cchess.portT.setEnabled(false);
		this.cchess.nickNameT.setEnabled(false);
		this.cchess.connect.setEnabled(false);
		this.cchess.disconnect.setEnabled(true);
		this.cchess.challenge.setEnabled(true);
		this.cchess.acceptChallenge.setEnabled(false);
		this.cchess.declineChallenge.setEnabled(false);
		this.cchess.tie.setEnabled(false);
		startX = -1;
		startY = -1;
		endX = -1;
		endY = -1;
		king1x = 4;
		king1y = 0;
		king2x = 4;
		king2y = 9;
		selected = false;
	}

	public void gameNotEnd() {
		pieces[endX][endY] = pieces[startX][startY];
		pieces[startX][startY] = null;
		pieces[endX][endY].setSelected(false);
		this.cchess.repaint(); // tô màu bước đi
		// cập nhật vị trí tướng
		if (pieces[endX][endY].getName().equals("帥")) {
			king1x = endX;
			king1y = endY;
		} else if (pieces[endX][endY].getName().equals("將")) {
			king2x = endX;
			king2y = endY;
		}
		if (king1x == king2x) {	// luật
			int count = 0;
			for (int n = king1y + 1; n < king2y; ++n) {
				if (pieces[king1x][n] != null) {
					count++;
					break;
				}
			}
			if (count == 0) {// luật
				JOptionPane.showMessageDialog(this.cchess, "Bạn thua!", "THÔNG BÁO",
						JOptionPane.INFORMATION_MESSAGE);
				// thiết lập cho ván chơi mới
				this.cchess.act.challenger = null;
				this.cchess.myColor = 0;
				this.cchess.myTurn = false;
				this.cchess.next();
				this.cchess.hostT.setEnabled(false);
				this.cchess.portT.setEnabled(false);
				this.cchess.nickNameT.setEnabled(false);
				this.cchess.connect.setEnabled(false);
				this.cchess.disconnect.setEnabled(true);
				this.cchess.challenge.setEnabled(true);
				this.cchess.acceptChallenge.setEnabled(false);
				this.cchess.declineChallenge.setEnabled(false);
				this.cchess.tie.setEnabled(false);
				king1x = 4;
				king1y = 0;
				king2x = 4;
				king2y = 9;
			}
		}
		startX = -1;
		startY = -1;
		endX = -1;
		endY = -1;
		selected = false;
	}

	public void noPieces() {
		try {
			this.cchess.act.output.writeUTF("<#MOVE#>" + this.cchess.act.challenger + startX + startY + endX + endY);
			this.cchess.myTurn = false;
			pieces[endX][endY] = pieces[startX][startY];
			pieces[startX][startY] = null;
			pieces[endX][endY].setSelected(false);
			this.cchess.repaint();// tô màu bước đi

			// cập nhật vị trí tướng
			if (pieces[endX][endY].getName().equals("帥")) {
				king1x = endX;
				king1y = endY;
			} else if (pieces[endX][endY].getName().equals("將")) {
				king2x = endX;
				king2y = endY;
			}
			if (king1x == king2x)// điều kiện thua
			{
				int count = 0;
				for (int n = king1y + 1; n < king2y; ++n) {
					if (pieces[king1x][n] != null) {
						count++;
						break;
					}
				}
				if (count == 0) {// thua cuộc
					JOptionPane.showMessageDialog(this.cchess, "Bạn thua!", "THÔNG BÁO",
							JOptionPane.INFORMATION_MESSAGE);
					// thiết lập cho ván chơi mới
					this.cchess.act.challenger = null;
					this.cchess.myColor = 0;
					this.cchess.myTurn = false;
					this.cchess.next();
					this.cchess.hostT.setEnabled(false);
					this.cchess.portT.setEnabled(false);
					this.cchess.nickNameT.setEnabled(false);
					this.cchess.connect.setEnabled(false);
					this.cchess.disconnect.setEnabled(true);
					this.cchess.challenge.setEnabled(true);
					this.cchess.acceptChallenge.setEnabled(false);
					this.cchess.declineChallenge.setEnabled(false);
					this.cchess.tie.setEnabled(false);
					king1x = 4;
					king1y = 0;
					king2x = 4;
					king2y = 9;
				}
			}
			startX = -1;
			startY = -1;
			endX = -1;
			endY = -1;
			selected = false;
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	public void move(int startI, int startJ, int endX, int endY) {
		if (pieces[endX][endY] != null
				&& (pieces[endX][endY].getName().equals("帥") || pieces[endX][endY].getName().equals("將"))) {// tướng bị ăn
																											
			pieces[endX][endY] = pieces[startI][startJ];
			pieces[startI][startJ] = null;
			this.cchess.repaint();// tô màu nước đi
			JOptionPane.showMessageDialog(this.cchess, "Bạn thua!", "THÔNG BÁO", JOptionPane.INFORMATION_MESSAGE);
			
			// thiết lập cho ván chơi mới
			this.cchess.act.challenger = null;
			this.cchess.myColor = 0;
			this.cchess.myTurn = false;
			this.cchess.next();
			this.cchess.hostT.setEnabled(false);
			this.cchess.portT.setEnabled(false);
			this.cchess.nickNameT.setEnabled(false);
			this.cchess.connect.setEnabled(false);
			this.cchess.disconnect.setEnabled(true);
			this.cchess.challenge.setEnabled(true);
			this.cchess.acceptChallenge.setEnabled(false);
			this.cchess.declineChallenge.setEnabled(false);
			this.cchess.tie.setEnabled(false);
			king1x = 4;
			king1y = 0;
			king2x = 4;
			king2y = 9;
		} else {// nếu tướng chưa bị ăn
			pieces[endX][endY] = pieces[startI][startJ];
			pieces[startI][startJ] = null;// di chuyển quân cờ
			this.cchess.repaint();
			// nếu tướng đã di chuyển
			if (pieces[endX][endY].getName().equals("帥")) {
				king1x = endX;
				king1y = endY;
			} else if (pieces[endX][endY].getName().equals("將")) {
				king2x = endX;
				king2y = endY;
			}

			if (king1x == king2x) {// 2 tướng trên 1 line
				int count = 0;
				for (int n = king1y + 1; n < king2y; ++n) {
					if (pieces[king1x][n] != null) {
						count++;
						break;
					}
				}
				if (count == 0) {
					JOptionPane.showMessageDialog(this.cchess, "Bạn thắng!", "THÔNG BÁO", JOptionPane.INFORMATION_MESSAGE);
					// thiết lập cho ván chơi mới
					this.cchess.act.challenger = null;
					this.cchess.myColor = 0;
					this.cchess.myTurn = false;
					this.cchess.next();
					this.cchess.hostT.setEnabled(false);
					this.cchess.portT.setEnabled(false);
					this.cchess.nickNameT.setEnabled(false);
					this.cchess.connect.setEnabled(false);
					this.cchess.disconnect.setEnabled(true);
					this.cchess.challenge.setEnabled(true);
					this.cchess.acceptChallenge.setEnabled(false);
					this.cchess.declineChallenge.setEnabled(false);
					this.cchess.tie.setEnabled(false);
					king1x = 4;
					king1y = 0;
					king2x = 4;
					king2y = 9;
				}
			}
		}
		this.cchess.repaint();
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
