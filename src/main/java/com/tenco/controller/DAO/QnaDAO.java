package com.tenco.controller.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tenco.controller.BDHelper.DBHelper;
import com.tenco.controller.DTO.QnaDTO;
import com.tenco.controller.DTO.SoloDTO;

public class QnaDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public QnaDAO() {
		conn = DBHelper.getInstance().getConnection();
	}

	public int insert(String title, String content, String userEmail) {
		String query = " INSERT INTO board(title,content,user_email) " + " VALUES " + "( ? , ? , ?) ";

		int resultRow = 0;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, userEmail);
			resultRow = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(">> Qnainsert 에서 오류");
			e.printStackTrace();
		}
		return resultRow;
	};

	public QnaDTO selectReply(String title, String email) {
		QnaDTO resultUser = null;
		String sql = " SELECT reply " 
						+ " FROM board " 
							+ " WHERE title = ? AND user_email = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(111111111);
			QnaDTO qnaDTO = new QnaDTO();
			qnaDTO.setReply(rs.getString("reply"));
			
			resultUser = qnaDTO;
			}
		} catch (Exception e) {
			System.out.println(">> 답변 보기 select에서 에러발생");
			e.printStackTrace();
		}
		return resultUser;
	}
	
	public ArrayList<QnaDTO> selectReplyList() {
		ArrayList<QnaDTO> list = new ArrayList<>();
		String sql = " SELECT *  FROM board "; 

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			QnaDTO qnaDTO = new QnaDTO();
			qnaDTO.setId(rs.getInt("id"));
			qnaDTO.setTitle(rs.getString("title"));
			qnaDTO.setContent(rs.getString("Content"));
			qnaDTO.setReply(rs.getString("reply"));
			qnaDTO.setUserEmail(rs.getString("user_email"));
			list.add(qnaDTO);
			}
		} catch (Exception e) {
			System.out.println(">> 질문 전체 select에서 에러발생");
			e.printStackTrace();
		}
		return list;
	}
	
	public int replyWrite(String reply, String id ) {
		int resultRowCount = 0;
		String queryStr = " UPDATE board "
									+ " SET reply = ? "
									+ " WHERE id = ?  ";
		PreparedStatement pStmt = null;
		try {
			System.out.println("333333333333");
			pStmt = conn.prepareStatement(queryStr);
			pStmt.setString(1, reply);
			pStmt.setString(2, id);
			resultRowCount = pStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(resultRowCount);
		return resultRowCount;
	}
	
}