package com.gabrielmotta.userscore.helpers;

import com.gabrielmotta.userscore.api.dto.CepResponse;

public class CepHelper {

    public static CepResponse mockCepResponse() {
        return new CepResponse("86015010", "PR", "Londrina", "Jardim Higienópolis", "Avenida Higienópolis");
    }
}
