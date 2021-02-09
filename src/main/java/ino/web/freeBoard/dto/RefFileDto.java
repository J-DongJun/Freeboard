package ino.web.freeBoard.dto;

import org.apache.ibatis.type.Alias;

@Alias("refFileDto")
public class RefFileDto {
	
	// 파일시퀸스
	private int filenum;
	//게시글 번호
	private int board_no;
	// 원본파일이름
	private String org_file_name;
	// 변경된 파일이름
	private String change_file_name;
	// 파일 크기
	private int file_size;
	// 파일 등록일
	private String regdate;
	// 삭제구분
	private String del_yn;
	
	public int getBoard_no() {
		return board_no;
	}
	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}
	public String getOrg_file_name() {
		return org_file_name;
	}
	public void setOrg_file_name(String org_file_name) {
		this.org_file_name = org_file_name;
	}
	public String getChange_file_name() {
		return change_file_name;
	}
	public void setChange_file_name(String change_file_name) {
		this.change_file_name = change_file_name;
	}
	public int getFile_size() {
		return file_size;
	}
	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	public int getFilenum() {
		return filenum;
	}
	public void setFilenum(int filenum) {
		this.filenum = filenum;
	}
	@Override
	public String toString() {
		return "RefFileDto [filenum=" + filenum + ", board_no=" + board_no + ", org_file_name=" + org_file_name
				+ ", change_file_name=" + change_file_name + ", file_size=" + file_size + ", regdate=" + regdate
				+ ", del_yn=" + del_yn + "]";
	}
	
	
	
}
