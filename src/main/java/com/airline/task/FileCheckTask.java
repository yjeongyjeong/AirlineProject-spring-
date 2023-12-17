package com.airline.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.airline.mapper.BoardEventFileMapper;
import com.airline.service.BoardEventService;
import com.airline.vo.BoardEventFileVO;
import com.airline.vo.BoardEventVO;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FileCheckTask {
	
	@Autowired
	private BoardEventFileMapper attachMapper;
	
	@Autowired
	private BoardEventService eventService;
	
//	@Scheduled(cron="0 * * * * *")
//	public void checkFiles() throws Exception{
//		log.warn("File Check Task run...");
//		
//		log.warn("=========================");
//	}
	
	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		
		return str.replace("-", File.separator);
	}
	
	@Scheduled(cron = "0 0 2 * * *")			//서버가 구동중이라면 새벽 2시에 안쓰는 파일 정리...
	//@Scheduled(cron="0 * * * * *")
	public void checkFiles() throws Exception{
		
		log.warn("File Check Task run...");
		log.warn(new Date());
		
		//file list in database
		List<BoardEventFileVO> fileList = attachMapper.getOldFiles();
		
		//ready for check file in directory with database file list
		List<Path> fileListPaths = fileList.stream()
									.map(vo->Paths.get("C:\\upload", vo.getUploadPath(),
											vo.getUuid() + "_" + vo.getFileName()))
									.collect(Collectors.toList());
		
		//image file has thumbnail file
		fileList.stream().filter(vo->vo.getUuid()!=null)
						.map(vo->Paths.get("C:\\upload", vo.getUploadPath(),
								"s_" + vo.getUuid() + "_" + vo.getFileName()))
						.forEach(p->fileListPaths.add(p));
		
		log.warn("====================================");
		
		fileListPaths.forEach(p->log.warn(p));
		
		//files in yesterday directory
		File targetDir = Paths.get("C:\\upload", getFolderYesterDay()).toFile();
		
		File[] removeFiles = targetDir.listFiles(file->fileListPaths.contains(file.toPath())==false);
		
		log.warn("====================================");
		for(File file : removeFiles) {
			log.warn(file.getAbsolutePath());
			file.delete();
		}
	}
	
	//@Scheduled(cron = "0 0 2 * * *")			//서버가 구동중이라면 새벽 2시에 기간이 지난 이벤트 숨김...
	@Scheduled(cron="0 * * * * *")				//초 분 시 일 월 요일 (연도-선택)
	public void checkEvent() throws Exception{
		
		log.warn("Event Check Task run...");
		log.warn(new Date());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");         
		Date now = new Date();         
		String today = sdf.format(now);
		System.out.println("현재 시간>>>" + today);
		
		//기간 지난 이벤트 리스트
		List<BoardEventVO> list = eventService.getListOverDue(today);
		eventService.getListOverDue(today).forEach(board->log.info(board));
		
		for(BoardEventVO vo : list) {
			eventService.updateOngoing(vo.getBoardNum());
			log.info(">>>>>수정>>>>"+vo);
		}
		
	}
}