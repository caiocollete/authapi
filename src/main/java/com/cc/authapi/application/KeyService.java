package com.cc.authapi.application;

import com.cc.authapi.domain.ApiResponse;
import com.cc.authapi.domain.Key;
import com.cc.authapi.dtos.KeyDTO;
import com.cc.authapi.repository.IKeyRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class KeyService {

    private final IKeyRepository keyRepository;

    public KeyService(IKeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }

    private Date parseExpiration(String time) throws IllegalArgumentException {
        if (time.equalsIgnoreCase("lf")) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, 100);
            return calendar.getTime();
        } else if (time.endsWith("d")) {
            int days = Integer.parseInt(time.replace("d", ""));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, days);
            return calendar.getTime();
        }
        throw new IllegalArgumentException("Invalid time format.");
    }


    public ApiResponse<KeyDTO> generateKey(String time) {
        if (time == null || time.isBlank()) {
            return new ApiResponse<>(false, "Formato inválido. Use '3d' ou 'lf'", null);
        }

        try {
            Date expiration = parseExpiration(time);
            Key key = new Key(expiration);
            keyRepository.save(key);
            return new ApiResponse<>(true, "Chave gerada com sucesso", DtosMappers.toKeyDTO(key));
        }
        catch (IllegalArgumentException e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
        catch (Exception e) {
            return new ApiResponse<>(false, "Erro interno ao gerar chave", null);
        }
    }

}