package ino.web.freeBoard.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import ino.web.freeBoard.dto.FreeBoardDto;
import ino.web.freeBoard.dto.Pagination;
import ino.web.freeBoard.dto.RefBoardDto;
import ino.web.freeBoard.dto.RefFileDto;
import ino.web.freeBoard.service.FreeBoardService;

// https://dh2y.iptime.org:10443/svn/JDJ
@Controller
public class FreeBoardController {
	
	@Autowired
	private FreeBoardService freeBoardService;
	
	// 메인게시글 리스트
	@RequestMapping(value="/main.ino", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request
							, @RequestParam(defaultValue="1") int curPage
							, @RequestParam(defaultValue="name") String search_option
							, @RequestParam(defaultValue="") String keyword){
		
		ModelAndView mav = new ModelAndView();
		FreeBoardDto boardDto = new FreeBoardDto();
		
		boardDto.setSearch_option(search_option);
		boardDto.setKeyword(keyword);
		
		int listCnt = freeBoardService.selectBoardListCnt(boardDto); //총게시글수
		System.out.println("listCnt : " + listCnt);
		
		
		Pagination pagination = new Pagination(listCnt, curPage);
		
		System.out.println("!@#!@#");
		System.out.println("!@#!@#");
		System.out.println( pagination.toString() );
		
		boardDto.setStartIndex(pagination.getStartIndex());
		boardDto.setCntPerPage(pagination.getPageSize());
		
		
		System.out.println("서치옵션 : " + search_option);
		System.out.println("키워드 : " + keyword);
		System.out.println("start : " + pagination.getStartIndex());
		System.out.println("perpage : " + pagination.getPageSize());
		
		List list = freeBoardService.freeBoardList(boardDto);
		
		
		mav.setViewName("boardMain");
		mav.addObject("freeBoardList",list);
		mav.addObject("pagination", pagination);
		mav.addObject("listCnt", listCnt);
		mav.addObject("search_option", search_option);
		mav.addObject("keyword", keyword);
	return mav;
	}
	
	
	@RequestMapping(value="/freeBoardInsert.ino", method = RequestMethod.GET)
	public String freeBoardInsert(HttpServletRequest request){
		return "freeBoardInsert";
	}
	
	// 메인 게시글 글쓰기
	@RequestMapping(value="/freeBoardInsertPro.ino", method = RequestMethod.POST)
	public String freeBoardInsertPro(FreeBoardDto dto, HttpServletRequest request){
		dto.setName(request.getParameter("name"));
		dto.setTitle(request.getParameter("title"));
		dto.setContent(request.getParameter("content"));
		freeBoardService.freeBoardInsertPro(dto);
		int num = dto.getNum();
		System.out.println("insert num >>>>>>" + num);
		return "redirect:freeBoardDetail.ino?num=" + num;
	}
	
	// 메인 게시글 상세페이지
	@RequestMapping(value="/freeBoardDetail.ino", method = RequestMethod.GET)
	public ModelAndView freeBoardDetail(HttpServletRequest request, FreeBoardDto dto){
		int num = Integer.parseInt(request.getParameter("num"));
		dto = freeBoardService.getDetailByNum(num);
		return new ModelAndView("freeBoardDetail", "freeBoardDto", dto);
	}
	
	// 메인 게시글 수정 페이지처리
	@RequestMapping(value="/freeBoardUpdate.ino", method = RequestMethod.POST)
	public ModelAndView freeBoardUpdate(HttpServletRequest request,FreeBoardDto dto){
		int num = Integer.parseInt(request.getParameter("num"));
		dto = freeBoardService.getDetailByNum(num);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("freeBoardUpdate");
		mav.addObject("freeBoardDto", dto);
		System.out.println("수정전 "+dto.toString());
		return mav;
	}
	
	// 메인 게시글 수정 기능
	@RequestMapping(value="/freeBoardUpdatePro.ino", method = RequestMethod.POST)
	public String freeBoardUpdatePro(HttpServletRequest request,FreeBoardDto dto){
		dto.setName(request.getParameter("name"));
		dto.setTitle(request.getParameter("title"));
		dto.setContent(request.getParameter("content"));
		dto.setNum(Integer.parseInt(request.getParameter("num")));
		freeBoardService.freeBoardUpdatePro(dto);
		System.out.println("수정 후"+dto.toString());
		return "redirect:main.ino";
	}
	
	// 메인 게시글 삭제 기능
	@RequestMapping(value="/freeBoardDelete.ino", method = RequestMethod.GET)
	public String freeBoardDelete(@RequestParam("num") int num){
		freeBoardService.freeBoardDelete(num);
		return "redirect:main.ino";
	}
	
	// ====================================================
	
