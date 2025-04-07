package com.cc.authapi.application;

import com.cc.authapi.domain.Key;
import com.cc.authapi.repository.IKeyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class KeyService {

    private final IKeyRepository keyRepository;

    public KeyService(IKeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }

    public ResponseEntity<Object> genToken(String time) {
        if (time == null || time.isBlank()) {
            return ResponseEntity.badRequest().body("Insert a valid time format (e.g. '3d' or 'lf')");
        }

        try {
            Date expirationDate;

            if (time.equalsIgnoreCase("lf")) {
                // 100 anos no futuro
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, 100);
                expirationDate = calendar.getTime();
            } else if (time.endsWith("d")) {
                int days = Integer.parseInt(time.replace("d", ""));
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, days);
                expirationDate = calendar.getTime();
            } else {
                return ResponseEntity.badRequest().body("Invalid time format. Use 'Xd' for days or 'lf' for lifetime.");
            }

            Key newKey = new Key(expirationDate);
            keyRepository.save(newKey);

            return ResponseEntity.ok(newKey);

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid time format. Use format like '3d' or 'lf'");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error generating key");
        }
    }
}