package com.download.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@Controller
public class FileDownloadController {
  String folderPath = "C:\\Users\\Warmijak\\Desktop\\CV";

  @RequestMapping("/")
  public String showFiles(Model model) {
    File folder = new File(folderPath);
    File[] listOfFiles = folder.listFiles();
    model.addAttribute("files", listOfFiles);
    return "showFiles";

  }

  @RequestMapping("/file/{fileName}")
  @ResponseBody
  public void show(@PathVariable("fileName") String fileName, HttpServletResponse response) {
    if (fileName.indexOf(".pdf") > -1) response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    response.setHeader("Content-Transfer-Encoding", "binary");

    try {
      BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
      FileInputStream fis = new FileInputStream(folderPath + fileName);
      int len;
      byte[] buf = new byte[1024];
      while ((len = fis.read(buf)) > 0) {
        bos.write(buf, 0, len);
      }
      bos.close();
      response.flushBuffer();

    } catch (IOException e) {
      e.printStackTrace();

    }
  }

}
