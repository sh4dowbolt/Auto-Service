package com.agropro.testTask.controller;

import com.agropro.testTask.Entity.GPSPosition.GPSPosition;
import com.agropro.testTask.service.CalculatePathService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Controller
@SessionAttributes("fileName")
@RequestMapping("/")
public class FileController {

    private final CalculatePathService calculatePathService;


    private final String UPLOAD_DIR="src/main/resources/upload/";

    public FileController(CalculatePathService calculatePathService) {
        this.calculatePathService = calculatePathService;
    }
    // загрузка документа
    @GetMapping("calculatePath/")
    public String calculatePage() {
        return "calculatePage";
    }
    @PostMapping("calculatePath/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file,
                             RedirectAttributes attributes, Model model) {
        Path path=null;



    if(file.isEmpty()) {
        attributes.addFlashAttribute("message", "Пожалуйста," +
                " выберите файл для загрузки");

    }

    String fileName= StringUtils.cleanPath(file.getOriginalFilename());
    try {
        path = Paths.get(UPLOAD_DIR+fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);


    } catch (IOException e) {
        e.printStackTrace();
    }
    attributes.addFlashAttribute("message","Успешно загружен "+fileName);


        return "redirect:/calculatePath/";
    }
    // обработка и рассчет
    @GetMapping("calculatePath/showResult")
    public String showResultOfFile(ModelMap model, @ModelAttribute("path") Path path) {




        List<String> dataFromFile = calculatePathService.readFromTheFile(path.toString());
        List<GPSPosition> gpsPositions =
                calculatePathService.parseDataFromFileToGPSPosition(dataFromFile);
        List<GPSPosition> ggaType = calculatePathService.prepareDataGGAType(gpsPositions);
        List<GPSPosition> vtgType = calculatePathService.prepareDataVTGType(gpsPositions);


        double counting = calculatePathService.countPath(ggaType, vtgType);
        String result = "Ваш путь составляет: "+counting+" км";

       model.addAttribute("Counting", result);
        return "calculatePage";
    }
}
