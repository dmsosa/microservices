package com.duvi.services.noti.service.impl;

import com.duvi.services.noti.domain.NotiSettings;
import com.duvi.services.noti.domain.NotiType;
import com.duvi.services.noti.domain.Recipient;
import com.duvi.services.noti.domain.dto.NotiSettingsDTO;
import com.duvi.services.noti.repository.NotiRepository;
import com.duvi.services.noti.repository.RecipientRepository;
import com.duvi.services.noti.service.RecipientService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipientServiceImpl implements RecipientService  {
    private RecipientRepository recipientRepository;
    private NotiRepository notiRepository;
    public RecipientServiceImpl(RecipientRepository recipientRepository, NotiRepository notiRepository) {
        this.recipientRepository = recipientRepository;
        this.notiRepository = notiRepository;
    }


    @Override
    public Recipient findOrCreateRecipient(String accountName, String email) {
        Optional<Recipient> optionalRecipient = recipientRepository.findById(accountName);
        if (optionalRecipient.isEmpty()) {
            Recipient recipient = new Recipient();
            recipient.setAccountName(accountName);
            recipient.setEmail(email);
            return recipientRepository.save(recipient);
        };
        return optionalRecipient.get();
    }

    @Override
    public void removeNotification(Recipient recipient, NotiType notiType) {
        List<NotiSettings> allNotifications = notiRepository.findAllByRecipient(recipient);
        List<NotiSettings> notiSettings = allNotifications.stream().filter(noti -> noti.getType() == notiType).toList();
        if (notiSettings.isEmpty()) {
            throw new RuntimeException("Dieses nutzer der Typ von Notification %s nicht einmail hat!".formatted(notiType));
        }
        notiRepository.delete(notiSettings.getFirst());
    }

    @Override
    public void setNotification(Recipient recipient, NotiSettingsDTO noti) {
        Optional<NotiSettings> optSettings = notiRepository.findByRecipientAndType(recipient, noti.type());
        if (optSettings.isEmpty()) {
            NotiSettings notiSettings = new NotiSettings(noti);
            notiSettings.setRecipient(recipient);
            notiRepository.save(notiSettings);
        }
        NotiSettings prevSettings = optSettings.get();
        prevSettings.setActive(noti.active());
        prevSettings.setFrequency(noti.frequency());
        prevSettings.setType(noti.type());
        notiRepository.save(prevSettings);
    }

    public List<Recipient> findReadyToNoti(NotiType notiType) {
        return recipientRepository
                .findAll()
                .stream()
                .filter(recp -> isReadyToNoti(recp, notiType))
                .toList();

    }

    @Override
    public void MarkNotified(Recipient recipient, NotiType notiType) {

        Optional<NotiSettings> notiSettings = notiRepository.findByRecipientAndType(recipient, notiType);
        if (notiSettings.isEmpty()) {
            throw new RuntimeException("Es gibt kein %s Notification fur dieses Nutzer!".formatted(notiType));
        }
        NotiSettings newSettings = notiSettings.get();
        newSettings.setLastNotified(LocalDateTime.now());
        notiRepository.save(newSettings);
    }

    private boolean isReadyToNoti(Recipient recipient, NotiType notiType) {
        NotiSettings notiSettings = recipient.getNotiSettings().stream().filter(noti -> noti.getType() == notiType).toList().getFirst();
        if (notiSettings.getLastNotified() == null) return true;
        return notiSettings.getLastNotified().isBefore(LocalDateTime.now().minusDays(notiSettings.getFrequency().getDays()));
    }
}
