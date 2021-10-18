package com.pe.bluering;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.pe.bluering.service.SummerService;
import com.pe.bluering.vo.SummerVO;


@Controller
public class HomeController {
	
	@Autowired
	SummerService summerService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home2";
	}
	
	
	
	@RequestMapping(value="/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
	@ResponseBody
	public String uploadSummernoteImageFile(SummerVO summervo, @RequestParam("file") MultipartFile multipartFile, HttpServletRequest request )  {
		JsonObject jsonObject = new JsonObject();
		
		
		int idx = 0;
        if (summerService.getImgCount() == 0) {
          idx = 1;
          System.out.println("idx : " + idx);
        } else {
          idx = summerService.getLastIdx()+1;  // 마지막 행의 idx 값을 가져와서 1 증가 
          System.out.println("idx : " + idx);
        } 
        
        
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
		String fileRoot = contextRoot+"resources/fileupload/"+idx+"/";
		System.out.println("fileRoot : " +fileRoot);
		String originalFileName = multipartFile.getOriginalFilename();	
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	
		String savedFileName = UUID.randomUUID() + extension;	
		
		File targetFile = new File(fileRoot + savedFileName);	
		System.out.println("targetFile : " + targetFile);
		
		
		
		
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);	
			
			
			

			//썸네일 만들기 
			String oPath = fileRoot + savedFileName; // 원본 이미지 파일 경로
			File oFile = new File(oPath);
			
			int index = oPath.lastIndexOf(".");
			String ext = oPath.substring(index + 1); // 파일 확장자
			
			
			String thumbPath =  oFile.getParent() + File.separator +"thumb"+File.separator ;
			File Folder = new File(thumbPath);
			
			if (!Folder.exists()) {
				try{
				    Folder.mkdir(); //폴더 생성합니다.
				    System.out.println("create Folder");
			        } 
			        catch(Exception e){
				    e.getStackTrace();
				}        
		         }else {
				System.out.println("aleady create Folder");
			}
			
			
			
			String tPath = oFile.getParent() + File.separator +"thumb"+File.separator+"t-"+oFile.getName(); // 썸네일저장 경로
			System.out.println("썸네일저장 경로 : " + tPath);
			File tFile = new File(tPath);
			
			double ratio = 4; // 이미지 축소 비율
			
			//썸네일 생성 시작
			BufferedImage oImage = ImageIO.read(oFile); // 원본이미지
			int tWidth = (int) (oImage.getWidth() / ratio); // 생성할 썸네일이미지의 너비
			int tHeight = (int) (oImage.getHeight() / ratio); // 생성할 썸네일이미지의 높이
			
			BufferedImage tImage = new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_3BYTE_BGR); // 썸네일이미지
			Graphics2D graphic = tImage.createGraphics();
			Image image = oImage.getScaledInstance(tWidth, tHeight, Image.SCALE_SMOOTH);
			graphic.drawImage(image, 0, 0, tWidth, tHeight, null);
			graphic.dispose(); // 리소스를 모두 해제

			ImageIO.write(tImage, ext, tFile);
			
			//썸네일 생성 종료
				
			jsonObject.addProperty("url", "/resources/fileupload/"+idx+"/thumb/"+"t-"+savedFileName);
			jsonObject.addProperty("responseCode", "success");
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile);	
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}
		String a = jsonObject.toString();
		System.out.println("a" + a);
		return a;
		
	}
	
	
	@RequestMapping(value="/uploadSummernoteImageFileUpdate", produces = "application/json; charset=utf8")
	@ResponseBody
	public String uploadSummernoteImageFileUpdate(SummerVO summervo,  @RequestParam("idx") int idx, @RequestParam("file") MultipartFile multipartFile, HttpServletRequest request )  {
		JsonObject jsonObject = new JsonObject();

	
        System.out.println("수정 idx :"+idx);
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
		String fileRoot = contextRoot+"resources/fileupload/"+idx+"/";
		System.out.println("fileRoot : " +fileRoot);
		String originalFileName = multipartFile.getOriginalFilename();	
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	
		String savedFileName = UUID.randomUUID() + extension;	
		
		File targetFile = new File(fileRoot + savedFileName);	
		System.out.println("targetFile : " + targetFile);
		
		
		
		
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);	
			
			
			

			//썸네일 만들기 
			String oPath = fileRoot + savedFileName; // 원본 이미지 파일 경로
			File oFile = new File(oPath);
			
			int index = oPath.lastIndexOf(".");
			String ext = oPath.substring(index + 1); // 파일 확장자
			
			
			String thumbPath =  oFile.getParent() + File.separator +"thumb"+File.separator ;
			File Folder = new File(thumbPath);
			
			if (!Folder.exists()) {
				try{
				    Folder.mkdir(); //폴더 생성합니다.
				    System.out.println("create Folder");
			        } 
			        catch(Exception e){
				    e.getStackTrace();
				}        
		         }else {
				System.out.println("aleady create Folder");
			}
			
			
			
			String tPath = oFile.getParent() + File.separator +"thumb"+File.separator+"t-"+oFile.getName(); // 썸네일저장 경로
			System.out.println("썸네일저장 경로 : " + tPath);
			File tFile = new File(tPath);
			
			double ratio = 4; // 이미지 축소 비율
			
			//썸네일 생성 시작
			BufferedImage oImage = ImageIO.read(oFile); // 원본이미지
			int tWidth = (int) (oImage.getWidth() / ratio); // 생성할 썸네일이미지의 너비
			int tHeight = (int) (oImage.getHeight() / ratio); // 생성할 썸네일이미지의 높이
			
			BufferedImage tImage = new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_3BYTE_BGR); // 썸네일이미지
			Graphics2D graphic = tImage.createGraphics();
			Image image = oImage.getScaledInstance(tWidth, tHeight, Image.SCALE_SMOOTH);
			graphic.drawImage(image, 0, 0, tWidth, tHeight, null);
			graphic.dispose(); // 리소스를 모두 해제

			ImageIO.write(tImage, ext, tFile);
			
			//썸네일 생성 종료
				
			jsonObject.addProperty("url", "/resources/fileupload/"+idx+"/thumb/"+"t-"+savedFileName);
			jsonObject.addProperty("responseCode", "success");
			
			String thumbNailUrl = "/resources/fileupload/"+idx+"/thumb/"+"t-"+savedFileName;
			summervo.setThumburl(thumbNailUrl);
			summervo.setIdx(idx);
			summerService.summerThumbUpdate(summervo);
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile);	
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}
		String a = jsonObject.toString();
		System.out.println("a" + a);
		return a;
		
	}
	
	
	
	@RequestMapping(value = {"/summerUpdate"}, method = {RequestMethod.POST})
	  public String noticeUpdate(SummerVO summervo, Model model, HttpSession session, HttpServletRequest request) {
	   
	      this.summerService.summerUpdate(summervo);

	    return "redirect:/list";
	  }
	
	 @RequestMapping(value = {"/summerRegist"}, method = {RequestMethod.POST})
	  public String summerRegist(SummerVO summervo, Model model, HttpSession session) {
		 
		 System.out.println("summer : " + summervo);
		 summerService.summerInsert(summervo);
	    return "redirect:/";
	  }
	
	 
	 @RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
	  public String summerList(SummerVO summervo, Model model, HttpSession session) {
		 
		 System.out.println("summer : " + summervo);
		 List<SummerVO> list = summerService.getSummerList();
		 System.out.println("List " + list);
		 model.addAttribute("list", list);
	     return "./list";
	  }
	 
	 
	 @RequestMapping(value = {"/summerModify"}, method = {RequestMethod.GET})
	  public String noticeModify(SummerVO summervo, @RequestParam("idx") int idx, Model model, HttpSession session) {
	   
		 summervo = summerService.summerModify(idx);
		 model.addAttribute("summervo", summervo);
	     return "/summerModify";
	  }
	 
	 
	 @RequestMapping(value = {"/summerDelete"}, method = {RequestMethod.POST})
	  public String summerDelete(SummerVO summervo, Model model, HttpSession session, @RequestParam("idx") int idx, HttpServletRequest request, @RequestParam(value = "num", defaultValue = "1") int num) {
	   
		 String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
		 String fileRoot = contextRoot+"resources/fileupload/"+idx+"/";
		 deleteFile(fileRoot);
		 
		 summerService.summerDelete(idx);
		 return "redirect:/list";
	  }
	 
	 
	 private void deleteFile(String fileRoot) {
		
		 
		 	File deleteFolder = new File(fileRoot);

			if(deleteFolder.exists()){
				File[] deleteFolderList = deleteFolder.listFiles();
				System.out.println("Delete Folder List :" + deleteFolderList);
				for (int i = 0; i < deleteFolderList.length; i++) {
					if(deleteFolderList[i].isFile()) {
						deleteFolderList[i].delete();
					}else {
						deleteFile(deleteFolderList[i].getPath());
					}
					deleteFolderList[i].delete(); 
				}
				deleteFolder.delete();
			}
		
	 }


}
