package com.org.ElectricPowerSystem;

import com.org.ElectricPowerSystem.model.dto.DataDTO;
import com.org.ElectricPowerSystem.model.dto.FaultDTO;
import com.org.ElectricPowerSystem.service.ComtradeReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comtrade")
@CrossOrigin(origins = "http://localhost:4200")
public class ComtradeReaderController {

    @Autowired
    private ComtradeReaderService comtradeReaderService;

    @GetMapping("/readAndSave")
    public ResponseEntity<List<DataDTO>> readAndSave(@RequestParam String path, @RequestParam String fileName) {
        comtradeReaderService.read(path, fileName);
        comtradeReaderService.checkTrigger();
        comtradeReaderService.save();
        log.info("Comtrade with path: {}/{} read and save.", path, fileName);
        return getData();
    }

    @GetMapping("/getFault")
    public ResponseEntity<List<FaultDTO>> getFaultList() {
        return new ResponseEntity<>(comtradeReaderService.getFault(), HttpStatus.OK);
    }

    @GetMapping("/getData")
    public ResponseEntity<List<DataDTO>> getData() {
        return new ResponseEntity<>(comtradeReaderService.getData(), HttpStatus.OK);
    }
}
