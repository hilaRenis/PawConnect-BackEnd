package nl.fontys.pawconnect.configuration.security.token;

import nl.fontys.pawconnect.configuration.security.token.impl.AccessToken;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