	// 자료실 리스트
	@RequestMapping(value="/ref.ino", method = RequestMethod.GET)
	public ModelAndView ref(HttpServletRequest request
										,@RequestParam(defaultValue="1") int curPage){
		ModelAndView mav = new ModelAndView();
		RefBoardDto dto = new RefBoardDto();
		
		int listCnt = freeBoardService.selectRefBoardListCnt(dto);// 총게시글수
		
		Pagination pagination = new Pagination(listCnt, curPage);
		
		dto.setRefStartIndex(pagination.getStartIndex());
		dto.setRefCntPerPage(pagination.getPageSize());
		
		List list = freeBoardService.refBoardList(dto);
		
		
		mav.setViewName("refBoardMain");
		mav.addObject("RefBoardList",list);
		mav.addObject("pagination", pagination);
		mav.addObject("listCnt", listCnt);
		return mav;
	}
	
	// 자료실 글쓰기 화면
	@RequestMapping(value="/refBoardInsert.ino", method = RequestMethod.GET)
	public String refBoardInsert(HttpServletRequest request){
		return "refBoardInsert";
	}

	// 자료실 글쓰기 구현
	@RequestMapping(value="/refBoardInsertPro.ino", method = RequestMethod.POST)
	public String refBoardInsertPro(RefBoardDto dto,
										RefFileDto filedto
									, HttpServletRequest request
									, @RequestParam(value="file1", required = false) MultipartFile mf
									//view 에서 받아온 파일을 mf변수에 저장 
				){
			//파일이 저장되는경로
			String SAVE_PATH = "C:\\fileUpLoad\\";
			//받아온 변수 mf의 오리지널 네임 추출해서 originalFileName 변수에 담아줌
			String originalFileName = mf.getOriginalFilename();
			//받아온 변수 mf의 파일 사이즈 추출
			long fileSize = mf.getSize();
			//저장경로 + 현재시간 + 오리지널 파일이름 융합! 엑조디아
			String safeFile = SAVE_PATH + System.currentTimeMillis() + originalFileName;
			try {
				//업로드한 파일의 데이터를 저장합니다.
				mf.transferTo(new File(safeFile));
			}
			catch (IllegalStateException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("글추가할때mf값 : "+mf);
			//게시글 
			dto.setRefname(request.getParameter("refname"));
			dto.setReftitle(request.getParameter("reftitle"));
			dto.setRefcontent(request.getParameter("refcontent"));
			
			freeBoardService.refBoardInsertPro(dto);
			int refnum = dto.getRefnum();
			
			//게시글 번호를 파일 테이블 안의 해당게시글 번호 셋팅
			filedto.setBoard_no(refnum);
			filedto.setOrg_file_name(originalFileName);
			filedto.setChange_file_name(safeFile);
			filedto.setFile_size((int) fileSize);
			
			freeBoardService.refBoardFileInsertPro(filedto);
			
			System.out.println("자료실 글쓰기 dto tostring :  "+dto.toString());
			System.out.println("자료실 글쓰기 filedto tostring : "+filedto.toString());
			
			System.out.println("insert refnum >>>>>>" + refnum);
			return "redirect:refBoardDetail.ino?refnum=" + refnum;
		}
		
	// 자료실 상세페이지
		@RequestMapping(value="/refBoardDetail.ino", method = RequestMethod.GET)
		public ModelAndView refBoardDetail(HttpServletRequest request
									, @RequestParam(value="file1", required = false) MultipartFile mf,
									RefBoardDto dto, RefFileDto filedto){
			
			int refnum = Integer.parseInt(request.getParameter("refnum"));
			System.out.println("detail refnum >>>>>>" + refnum);
			
			dto = freeBoardService.getDetailRefNum(refnum);
			filedto = freeBoardService.getDetailRefFileNum(refnum);
			
			
			
			
			System.out.println("자료실 상세페이지 dto tostring :  "+dto.toString());
			
			try {
				System.out.println("자료실 상세페이지 filedto tostring : "+filedto.toString());
				
			} catch (NullPointerException e) {
				
				System.out.println("NullPointerException  발생함");
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Exception");
			} finally{
				System.out.println(" try 종료 ");
			}
			
			
			ModelAndView mav = new ModelAndView();
			mav.setViewName("refBoardDetail");
			mav.addObject("refBoardDto", dto);
			mav.addObject("fileDto", filedto);
			mav.addObject("flag", request.getParameter("flag"));
			return mav;
		}
		
		// 자료실 수정 페이지처리
		@RequestMapping(value="/refBoardUpdate.ino", method = RequestMethod.GET)
		public ModelAndView refBoardUpdate(HttpServletRequest request,
											RefBoardDto dto, 
											RefFileDto filedto,
											@RequestParam(value="file1", required = false) MultipartFile mf
											){
				int refnum = Integer.parseInt(request.getParameter("refnum"));
				
				dto = freeBoardService.getDetailRefNum(refnum);
				
				//게시글번호로 파일들 조회해옴
				filedto = freeBoardService.getDetailRefFileNum(refnum);
				
			ModelAndView mav = new ModelAndView();
			mav.setViewName("refBoardUpdate");
			mav.addObject("refBoardDto", dto);
			mav.addObject("fileDto", filedto);
			mav.addObject("flag", request.getParameter("flag"));
			return mav;
		}
		
		
		// 자료실 수정 기능
		@RequestMapping(value="/refBoardUpdatePro.ino", method = RequestMethod.POST)
		public String refBoardUpdatePro(HttpServletRequest request,RefBoardDto dto){ 	
			
			
			// 게시판 수정 할때 첨부파일 확인여부에 따른 수정 반영
			// 복습 다시 하기
			
			MultipartFile mf = null;
			MultipartHttpServletRequest  multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
			
			while(iterator.hasNext()){
				mf = multipartHttpServletRequest.getFile(iterator.next());
				if(mf.isEmpty() == false){ // 파일 있는 경우 
					
					
					//파일이 저장되는경로
					String SAVE_PATH = "C:\\fileUpLoad\\";
					//받아온 변수 mf의 오리지널 네임 추출해서 originalFileName 변수에 담아줌
					String originalFileName = mf.getOriginalFilename();
					//받아온 변수 mf의 파일 사이즈 추출
					long fileSize = mf.getSize();
					//저장경로 + 현재시간 + 오리지널 파일이름 융합! 엑조디아
					String safeFile = SAVE_PATH + System.currentTimeMillis() + originalFileName;
					try {
						//업로드한 파일의 데이터를 저장합니다.
						mf.transferTo(new File(safeFile));
					}
					catch (IllegalStateException e) {
						e.printStackTrace();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					
					
					//게시글 
					dto.setRefname(request.getParameter("refname"));
					dto.setReftitle(request.getParameter("reftitle"));
					dto.setRefcontent(request.getParameter("refcontent"));
					
					freeBoardService.refBoardInsertPro(dto);
					int refnum = dto.getRefnum();
					
					//게시글 번호를 파일 테이블 안의 해당게시글 번호 셋팅
					RefFileDto filedto = new RefFileDto();
					filedto.setBoard_no(refnum);
					filedto.setOrg_file_name(originalFileName);
					filedto.setChange_file_name(safeFile);
					filedto.setFile_size((int) fileSize);
					
					freeBoardService.refBoardFileInsertPro(filedto);
					
					System.out.println("자료실 글쓰기 dto tostring :  "+dto.toString());
					System.out.println("자료실 글쓰기 filedto tostring : "+filedto.toString());
					
					System.out.println("insert refnum >>>>>>" + refnum);
					return "redirect:ref.ino";
					
				}else{ // 파일 없는 경우
					
					dto.setRefname(request.getParameter("refname"));
					dto.setReftitle(request.getParameter("reftitle"));
					dto.setRefcontent(request.getParameter("refcontent"));
					
					freeBoardService.refBoardInsertPro(dto);
					int refnum = dto.getRefnum();
					
				}


				
			}
			System.out.println("!@#");
			System.out.println(multipartHttpServletRequest.getFileNames()); 
			System.out.println("!@#");
			
			
		    String type = request.getHeader("Content-Type");
		    
			int refnum = Integer.parseInt(request.getParameter("refnum"));
			freeBoardService.refBoardUpdatePro(dto);
			RefFileDto filedto = freeBoardService.getDetailRefFileNum(refnum);
			
			
			 
			
			
			// filenum 예외처리
			if(!String.valueOf( request.getParameter("filenum") ).equals("")){
				int filenum = Integer.parseInt(request.getParameter("filenum"));
			}
			
		return "redirect:ref.ino";
	}
		// 자료실 삭제 기능
		@RequestMapping(value="/refBoardDelete.ino", method = RequestMethod.GET)
		public String refBoardDelete(@RequestParam("refnum") int refnum){
			freeBoardService.refBoardDelete(refnum);
			freeBoardService.refBoardFileDelete(refnum);
			
			return "redirect:ref.ino";
		}
		
		// 파일 삭제 기능 N에서Y변경
		@RequestMapping(value="/fileDelete.ino", method = RequestMethod.GET)
		public String fileDelete(HttpServletRequest request, RefFileDto filedto
													,RefBoardDto dto,
													@RequestParam("filenum") int filenum
													, @RequestParam("refnum") int refnum){
			int flag = freeBoardService.fileUpdateDelete(filenum); //이거 파일 N -> Y변경임
			System.out.println(flag);
			System.out.println("filenum : " + filenum);
			System.out.println("refnum : " + refnum);
			System.out.println("toString filedto = "+ filedto.toString());
			System.out.println("toString dto = "+ dto.toString());
			return "redirect:refBoardUpdate.ino?filenum=" + filenum + "&refnum=" +refnum + "&flag="+flag;
		}
		
	/*
	 * String getName();
	 * - 파라미터의 이름을 반환해줍니다.
	 * 
	 * String getOriginalFilename();
	 * - 업로드할 파일의 실제이름을 반환합니다.
	 * 
	 * long getSize();
	 * - 업로드할 파일의 크기를 반환합니다.
	 * 
	 * boolean isEmpty()
	 * - 업로드한 파일의 존재여부를 반환합니다.
	 * 
	 * byte[] getBytes()
	 * - 업로드한 파일의 실제 데이터를 구합니다.
	 * 
	 * void transferTo(File dest)
	 * - 업로드한 파일의 데이터를 저장합니다.
	 */
		
}
