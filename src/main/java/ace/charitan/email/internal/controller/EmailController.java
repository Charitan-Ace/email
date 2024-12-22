package ace.charitan.email.internal.controller;

import ace.charitan.email.internal.dto.CreateEmailTestDto;
import ace.charitan.email.internal.service.InternalEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class EmailController {
    @Autowired
    private InternalEmailService service;

    @PostMapping("/test")
    public ResponseEntity<String> testSendEmail(@RequestBody CreateEmailTestDto dto) {
        service.sendEmail(dto.getEmail(), dto.getSubject(), dto.getText());
        return ResponseEntity.ok("lol");
    }
}
