package nl.fontys.pawconnect.configuration.security.token;

import nl.fontys.pawconnect.configuration.security.token.impl.AccessToken;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
