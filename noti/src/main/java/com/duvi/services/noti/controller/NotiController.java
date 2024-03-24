package com.duvi.services.noti.controller;

import com.duvi.services.noti.domain.NotiSettings;
import com.duvi.services.noti.domain.Recipient;
import com.duvi.services.noti.domain.dto.NotiSettingsDTO;
import com.duvi.services.noti.service.NotiService;
import com.duvi.services.noti.service.RecipientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("")
public class NotiController {
    private NotiService notiService;
    private RecipientService recipientService;
    public NotiController(NotiService notiService, RecipientService recipientService) {
        this.notiService = notiService;
        this.recipientService = recipientService;
    }

    @GetMapping("/current")
    public ResponseEntity<List<NotiSettings>> getCurrentNotifications(Principal principal) {
        Jwt jwt = (Jwt) principal;
        List<NotiSettings> notiSettings = recipientService.findOrCreateRecipient(jwt.getClaimAsString("accountName"), null).getNotiSettings().stream().toList();
        return new ResponseEntity<>(notiSettings, HttpStatus.OK);
    }
    @PutMapping("/current")
    public void setNotification(Principal principal, @RequestBody NotiSettingsDTO notiSettingsDTO) {
        Jwt jwt = (Jwt) principal;
        Recipient recipient = recipientService.findOrCreateRecipient(jwt.getClaimAsString("accountName"), jwt.getClaimAsString("email"));
        recipientService.setNotification(recipient, notiSettingsDTO);
    }
}
